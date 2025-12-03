import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

	private static final String DB_URL = "jdbc:sqlite:C:/Users/ericm/git/CS3250-PortfolioProject/Banking_calculation/bank.db";
	
    public static Connection connect() {
        try {
            System.out.println("Connecting to DB at: " + DB_URL);

            java.io.File f = new java.io.File("C:/Users/ericm/git/CS3250-PortfolioProject/Banking_calculation/bank.db");
            System.out.println("DB exists on disk? " + f.exists());
            
            Connection conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);  // Auto commit to DB
            return conn;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}