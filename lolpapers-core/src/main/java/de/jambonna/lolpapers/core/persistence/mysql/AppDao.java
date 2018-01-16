package de.jambonna.lolpapers.core.persistence.mysql;

import de.jambonna.lolpapers.core.persistence.AppDaoAbstract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 */
public class AppDao extends AppDaoAbstract {

    
    @Override
    protected String getLoadPropsSQLQuery() {
        return "SELECT `key`, `value` FROM props";
    }


    @Override
    protected void updateProps(Connection c, Map<String, String> propMap,
            Map<String, String> propsToUpdate) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement(
                    "INSERT INTO `props` (`key`, `value`) VALUES (?, ?)"
                        + " ON DUPLICATE KEY UPDATE `value` = ?");
            
            for (Map.Entry<String, String> prop : propsToUpdate.entrySet()) {                
                stmt.setString(1, prop.getKey());
                stmt.setString(2, prop.getValue());
                stmt.setString(3, prop.getValue());
                stmt.executeUpdate();
                propMap.put(prop.getKey(), prop.getValue());
            }
        } finally {
            closeSilentlySQLStmt(stmt);
        }
    }
}
