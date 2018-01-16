package de.jambonna.lolpapers.web.components.front;

import de.jambonna.lolpapers.core.model.Category;
import de.jambonna.lolpapers.web.model.RequestContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Category", urlPatterns = { "/internal/category" })
public class CategoryServlet extends FrontServletAbstract {

    @Override
    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        rc.setCurrentUrlForAfterLogin();

        Long id = rc.getLongParam("id");
        Category c = rc.getMainDao().findCategory(id);
        if (c == null || c.getWebsite() == null) {
            responseNotFound(response);
        } else {
            rc.setWebsiteEntity(c.getWebsite());
            
            rc.getRequest().setAttribute("category", c);
            executeJsp(rc, response, "category.jsp");
        }
    }
    
}