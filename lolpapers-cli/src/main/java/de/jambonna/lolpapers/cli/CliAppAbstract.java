package de.jambonna.lolpapers.cli;

import de.jambonna.lolpapers.core.app.App;
import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.lang.Languages;
import de.jambonna.lolpapers.core.persistence.MainDao;
import java.io.IOException;

/**
 * Base class of CLI programs
 */
public abstract class CliAppAbstract {
    private String[] args;
    private App app;
    private MainDao mainDao;
    private Languages languages;
    

    public void run(String[] args) {
        this.args = args;
        
        try {
            CliAppBootstrap bootstrap = new CliAppBootstrap();
            bootstrap.setConfigFromPropertiesResource();

            app = bootstrap.startApp();
        } catch (IOException | AppException e) {
            System.err.println("Unable to init app :");
            e.printStackTrace(System.err);
            return;
        }
        
        try {
            doStuff();
        } catch (Exception e) {
            System.err.println("Error during process :");
            e.printStackTrace(System.err);
        } finally {
            if (mainDao != null) {
                mainDao.close();
                mainDao = null;
            }
            app.terminate();
            app = null;
        }
    }
    
    public App getApp() {
        return app;
    }
    
    public MainDao getMainDao() throws AppException {
        if (mainDao == null) {
            mainDao = getApp().createDaoObject(MainDao.class);
        }
        return mainDao;
    }
    
    public Languages getLanguages() {
        if (languages == null) {
            languages = new Languages();
        }
        return languages;
    }
    
    protected abstract void doStuff() throws Exception;
}
