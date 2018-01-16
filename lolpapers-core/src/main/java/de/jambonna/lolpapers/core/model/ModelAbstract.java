package de.jambonna.lolpapers.core.model;

import de.jambonna.lolpapers.core.app.App;

/**
 * Base class for most entity/business classes 
 */
public abstract class ModelAbstract {

    private App app;
    

    public void init(App app) {
        this.app = app;
    }
    
    public App getApp() {
        App curApp = app;
        if (curApp == null) {
            throw new IllegalStateException("No App available");
        }
        return app;
    }


}
