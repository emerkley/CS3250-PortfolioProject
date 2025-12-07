public class UserSelection {

    private User selectedUser;

    public UserSelection(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }
    
    public void setSelectedUser(User newUser) { 
    	this.selectedUser = newUser; 
    }
    
    public String getUserSummary() {
        return "User: " + selectedUser.getName() +
               "\nChecking: $" + selectedUser.getCheckingAccount().getBalance() +
               "\nSavings: $" + selectedUser.getSavingsAccount().getBalance() +
               "\nGoal: " + selectedUser.getGoalAccount().getGoalName() +
               " $" + selectedUser.getGoalAccount().getBalance() +
               " (" + selectedUser.getGoalAccount().getProgress() + "%)";
    }

    public void depositToChecking(double amount) {
        selectedUser.getCheckingAccount().deposit(amount);
    }

    public void withdrawFromChecking(double amount) {
        selectedUser.getCheckingAccount().withdraw(amount);
    }

    public void depositToSavings(double amount) {
        selectedUser.getSavingsAccount().deposit(amount);
    }

    public void withdrawFromSavings(double amount) {
        selectedUser.getSavingsAccount().withdraw(amount);
    }
 
}