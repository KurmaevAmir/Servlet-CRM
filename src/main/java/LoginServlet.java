import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import DB.Authentication;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (Authentication.check_data_user(username, password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            session.setMaxInactiveInterval(-1);
            resp.sendRedirect("/crm");
        } else {
            req.setAttribute("error", "Неверный логин или пароль. Попробуйте ещё раз.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
