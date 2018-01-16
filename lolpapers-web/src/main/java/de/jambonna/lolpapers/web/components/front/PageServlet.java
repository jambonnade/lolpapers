package de.jambonna.lolpapers.web.components.front;

import de.jambonna.lolpapers.core.model.UserException;
import de.jambonna.lolpapers.web.model.RequestContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Page", urlPatterns = { "/internal/page" })
public class PageServlet extends FrontServletAbstract {

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.loadWebsiteDataFromParam();
        rc.setCurrentUrlForAfterLogin();
        
        if (rc.getRequest().getParameter("err1") != null) {
            throw new UnsupportedOperationException("Chips crevette");
        }
        if (rc.getRequest().getParameter("err2") != null) {
            throw new UserException("chips", "Bla blablabla");
        }
                
        String page = rc.getParam("page");
        if (page != null && page.matches("^[\\w\\-]+$")) {
            executeJsp(rc, response, "page/" + page + ".jsp");
        } else {
            responseNotFound(response);
        }
    }
    
}
