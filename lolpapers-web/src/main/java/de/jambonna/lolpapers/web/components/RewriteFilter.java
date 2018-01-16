package de.jambonna.lolpapers.web.components;

import de.jambonna.lolpapers.core.model.Url;
import de.jambonna.lolpapers.web.model.RequestContext;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dispatches a request to an internal request path using App's Urls
 */
@WebFilter(urlPatterns = { "/*" })
public class RewriteFilter implements Filter {


    private static final Logger logger = LoggerFactory.getLogger(RewriteFilter.class);

        
    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) 
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest)request;
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            
            RequestContext rc = new RequestContext(httpRequest);

            String requestPath = rc.getRequestedInternalPath();
            
            logger.info("Processing request '{}'", requestPath);
            Url u = rc.getApp().getSharedData().getUrlForRequestPath(requestPath);
            if (u != null) {
                String identifier = u.getIdentifier();
                logger.debug("Request matches url {}", identifier);

                doRewrite(rc, httpResponse, u);
                return;
            }
        }
        fc.doFilter(request, response);
    }
        
    private void doRewrite(RequestContext rc, HttpServletResponse response, Url url)
            throws IOException, ServletException {
        if (url.isHistory()) {
            // TODO : 301
            response.sendRedirect(rc.getUrl(url.getDestPath()));
        } else {
            RequestDispatcher dispatcher = 
                    rc.getServletContext().getRequestDispatcher("/" + url.getDestPath());
            if (dispatcher != null) {
                dispatcher.forward(rc.getRequest(), response);
            }
        }
    }

    @Override
    public void destroy() {
    }
    
}
