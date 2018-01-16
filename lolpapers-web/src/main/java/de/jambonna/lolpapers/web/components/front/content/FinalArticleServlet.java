package de.jambonna.lolpapers.web.components.front.content;

import de.jambonna.lolpapers.core.model.FinalArticle;
import de.jambonna.lolpapers.web.components.front.FrontServletAbstract;
import de.jambonna.lolpapers.web.model.RequestContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Final Article", urlPatterns = { "/internal/finalarticle" })
public class FinalArticleServlet extends FrontServletAbstract {
    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.setCurrentUrlForAfterLogin();

        Long id = rc.getLongParam("id");
        FinalArticle fa = rc.getMainDao().findFinalArticle(id);
        if (fa == null || fa.getBaseArticle() == null || fa.getBaseArticle().getCategory() == null) {
            responseNotFound(response);
        } else {
            rc.setWebsiteEntity(fa.getBaseArticle().getCategory().getWebsite());
            
            rc.getRequest().setAttribute("finalArticle", fa);
            executeJsp(rc, response, "content/final-article.jsp");
        }
    }

}
