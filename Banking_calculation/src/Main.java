
public class Main {
	public static void main(String[] args) {
	
		User u1 = new User("Eric", 24);
        User u2 = new User("James", 17);

        u1.getGoalAccount().setGoal("Jet Ski", 1150);
        u2.getGoalAccount().setGoal("Vacation", 2000);
        
        u1.getCheckingAccount().deposit(1500);
        u1.getGoalAccount().deposit(200);
        
        u2.getCheckingAccount().deposit(500);
        u2.getGoalAccount().deposit(100);

        printUserDetails(u1);
        printUserDetails(u2);
	}
	
	
	// Asked chatGPT on main to see a way to make printing this look cleaner for multiple items
	// and created this to simplify multiple user print lines.
	private static void printUserDetails(User user) {
        System.out.println(user);
        System.out.println("  " + user.getCheckingAccount());
        System.out.println("  " + user.getSavingsAccount());
        System.out.println("  " + user.getGoalAccount());
        System.out.println();
    }
}


// TODO
/* Add in a withdraw and deposit 
 * Add in a delete account button or reset account for goal
 * Complete goal progress (potential goal bar and victory screen?)
 * Make it look pretty :)
 * 
 * (Review)
 * Go over 09_JavaFx file to learn more of OnClick items
 * 
 * 10/5-12 Commits
 * added items to Todo for what i still need to do 
 * Increased use count when entering new name
 * Added in vbox to store users account information and set up a base start
 * 
 * 10/13-19 Commits
 *
 * */
