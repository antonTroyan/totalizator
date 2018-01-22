package by.troyan.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * LocaleFilter. Works with locales. Check locales if it is null - set default locale (RU)
 */


@WebFilter(urlPatterns = { "/*" },
    initParams = { @WebInitParam(name = "defaultLocale", value = "ru") })
public class LocaleFilter implements Filter {
    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        if(session.getAttribute("locale") == null){
            session.setAttribute("locale", defaultLocale);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        defaultLocale = null;
    }
}
