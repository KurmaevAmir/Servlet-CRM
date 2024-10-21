package Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessToAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session != null && session.getAttribute("username") != null && session.getAttribute("role") != null) {
            String requestURI = httpRequest.getRequestURI();
            if (requestURI.contains("/login") || requestURI.contains("/login.jsp")) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещён. Вы уже авторизованы.");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
