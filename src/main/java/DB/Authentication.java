package DB;

import java.sql.*;

public class Authentication {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/servlet_crm";
    private static final String DB_USER = "AVeamM";
    private static final String DB_PASSWORD = "localpassword";

    public static boolean check_data_user(String username, String password) {
        boolean result = false;
        try
        {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        try (Connection connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connect.prepareStatement("SELECT * FROM managers WHERE email = ? AND password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
