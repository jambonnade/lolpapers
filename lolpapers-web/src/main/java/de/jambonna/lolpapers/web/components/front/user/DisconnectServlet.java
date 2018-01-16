package de.jambonna.lolpapers.web.components.front.user;

import de.jambonna.lolpapers.web.components.front.FrontServletAbstract;
import de.jambonna.lolpapers.web.model.RequestContext;
import de.jambonna.lolpapers.web.model.session.UserSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "User Disconnect", urlPatterns = { "/internal/user/disconnect" })
public class DisconnectServlet extends FrontServletAbstract {

    @Override
    protected void postAction(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.loadWebsiteDataFromParam();

        UserSession session = rc.getUserSession();
        if (session.getUserId() != null) {
            session.setUserId(null);
            addSessionSuccess(rc, "user.disconnectSuccess");
        }
        redirectReferer(rc, response);
    }
    
}