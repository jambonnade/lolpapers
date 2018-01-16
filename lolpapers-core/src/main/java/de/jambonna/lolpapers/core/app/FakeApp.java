package de.jambonna.lolpapers.core.app;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import de.jambonna.lolpapers.core.persistence.Dao;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import javax.persistence.EntityManager;

/**
 * Fake App implementation for testing purposes
 */
public class FakeApp implements App {

    private final Config config;
    private final FileSystem fs;
    private final SharedData sharedData;
    private VarFiles vf;
    private final ExceptionLogger excLogger;
    
    
    public FakeApp(Map<String, String> config) throws AppException {
        if (config == null) {
            config = Collections.emptyMap();
        }
        this.config = new Config(config);
        
        if (config.get("fakeAppRealVarPath") != null) {
            fs = null;
            vf = new VarFiles(FileSystems.getDefault(), config.get("fakeAppRealVarPath"));
        } else {
            fs = Jimfs.newFileSystem(Configuration.unix());
            String varPath = "/var/chips";
            try {
                Files.createDirectories(fs.getPath(varPath));
            } catch (IOException e) {
                throw new AppException("Unable to create fake var path", e);
            }
            vf = new VarFiles(fs, varPath);
        }
        excLogger = new ExceptionLogger(vf);
        sharedData = new SharedData();
    }
    
    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public EntityManager getNewEntityManager() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Connection getNewSQLConnection() throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public <T extends Dao> T createDaoObject(Class<T> daoInterface) {
        throw new UnsupportedOperationException("Not supported.");
    }


    @Override
    public VarFiles getVarFiles() {
        return vf;
    }

    public void setVf(VarFiles vf) {
        this.vf = vf;
    }
    

    @Override
    public SharedData getSharedData() {
        return sharedData;
    }

    @Override
    public UUID logException(Throwable exception) {
        return excLogger.log(exception);
    }

    @Override
    public void terminate() {
        if (fs != null) {
            try {
                fs.close();
            } catch (IOException e) {
            }
        }
    }

}
