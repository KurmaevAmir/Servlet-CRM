package Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdministratorAccess implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.contains("/registration") || requestURI.contains("/registration.jsp")) {
            HttpSession session = httpRequest.getSession(false);
            if (session != null && !session.getAttribute("role").equals("Администратор")) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ разрешён только администраторам.");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
