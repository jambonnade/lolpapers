package de.jambonna.lolpapers.web.components.front;

import com.google.gson.Gson;
import de.jambonna.lolpapers.core.app.AppException;
import de.jambonna.lolpapers.core.model.Urls;
import de.jambonna.lolpapers.core.model.Website;
import de.jambonna.lolpapers.web.model.RequestContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base class of all frontend Servlets
 */
public abstract class FrontServletAbstract extends HttpServlet {

    protected boolean preProcess(RequestContext rc, HttpServletResponse response) throws Exception {
        return true;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestContext rc = new RequestContext(req);
        rc.setupInRequest();
        resp.setBufferSize(128000);
        try {
            if (preProcess(rc, resp)) {
                getAction(rc, resp);
            }
        } catch (ServletException | IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            rc.close();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // I don't see any case where this is not to do
        req.setCharacterEncoding("UTF-8");
        
        RequestContext rc = new RequestContext(req);
        rc.setupInRequest();
        try {
            if (preProcess(rc, resp)) {
                postAction(rc, resp);
            }
        } catch (ServletException | IOException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            rc.close();
        }
    }

    protected void getAction(RequestContext rc, HttpServletResponse response) throws Exception {
        responseNotFound(response);
    }
    protected void postAction(RequestContext rc, HttpServletResponse response) throws Exception {
        responseNotFound(response);
    }

    public void responseNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    
    public void jsonResponse(HttpServletResponse response, Object result) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Gson gson = new Gson();
            out.print(gson.toJson(result));
        } finally {
            out.close();
        }
    }
    
    public boolean forward(RequestContext rc, HttpServletResponse response, String path) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(path);
        if (dispatcher != null) {
            dispatcher.forward(rc.getRequest(), response);
            return true;
        }
        return false;
    }
    
    public boolean forwardJsp(RequestContext rc, HttpServletResponse response, String path)
            throws ServletException, IOException {
        return forward(rc, response, "/WEB-INF/jsp/front/" + path);
    }
    public boolean executeJsp(RequestContext rc, HttpServletResponse response, String path)
            throws ServletException, IOException {
        boolean result = forwardJsp(rc, response, path);
        if (!result) {
            responseNotFound(response);
        }
        return result;
    }
    
    public void redirectReferer(RequestContext rc, HttpServletResponse response) throws IOException {
        String referer = rc.getRequest().getHeader("referer");
        if (referer != null) {
            response.sendRedirect(referer);
        } else {
            redirectHome(rc, response);
        }
    }
    
    public boolean redirectToLoginIfNeeded(RequestContext rc, HttpServletResponse response) 
            throws AppException, IOException {
        
        if (!rc.getUser().isExisting()) {
            rc.setCurrentUrlForAfterLogin();
            
            Website w = rc.getWebsiteData();
            if (w != null) {
                String url = rc.getUrl(Urls.USER_CONNECT_URL_ID, w);
                response.sendRedirect(url);
            } else {
                responseNotFound(response);
            }
            return true;
        }
        return false;
    }
    
    
    public void redirectHome(RequestContext rc, HttpServletResponse response) throws IOException {
        String url = rc.getBaseUrl();
        Website w = rc.getWebsiteData();
        if (w != null) {
            url = rc.getUrl(w.getRequestPath());
        }
        response.sendRedirect(url);
    }
        
    public void addSessionSuccess(RequestContext rc, String messageKey, String... messageParams) {
        String message = rc.getMessageBundle().getString(messageKey);
        MessageFormat formatter = new MessageFormat(message, rc.getLocale());
        message = formatter.format(messageParams);
        rc.getUserSession().addInfoTextMessage(message);
    }
    public void addSessionError(RequestContext rc, String messageKey, String... messageParams) {
        String message = rc.getMessageBundle().getString(messageKey);
        MessageFormat formatter = new MessageFormat(message, rc.getLocale());
        message = formatter.format(messageParams);
        rc.getUserSession().addErrorTextMessage(message);
    }
}
