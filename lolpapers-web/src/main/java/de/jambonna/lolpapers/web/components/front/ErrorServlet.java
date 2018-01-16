package de.jambonna.lolpapers.web.components.front;

import de.jambonna.lolpapers.web.model.RequestContext;
import de.jambonna.lolpapers.web.model.WebApp;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Error", urlPatterns = { "/internal/error" })
public class ErrorServlet extends FrontServletAbstract {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestContext rc = new RequestContext(req);
        rc.setupInRequest();
        try {
            Throwable throwable = (Throwable)rc.getRequest().getAttribute("javax.servlet.error.exception");
            UUID excId = null;
            if (throwable != null) {
                excId = rc.getApp().logException(throwable);
                rc.getRequest().setAttribute("errorRef", excId);
            }
            if (resp.isCommitted()) {
                addHtmlErrorInCommittedResponse(rc, resp, excId);
            } else {
                forwardJsp(rc, resp, "error.jsp");
            }
        } catch (Exception ex) {
            log("Error handling error", ex);
            failSafeHandleError(req, resp);
        } finally {
            rc.close();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
    protected void addHtmlErrorInCommittedResponse(
            RequestContext rc, HttpServletResponse response, UUID excId) throws IOException {
        PrintWriter out = response.getWriter();
        try {
            String msg = rc.getMessageBundle().getString("error.exception.title");
            out.println("<!--");
            out.println(msg);
            if (excId != null) {
                out.println(excId);
            }
            out.println("-->");
        } finally {
            out.close();
        }
    }
    
    protected void failSafeHandleError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            Throwable throwable = (Throwable)request.getAttribute("javax.servlet.error.exception");
            Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");

            response.setContentType("text/html; charset=UTF-8");


            out.println("<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head><title>Error</title></head>\n"
                    + "<body>\n");

            if (statusCode != null) {
                out.println("<h1>" + statusCode + "</h1>");
            }
            if (throwable != null) {
                out.println("<p>An error occurred</p>");
            }
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }

    }
    
}
