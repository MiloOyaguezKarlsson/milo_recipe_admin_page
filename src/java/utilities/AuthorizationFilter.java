package utilities;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// filter som gör så att man inte kan gå in på sidan om man inte är inloggad
// utan istället omdirigeras till inloggnings-sidan
// har inte gjort själv
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest reqt = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession sess = reqt.getSession(false);

        String reqURI = reqt.getRequestURI();

        if (reqURI.indexOf("/index.xhtml") >= 0
                || (sess != null && sess.getAttribute("user") != null)
                || reqURI.indexOf("/public/") >= 0
                || reqURI.contains("javax.faces.resource")) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(reqt.getContextPath() + "/faces/index.xhtml");
        }
    }

    @Override
    public void destroy() {
    }

}
