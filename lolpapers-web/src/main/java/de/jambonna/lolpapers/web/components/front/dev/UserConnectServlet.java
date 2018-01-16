package de.jambonna.lolpapers.web.components.front.dev;

import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.core.model.UserException;
import de.jambonna.lolpapers.web.components.front.FrontServletAbstract;
import de.jambonna.lolpapers.web.model.RequestContext;
import de.jambonna.lolpapers.web.model.session.UserSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;



@WebServlet(name = "Dev User Connect", urlPatterns = { "/dev/userConnect" })
public class UserConnectServlet extends FrontServletAbstract {

    @Override
    protected void postAction(RequestContext rc, HttpServletResponse response) throws Exception {
        if (!rc.isDevMode()) {
            responseNotFound(response);
            return;
        }
        
        UserSession session = rc.getUserSession();
        if (session.getUserId() == null) {
            Long userId = rc.getLongParam("id");
            if (userId == null && rc.getRequest().getParameter("id") != null) {
                String name = rc.getRequest().getParameter("id");
                try {
                    User user = User.newUser(name, rc.getApp());
                    rc.getMainDao().saveUser(user);
                    session.setUserId(user.getUserId());
                    addSessionSuccess(rc, "user.connectSuccess", user.getScreenName());
                } catch (UserException ex) {
                    switch (ex.getCode()) {
                        case User.USER_EXCEPTION_INVALID_NAME:
                            addSessionError(rc, "user.invalidUserNameError", name);
                            break;
                        default:
                            throw ex;
                    }
                }
            } else {
                User user = rc.getMainDao().findUser(userId);
                if (user != null) {
                    if (user.isActive()) {
                        session.setUserId(user.getUserId());
                        addSessionSuccess(rc, "user.connectSuccess", user.getScreenName());
                    } else {
                        addSessionError(rc, "user.disabledError");
                    }
                } else {
                    session.addErrorTextMessage("Invalid id");
                }
            }
        }
        
        redirectReferer(rc, response);
    }
    
}