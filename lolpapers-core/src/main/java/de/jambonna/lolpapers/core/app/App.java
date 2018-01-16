package de.jambonna.lolpapers.core.app;

import de.jambonna.lolpapers.core.persistence.Dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import javax.persistence.EntityManager;
import net.jcip.annotations.ThreadSafe;

/**
 * The central object of the application
 */
@ThreadSafe
public interface App {
    /**
     * Gets the object holding read-only configuration for the app
     * 
     * @return the Config object
     */
    public Config getConfig();

    /**
     * Gets a new EntityManager. May not fail at this point
     * 
     * @return a new EntityManager
     */
    public EntityManager getNewEntityManager();
    
    /**
     * Gets a new JDBC connection using the configured datasource. May fail
     * 
     * @return a new Connection object
     * @throws SQLException
     */
    public Connection getNewSQLConnection() throws SQLException;
    
    /**
     * Creates a DAO object of the given supertype for the configured storage type
     * 
     * @param <T> The desired abstract type
     * @param daoType The desired abstract type class
     * @return a new object implementing the desired astract type
     * @throws AppException if the implementation cannot be found/instanciated
     */
    public <T extends Dao> T createDaoObject(Class<T> daoType) throws AppException;
    
    /**
     * Gets the VarFiles object used by this app
     * 
     * @return the VarFiles object
     */
    public VarFiles getVarFiles();
    
    /**
     * Gets the SharedData object used by this app
     * 
     * @return the SharedData object
     */
    public SharedData getSharedData();
    
    /**
     * Logs an exception and returns a unique id of it, to pass to the final user
     * 
     * @param exception the exception object
     * @return a unique id
     */
    public UUID logException(Throwable exception);
    
    /**
     * Releases resources related to this instance.
     * To be called when the app object is no longer needed
     */
    public void terminate();
}
