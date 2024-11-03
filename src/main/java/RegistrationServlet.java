import DB.DBConnection;
import DB.GetManagerData;
import Data.HashingData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/admin/registration")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ разрещён только администраторам.");
            return;
        }
        req.getRequestDispatcher("/admin/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String patronymic = req.getParameter("patronymic");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmation_password");
        String phone_number = req.getParameter("phone_number");
        String role = req.getParameter("role");

        if (!password.equals(confirmPassword)) {
            req.setAttribute("status", "Пароли не совпадают");
            req.getRequestDispatcher("/admin/registration.jsp").forward(req, resp);
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if (GetManagerData.getUserIDWhereEmail(email) != 0) {
                req.setAttribute("status", "Пользователь с таким email уже существует.");
                req.getRequestDispatcher("/admin/registration.jsp").forward(req, resp);
                return;
            }

            connection = DBConnection.getConnection();

            String checkPhone = "SELECT id FROM managers WHERE phone_number = ?";
            preparedStatement = connection.prepareStatement(checkPhone);
            preparedStatement.setString(1, phone_number);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                req.setAttribute("status", "Пользователь с таким номером телефона уже существует.");
                req.getRequestDispatcher("/admin/registration.jsp").forward(req, resp);
                return;
            }

            String hashedPassword = HashingData.hash(password);
            if (hashedPassword == null) {
                resp.sendError(HttpServletResponse.SC_CONFLICT, "Ошибка регистрации пароля");
            }
            String insertUserSQL = "INSERT INTO managers (name, surname, patronymic, email, password, phone_number)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertUserSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, patronymic);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, hashedPassword);
            preparedStatement.setString(6, phone_number);
            preparedStatement.executeUpdate();

            String groupSQL = "INSERT INTO managers_groups (group_id, manager_id) VALUES (?, ?)";
            int managerID = GetManagerData.getUserIDWhereEmail(email);
            int groupID = role.equals("admin") ? 1 : 2;

            PreparedStatement groupStatement = connection.prepareStatement(groupSQL);
            groupStatement.setInt(1, groupID);
            groupStatement.setInt(2, managerID);
            groupStatement.executeUpdate();
            req.setAttribute("status", "Пользователь зарегистрирован.");
            req.getRequestDispatcher("/admin/registration.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("status", "Ошибка базы данных.");
            req.getRequestDispatcher("/admin/registration.jsp").forward(req, resp);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
