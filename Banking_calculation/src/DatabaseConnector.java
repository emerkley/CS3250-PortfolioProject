import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:sqlite:banking.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}