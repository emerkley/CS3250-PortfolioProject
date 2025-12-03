public class TransactionManager {

    public static boolean deposit(Account account, double amount) {
        if (amount <= 0) return false;
        account.setBalance(account.getBalance() + amount);
        BankDatabase.getDatabase().updateBalance(account.getAccountId(), account.getBalance(), account.getAccountType());
        return true;
    }

    public static boolean withdraw(Account account, double amount) {
        if (amount <= 0 || amount > account.getBalance()) return false;
        account.setBalance(account.getBalance() - amount);
        BankDatabase.getDatabase().updateBalance(account.getAccountId(), account.getBalance(), account.getAccountType());
        return true;
    }

    public static boolean transfer(Account from, Account to, double amount) {
        if (withdraw(from, amount)) {
            deposit(to, amount);
            return true;
        }
        return false;
    }
}