import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Asked chat and another classmate for assistance on how to make a database in Java

public class BankDatabase {
    private List<User> users;
    private static BankDatabase database;
    
    // shares bankDatabase throughout program
    public static BankDatabase getDatabase() {
        if (database == null) {
            database = new BankDatabase();
        }
        return database;
    }
    
    private BankDatabase() {
        users = new ArrayList<>();
        createUsersTable();
        loadUsersFromDB();
        System.out.println("Loaded users: " + users.size());
    }


    public List<User> getUsers() {
        return users;
    }

    public User findUser(String name) {
        for (User u : users) {
            if (u.getName().equalsIgnoreCase(name)) {
                return u;
            }
        }
        return null;
    }
    
    public void addUser(User user) {
        users.add(user);
        insertUserIntoDB(user);
    }
    
    private void createUsersTable() {
    	String sql = """ 
    			CREATE TABLE IF NOT EXISTS users (
	    			id INTEGER PRIMARY KEY AUTOINCREMENT,
	    			username TEXT NOT NULL,
	    			age INTEGER NOT NULL,
	    			savings REAL DEFAULT 0.0,
	    			checking REAL DEFAULT 0.0,
	    			goals REAL DEFAULT 0.0,
	    			goalName TEXT DEFAULT '',
	    			goalCost REAL DEFAULT 0.0 
	    		); 
	    	""";

        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Used chat's help to create the connection between DB and GUI
    private void loadUsersFromDB() {
        String sql = "SELECT id, username, age, savings, checking, goals, goalName, goalCost FROM users";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("username");
                int age = rs.getInt("age");

                User u = new User(name, age);

                u.getSavingsAccount().deposit(rs.getDouble("savings"));
                u.getCheckingAccount().deposit(rs.getDouble("checking"));
                u.getGoalAccount().setGoal(rs.getString("goalName"), rs.getDouble("goalCost"));
                u.getGoalAccount().deposit(rs.getDouble("goals"));

                int id = rs.getInt("id");
                u.getSavingsAccount().setAccountId(id);
                u.getCheckingAccount().setAccountId(id);
                u.getGoalAccount().setAccountId(id);

                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Insert user from DB
    private void insertUserIntoDB(User user) {
        String sql = "INSERT INTO users(username, age, savings, checking, goals, goalName, goalCost) VALUES(?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setInt(2, user.getAge());
            stmt.setDouble(3, user.getSavingsAccount().getBalance());
            stmt.setDouble(4, user.getCheckingAccount().getBalance());
            stmt.setDouble(5, user.getGoalAccount().getBalance());
            stmt.setString(6, user.getGoalAccount().getGoalName());
            stmt.setDouble(7, user.getGoalAccount().getGoalCost());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    user.getSavingsAccount().setAccountId(id);
                    user.getCheckingAccount().setAccountId(id);
                    user.getGoalAccount().setAccountId(id);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Update balances in account
    public void updateBalance(int accountId, double newBalance, String accountType) {
        // Determine which column to update based on account type
        String column = switch (accountType.toLowerCase()) {
            case "savings" -> "savings";
            case "checking" -> "checking";
            case "goal" -> "goals";
            default -> throw new IllegalArgumentException("Unknown account type: " + accountType);
        };

        String sql = "UPDATE users SET " + column + " = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // Update goal name and cost
    public void updateGoalInfo(int accountId, String goalName, double goalCost) {
    	String sql = "UPDATE users SET goalName = ?, goalCost = ? WHERE id = ?";
    	try (Connection conn = DatabaseConnector.connect();
    			PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.setString(1, goalName);
    		stmt.setDouble(2, goalCost);
    		stmt.setInt(3, accountId);
    		stmt.executeUpdate();
    		} catch (SQLException e) { 
    			e.printStackTrace(); 
    			} 
    	}
    public void deleteUser(User user) {
    	// Remove from memory list
    	users.remove(user);
    	
    	String sql = "DELETE FROM users WHERE id = ?";
    	
    	try(Connection conn = DatabaseConnector.connect();
    		PreparedStatement stmt = conn.prepareStatement(sql)){
    			stmt.setInt(1, user.getSavingsAccount().getAccountId());
    			stmt.executeUpdate();
    		}catch(SQLException e ) {
    			e.printStackTrace();
    		}
    }
    
    
    }








