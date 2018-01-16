package de.jambonna.lolpapers.web.components.front.dev;

import de.jambonna.lolpapers.web.components.front.FrontServletAbstract;
import de.jambonna.lolpapers.web.model.RequestContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Dev Update Data", urlPatterns = { "/dev/updateData" })
public class UpdateDataServlet extends FrontServletAbstract {

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        if (!rc.isDevMode()) {
            responseNotFound(response);
            return;
        }
        
        rc.getApp().getSharedData().resetUrls(rc.getMainDao().getAllUrls());
        rc.getApp().getSharedData().updateWebsites(rc.getMainDao().getAllCategories());
        
        redirectReferer(rc, response);
    }
    
}