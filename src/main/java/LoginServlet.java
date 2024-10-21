import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import DB.DBConnection;

import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connect = DBConnection.getConnection();
            String sql = "SELECT m.id, g.name AS role " +
                         "FROM managers m " +
                         "JOIN managers_groups mg ON m.id = mg.manager_id " +
                         "JOIN groups g ON mg.group_id = g.id " +
                         "WHERE email = ? AND password = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String role = resultSet.getString("role");

                HttpSession session = req.getSession();
                session.setAttribute("id", userId);
                session.setAttribute("username", username);
                session.setAttribute("role", role);
                session.setMaxInactiveInterval(-1);

                resp.sendRedirect(req.getContextPath() + "/crm");
            } else {
                req.setAttribute("error", "Неверный логин или пароль. Попробуйте ещё раз!");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
