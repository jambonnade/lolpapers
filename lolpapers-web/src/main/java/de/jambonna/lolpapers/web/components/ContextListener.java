package de.jambonna.lolpapers.web.components;

import de.jambonna.lolpapers.core.app.App;
import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.app.AppFactory;
import de.jambonna.lolpapers.web.model.Urlsww;
import de.jambonna.lolpapers.web.model.WebApp;
import java.util.Enumeration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;


@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        
        AppFactory appFactory = new AppFactory();
                
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/lolpapersDB");

            appFactory.setDataSource(ds);

            App app = appFactory.startApp();
            
            WebApp.setupInContext(sc, app);
            
        } catch (NamingException | AppException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        App app = (App)sc.getAttribute("app");
        if (app != null) {
            app.terminate();
            sc.removeAttribute("app");
        }
    }

}
