package de.jambonna.lolpapers.core.persistence;

import de.jambonna.lolpapers.core.app.App;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public abstract class DaoAbstract implements Dao {
    private App app;
    private Logger logger;
    private Connection dedicatedConn;
    private EntityManager dedicatedEntityManager;
    
    
    @Override
    public void setApp(App app) {
        this.app = app;
    }
    
    public App getApp() {
        if (app == null) {
            throw new IllegalStateException("No App provided");
        }
        return app;
    }
    
    @Override
    public void close() {
        closeDedicatedConnection();
        closeDedicatedEntityManager();
    }
    
    public Connection getDedicatedConnection() throws SQLException {
        if (dedicatedConn == null) {           
            dedicatedConn = app.getNewSQLConnection();
        }
        return dedicatedConn;
    }
    
    public void closeDedicatedConnection() {
        if (dedicatedConn != null) {
            try {
                dedicatedConn.close();
            } catch (SQLException e) {
                getLogger().warn("Unable to close connection", e);
            } finally {
                // In any case, don't use this connection anymore
                dedicatedConn = null;
            }
        }
    }
    
    protected void closeSilentlySQLResult(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            getLogger().warn("Unable to close result set", e);
        }
    }
    protected void closeSilentlySQLStmt(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            getLogger().warn("Unable to close statement", e);
        }
    }

    public EntityManager getDedicatedEntityManager() {
        if (dedicatedEntityManager == null) {
            dedicatedEntityManager = getApp().getNewEntityManager();
        }
        return dedicatedEntityManager;
    }
    
    public void closeDedicatedEntityManager() {
        if (dedicatedEntityManager != null) {
            try {
                dedicatedEntityManager.close();
            } catch (Exception e) {
                getLogger().warn("Unable to close entity manager", e);
            } finally {
                dedicatedEntityManager = null;
            }
        }
    }

    public void silentlyRollbackEntityManager(EntityManager em) {
        try {
            em.getTransaction().rollback();
        } catch (Exception e) {
            getLogger().error("Error during rollback operation", e);
        }
    }
    
    public InputStream getPersistenceResource(String relPath) {
        String resPath = "META-INF/persistence/" 
                + getApp().getConfig().getPersistenceStorageType()
                + "/" + relPath;
        
        InputStream ins = getClass().getClassLoader().getResourceAsStream(resPath);
        return ins;
    }
    
    public String getPersistenceResourceTextfileContent(String relPath) 
            throws IOException {
        InputStream ins = getPersistenceResource(relPath);
        if (ins != null) {
            String cs = "UTF-8";
            InputStreamReader insr = new InputStreamReader(ins, cs);
            BufferedReader br = new BufferedReader(insr);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        }
        return null;
    }
    
    public Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(getClass().getName());
        }
        return logger;
    }
}
