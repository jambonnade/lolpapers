package de.jambonna.lolpapers.core.persistence;

import de.jambonna.lolpapers.core.app.AppException;
import java.util.Map;

/**
 * A DAO object for App system management, like actions done during App init
 */
public interface AppDao extends Dao {
    
    /**
     * Does initializations regarding persistence stuff,
     * typically schema migrations
     * 
     * @param outputProps Map to put global properties fetched during init
     * @return true if database was init
     * @throws AppException 
     */
    public boolean initApp(Map<String, String> outputProps) throws AppException;

}
