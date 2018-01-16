package de.jambonna.lolpapers.cli;

import de.jambonna.lolpapers.core.app.App;
import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.app.AppFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Responsible for initiating the App in the CLI context
 */
public class CliAppBootstrap {
    private final AppFactory appFactory;
    
    public CliAppBootstrap() {
        appFactory = new AppFactory();
    }
    
    public void setConfigFromProperties(Properties props) {
        appFactory.getConfigProps().clear();
        
        for (String prop : props.stringPropertyNames()) {
            appFactory.getConfigProps().put(prop, props.getProperty(prop));
        }
    }
    
    public void setConfigFromPropertiesStream(InputStream ins) throws IOException {
        Properties props = new Properties();
        props.load(ins);
        setConfigFromProperties(props);
    }
    
    public void setConfigFromPropertiesResource() throws IOException {
        // Loads config file found in the classpath (typically: working dir)
        InputStream ins = getClass().getResourceAsStream("config.properties");
        if (ins != null) {
            setConfigFromPropertiesStream(ins);
        }
    }
        
    public App startApp() throws AppException {
        // EMF can't do JNDI lookup for the DataSource in a non managed program
        // => will set the EMF's DataSource manually
        appFactory.getConfigProps().put("datasource.setDataSourceEmf", "1");
    
        return appFactory.startApp();
    }
}
