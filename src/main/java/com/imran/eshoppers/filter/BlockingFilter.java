package eshoppers.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class BlockingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        var requestUri
                = ((HttpServletRequest)request)
                .getRequestURI();
        if (requestUri.contains("/")) {
            chain.doFilter(request, response);
        }
    }
}
