package de.jambonna.lolpapers.web.model;

import de.jambonna.lolpapers.core.app.App;
import javax.servlet.ServletContext;

/**
 * Shared object stored in servlet context to be used by any web component of the app.
 * Contains the App object and maybe other stuff
 */
public class WebApp {
    public static final String CONTEXT_ATTRIBUTE = "de.jambonna.lolpapers.web.model.WebApp";
    
    private final App app;
    private final String devKey;

    
    public static WebApp setupInContext(ServletContext servletContext, App app) {
        WebApp webApp = new WebApp(servletContext, app);
        servletContext.setAttribute(CONTEXT_ATTRIBUTE, webApp);
        return webApp;
    }
    
    public static WebApp getFromContext(ServletContext servletContext) {
        return (WebApp)servletContext.getAttribute(CONTEXT_ATTRIBUTE);
    }
    

    
    public WebApp(ServletContext servletContext, App app) {
        this.app = app;
        
        devKey = servletContext.getInitParameter("de.jambonna.lolpapers.web.devKey");
    }

    public App getApp() {
        return app;
    }

    public String getDevKey() {
        return devKey;
    }

    
    
}
