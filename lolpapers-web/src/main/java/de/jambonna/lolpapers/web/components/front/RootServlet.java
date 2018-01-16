package de.jambonna.lolpapers.web.components.front;

import de.jambonna.lolpapers.core.model.Website;
import de.jambonna.lolpapers.web.model.RequestContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Root", urlPatterns = { "" })
public class RootServlet extends FrontServletAbstract {

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        // For now just redirect to the only existing website homepage
        Website w = rc.getApp().getSharedData().getWebsite("fr");
        if (w == null) {
            responseNotFound(response);
        } else {
            response.sendRedirect(rc.getUrl(w.getRequestPath()));
        }
    }
    
}