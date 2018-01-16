package de.jambonna.lolpapers.core.app;

import de.jambonna.lolpapers.core.persistence.Dao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.jambonna.lolpapers.core.persistence.AppDao;
import de.jambonna.lolpapers.core.persistence.DataSourceFactory;
import de.jambonna.lolpapers.core.persistence.MainDao;
import java.util.UUID;

/**
 * Provides a way to create and initiate an App implementation. 
 * Holds intermediate configuration data
 */
public class AppFactory {
    private DataSource ds;
    private Map<String, String> configProps = new HashMap();

    public DataSource getDataSource() {
        return ds;
    }

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    public Map<String, String> getConfigProps() {
        return configProps;
    }

    public void setConfigProps(Map<String, String> configProps) {
        this.configProps = configProps;
    }
    
    public App startApp() throws AppException {
        AppImpl app = null;
        boolean ok = false;

        try {
            app = new AppImpl(getConfigProps(), getDataSource());
            app.init();
            ok = true;
            return app;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Error during app init", e);
        } finally {
            if (!ok && app != null) {
                app.terminate();
            }
        }
    }

    
    private static class AppImpl implements App {
        
        private static final Logger logger = LoggerFactory.getLogger(App.class);

        private final Config config;
        private final EntityManagerFactory emf;
        private final DataSource ds;
        private final AppDao appDao;
        private final MainDao mainDao;
        private final VarFiles vf;
        private final ExceptionLogger excLogger;
        private final SharedData sharedData;
        

        private AppImpl(Map<String, String> configProps, DataSource customDs) 
                throws AppException {
            logger.info("App creation...");

            Map<String, String> props = new HashMap<>();
            try {
                loadDefaultProps(props);
            } catch (IOException e) {
                throw new AppException("Unable to load default config", e);
            }
            props.putAll(configProps);
            
            loadSystemProps(props);

            logger.debug("Final config properties :");
            for (Map.Entry<String, String> prop : props.entrySet()) {
                logger.debug("{} = {}", prop.getKey(), prop.getValue());
            }

            config = new Config(props);

            vf = new VarFiles(FileSystems.getDefault(), config.getAppVarPath());
            excLogger = new ExceptionLogger(vf);
            
            sharedData = new SharedData();
            
            // Create a DataSource manually if not provided
            if (customDs == null) {
                DataSourceFactory dsf = createStorageSpecificObject(DataSourceFactory.class);
                ds = dsf.createDataSource(config);
            } else {
                ds = customDs;
            }

            
            appDao = createDaoObject(AppDao.class);
            mainDao = createDaoObject(MainDao.class);

            Map<String, Object> emfCreateProps = new HashMap<>();
            if ("1".equals(props.get("datasource.setDataSourceEmf"))) {
                emfCreateProps.put("javax.persistence.nonJtaDataSource", ds);
            }
            
            emf = Persistence.createEntityManagerFactory(
                    "de.jambonna.lolpapers.pu", emfCreateProps);
        }
        
        private void loadDefaultProps(Map<String, String> props) throws IOException {
            String resPath = "META-INF/default.properties";

            InputStream ins = getClass().getClassLoader().getResourceAsStream(resPath);
            if (ins == null) {
                throw new FileNotFoundException("Resource not found : " + resPath);
            }
            Properties p = new Properties();
            p.load(ins);
            for (String prop : p.stringPropertyNames()) {
                props.put(prop, p.getProperty(prop));
            }
        }
        
        private void loadSystemProps(Map<String, String> props) {
            String propPrefix = "de.jambonna.lolpapers.conf.";
            Properties properties = System.getProperties();
            for (String prop : properties.stringPropertyNames()) {
                if (prop.startsWith(propPrefix)) {
                    String confName = prop.substring(propPrefix.length());
                    String confValue = properties.getProperty(prop);
                    logger.debug("System config prop : {} => {}", confName, confValue);
                    props.put(confName, confValue);
                }
            }
        }

        private EntityManagerFactory getEmf() {
            return emf;
        }

        private DataSource getDs() {
            return ds;
        }
        
        private <T> T createStorageSpecificObject(Class<T> type) throws AppException {
            String clsName = type.getPackage().getName() 
                        + "." + getConfig().getPersistenceStorageType()
                        + "." + type.getSimpleName();

            try {
                Class<?> cls = Class.forName(clsName);
                Constructor<?> constructor = cls.getConstructor();
                T instance = (T)constructor.newInstance();
                return instance;
            } catch (Exception e) {
                throw new AppException("Unable to instantiate " + clsName, e);
            }
        }

        private void init() throws AppException {
            logger.info("Init...");
            // Saved props not used for now
            Map<String, String> savedProps = new HashMap<>();
            if (appDao.initApp(savedProps)) {
                logger.info("Data setup...");
                Setup s = new Setup();
                s.setupInitialData(this);
            } else {
                logger.info("Init data...");
                sharedData.resetUrls(mainDao.getAllUrls());
            }
            sharedData.updateWebsites(mainDao.getAllCategories());
        }

        
        @Override
        public Config getConfig() {
            Config c = config;
            if (c == null) {
                throw new IllegalStateException("App : no config");
            }
            return c;
        }

        @Override
        public EntityManager getNewEntityManager() {
            EntityManager em = getEmf().createEntityManager();
            return em;
        }

        @Override
        public Connection getNewSQLConnection() throws SQLException {
            DataSource theDs = getDs();
            Connection c;
            // AFAIK DataSource not thread safe
            synchronized(theDs) {
                c = theDs.getConnection();
            }
            return c;
        }

        @Override
        public final <T extends Dao> T createDaoObject(Class<T> daoInterface) throws AppException {
            T instance = createStorageSpecificObject(daoInterface);
            instance.setApp(this);
            return instance;
        }


        @Override
        public VarFiles getVarFiles() {
            return vf;
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
            mainDao.close();
            appDao.close();
            
            synchronized (emf) {
                if (emf.isOpen()) {
                    emf.close();
                }
            }
        }

    }
}
