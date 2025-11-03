import java.util.ArrayList;
import java.util.List;

// Asked chat and another classmate for assistance on how to make a database in Java

public class BankDatabase {
    private List<User> users;

    public BankDatabase() {
        users = new ArrayList<>();
        loadSampleData();
    }

    public void addUser(User user) {
        users.add(user);
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

    // Temporary data suggested by chatGPT for user purposes
    private void loadSampleData() {
        User u1 = new User("Alice", 24);
        u1.getCheckingAccount().deposit(500);
        u1.getSavingsAccount().deposit(1200);
        u1.getGoalAccount().deposit(300);

        User u2 = new User("Bob", 65);
        u2.getCheckingAccount().deposit(250);
        u2.getSavingsAccount().deposit(900);
        u2.getGoalAccount().deposit(100);

        users.add(u1);
        users.add(u2);
    }
}