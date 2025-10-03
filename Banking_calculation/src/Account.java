
public class Account {
	private double balance;
	private int accountId;
	private String accountType;
	
	private static int nextAccountId = 5000;
	
	public Account(String accountType) {
		this.accountId = ++nextAccountId;
		this.balance = 0;
		this.accountType = accountType;
	}
	
	public Account(String accountType, double balance) {
		this.accountId = ++nextAccountId;
		this.balance = 0;
		this.accountType = accountType;
	}
	
	public void deposit(double amount) {
		if (amount > 0) {
			balance += amount;
		}
		else {
			System.out.println("You can't deposit 0 or  a negative number");
		}
		
	}
	public void withdraw(double amount) {
		if (amount > 0 && amount <= balance) {
			balance -= amount;
		}
		else {
			System.out.println("You can't withdraw a negative amount or take out insufficient funds");
		}
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public static int getNextAccountId() {
		return nextAccountId;
	}

	public static void setNextAccountId(int nextAccountId) {
		Account.nextAccountId = nextAccountId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	@Override
	public String toString() {
		return accountType + " Account #" + accountId + " Balance: $" + balance;
	}

}
