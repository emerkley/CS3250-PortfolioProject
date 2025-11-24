
public class GoalSaving extends Account {
	private String goalName;
	private double goalCost;
	private double balance;     
	
	public GoalSaving() {
		super("Goal");
        this.goalCost = 0;
        this.balance = 0;
	}
	public void setGoal(String name, double goalAmount) {
		this.goalName = name;
		this.goalCost = goalAmount;
		this.balance = 0;
	}
	
    public String getGoalName() {
        return goalName;
    }
    
    public double getGoalCost() {   
        return goalCost;            
    }
    
    public double getBalance() {
        return balance;
    }
    
	public double getProgress() {
        if (goalCost <= 0) return 0;
        return Math.min(100, (super.getBalance() / goalCost) * 100);
	}
	
    public boolean isComplete() {
        return super.getBalance() >= goalCost;
    }
}
