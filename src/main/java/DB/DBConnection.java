package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/servlet_crm";
    private static final String DB_USER = "AVeamM";
    private static final String DB_PASSWORD = "localpassword";

    public static Connection getConnection() {
        Connection connect = null;
        try {
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }
}
