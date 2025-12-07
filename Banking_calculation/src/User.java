
public class User {

	private String name;
	private int userAccountNum;
	private int age;
	private static int accountNumber = 10000;
	private int dbId;
	
	 private CheckingAcct checkingAccount;
	 private SavingsAcct savingsAccount;
	 private GoalSaving goalAccount;
	
	public User(String name, int age) {
		this.name = name;
		this.age = age;
		this.userAccountNum = ++accountNumber;
		this.checkingAccount = new CheckingAcct();
		this.savingsAccount = new SavingsAcct();
		this.goalAccount = new GoalSaving();
	 } 
	
    public void createCheckingAccount() {
        this.checkingAccount = new CheckingAcct();
        System.out.println("Checking account created.");
    }

    public void createSavingsAccount() {
        this.savingsAccount = new SavingsAcct();
        System.out.println("Savings account created.");
    }
    
    // getters and setters
	public int getAge() {return age;}
	public void setAge(int age) {this.age = age;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public static int getAccountNumber() {return accountNumber;}
	public static void setAccountNumber(int accountNumber) 
	{User.accountNumber = accountNumber;}
	public int getUserAccountNum() {return userAccountNum;}
	public void setUserAccountNum(int userAccountNum) 
	{this.userAccountNum = userAccountNum;}
    public int getDbId() { return dbId; }
    public void setDbId(int dbId) { this.dbId = dbId; }


	
	@Override
	public String toString() {
		return "Name: " + name + " age: " + age + 
		" account number: " + userAccountNum;
	}

	public CheckingAcct getCheckingAccount() {return checkingAccount;}

	public void setCheckingAccount(CheckingAcct checkingAccount) 
	{	this.checkingAccount = checkingAccount;}

	public SavingsAcct getSavingsAccount() 
	{	return savingsAccount;}

	public void setSavingsAccount(SavingsAcct savingsAccount) 
	{	this.savingsAccount = savingsAccount;}

	public GoalSaving getGoalAccount() {
		return goalAccount;}

	public void setGoalAccount(GoalSaving goalAccount) {
		this.goalAccount = goalAccount;}
}
