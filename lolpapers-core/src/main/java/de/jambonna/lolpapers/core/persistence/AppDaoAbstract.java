package de.jambonna.lolpapers.core.persistence;

import de.jambonna.lolpapers.core.app.AppException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Base JDBC implementation of AppDao
 */
public abstract class AppDaoAbstract extends DaoAbstract implements AppDao {

    
    @Override
    public boolean initApp(Map<String, String> outputProps) throws AppException {
        try {
            return migrate(outputProps);
        } catch (SQLException | IOException e) {
            throw new AppException("Unable to migrate schema", e);
        } finally {
            closeDedicatedConnection();
        }
    }
    
    protected boolean migrate(Map<String, String> propMap) throws SQLException, IOException {
        Integer currentSchemaVer = getApp().getConfig().getPersistenceSchemaVersion();
        int actualSchemaVer = 0;

        try {
            loadProps(propMap);
        } catch (SQLException e) {
            if (!isLoadPropsExceptionForNonexistentTable(e)) {
                throw e;
            }
        }
        
        String actualShcemaVerProp = propMap.get("schema_ver");
        if (actualShcemaVerProp != null) {            
            actualSchemaVer = Integer.valueOf(propMap.get("schema_ver"));
        }
        
        getLogger().info("Migrating schema from {} to {}", actualSchemaVer, currentSchemaVer);
        
        if (actualSchemaVer > 0) {
            for (int ver = actualSchemaVer + 1; ver <= currentSchemaVer; ver++) {
                runSqlScript(String.format("upgrade-to-%04d.sql", ver));
                updateProp(getDedicatedConnection(), propMap, "schema_ver", Integer.toString(ver));
            }
        } else {
            runSqlScript("init.sql");
            updateProp(getDedicatedConnection(), propMap, "schema_ver", currentSchemaVer.toString());
        }
        return actualSchemaVer == 0;
    }
    
    protected void runSqlScript(String name) throws IOException, SQLException {
        getLogger().info("Applying SQL script {}", name);

        String scriptContent = getSqlScript(name);
        if (scriptContent == null) {
            getLogger().warn("Script not found : {}", name);
            return;
        }
        
        Connection c = getDedicatedConnection();
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            stmt.executeUpdate(scriptContent);
        } finally {
            closeSilentlySQLStmt(stmt);
        }
    }
    
    public String getSqlScript(String name) throws IOException {
        return getPersistenceResourceTextfileContent("sql/" + name);
    }
    
    
    protected void loadProps(Map<String, String> propMap) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;

        propMap.clear();
        try {
            Connection c = getDedicatedConnection();
            stmt = c.createStatement();
            rs = stmt.executeQuery(getLoadPropsSQLQuery());
            while (rs.next()) {
                String k = rs.getString("key");
                String v = rs.getString("value");
                propMap.put(k, v);
            }
        } finally {
            closeSilentlySQLResult(rs);
            closeSilentlySQLStmt(stmt);
        }
    }
    
    protected abstract String getLoadPropsSQLQuery();
    
    protected boolean isLoadPropsExceptionForNonexistentTable(SQLException e) {
//        System.out.println("sqlstate" + e.getSQLState());
//        System.out.println("errorcode" + e.getErrorCode());
        return "42S02".equals(e.getSQLState());
    }

    
    protected void updateProp(Connection c, Map<String, String> propMap, String k, String v) throws SQLException {
        Map<String, String> propsToUpdate = new HashMap<>();
        propsToUpdate.put(k, v);
        updateProps(c, propMap, propsToUpdate);
    }
    
    protected abstract void updateProps(Connection c, 
            Map<String, String> propMap, Map<String, String> propsToUpdate) throws SQLException;
    
}
