package de.jambonna.lolpapers.web.components.front.user;

import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.Website;
import de.jambonna.lolpapers.web.components.front.FrontServletAbstract;
import de.jambonna.lolpapers.web.model.RequestContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;



@WebServlet(name = "User Profile", urlPatterns = { "/internal/user/profile" })
public class ProfileServlet extends FrontServletAbstract {

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        Website w = rc.loadWebsiteDataFromParam();
        
        Long id = rc.getLongParam("id");
        User user = rc.getMainDao().findUser(id);
        if (user == null || !user.isActive()) {
            addSessionError(rc, "user.profile.deletedUserError");
            redirectReferer(rc, response);
            return;
        }
        
        
        rc.getRequest().setAttribute("user", user);
        rc.setCurrentUrlForAfterLogin();
        
        executeJsp(rc, response, "user/profile.jsp");
    }


}
