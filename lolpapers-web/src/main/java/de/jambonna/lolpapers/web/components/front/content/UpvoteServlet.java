package de.jambonna.lolpapers.web.components.front.content;

import de.jambonna.lolpapers.core.model.FinalArticle;
import de.jambonna.lolpapers.core.model.User;
import de.jambonna.lolpapers.web.components.front.FrontServletAbstract;
import de.jambonna.lolpapers.web.model.RequestContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Upvote Content", urlPatterns = { "/internal/upvote" })
public class UpvoteServlet extends FrontServletAbstract {

    @Override
    protected boolean preProcess(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.loadWebsiteDataFromParam();

        return !redirectToLoginIfNeeded(rc, response);
    }

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        redirectReferer(rc, response);
    }

    @Override
    protected void postAction(RequestContext rc, HttpServletResponse response) throws Exception {
        User user = rc.getUser();
        Long contentId = rc.getLongParam("id");
        if (contentId != null && user.isExisting()) {
            FinalArticle fa = rc.getMainDao().findFinalArticle(contentId);
            if (fa != null) {
                if ("1".equals(rc.getParam("upvote"))) {
                    fa.getUpvoteUsers().add(user);
                } else {
                    fa.getUpvoteUsers().remove(user);
                }
                fa.updateUpvotes();
                rc.getMainDao().saveFinalArticle(fa);
            }
        }
        redirectReferer(rc, response);
    }
}