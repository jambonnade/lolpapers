package de.jambonna.lolpapers.core.persistence;

import de.jambonna.lolpapers.core.app.App;


/**
 * Base type of DAO objects. They need to be linked to the App object and 
 * may need to release resources explicitly when no more needed
 */
public interface Dao {
    /**
     * Provides the App object
     * 
     * @param app the App
     */
    public void setApp(App app);
    
    /**
     * Releases resources created like database connections
     */
    public void close();
}
