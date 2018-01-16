package de.jambonna.lolpapers.web.components;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;


@WebFilter(urlPatterns = { "/internal/*" })
public class InternalRequestFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        // Forbids direct access
        if (sr.getDispatcherType() != DispatcherType.FORWARD 
                && sr1 instanceof HttpServletResponse) {
            HttpServletResponse response = (HttpServletResponse)sr1;
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        fc.doFilter(sr, sr1);
    }

    @Override
    public void destroy() {
    }

}
