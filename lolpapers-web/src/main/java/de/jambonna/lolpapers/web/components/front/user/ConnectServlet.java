package de.jambonna.lolpapers.web.components.front.user;

import de.jambonna.lolpapers.web.components.front.*;
import de.jambonna.lolpapers.web.model.RequestContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet(name = "User Connect", urlPatterns = { "/internal/user/connect" })
public class ConnectServlet extends FrontServletAbstract {

    private static final Logger logger = LoggerFactory.getLogger(ConnectServlet.class);

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.loadWebsiteDataFromParam();
        
        if (rc.getUser().isExisting()) {
            redirectHome(rc, response);
            return;
        }

        executeJsp(rc, response, "user/connect.jsp");
    }
    
}
