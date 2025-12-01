
public class GoalSaving extends Account {
	private String goalName;
	private double goalCost;   
	
	public GoalSaving() {
		super("Goal");
        this.goalCost = 0;
        this.goalName = null;
	}
	public void setGoal(String name, double goalAmount) {
	    this.goalName = name;
	    this.goalCost = goalAmount;
	}
	
    public String getGoalName() {
        return goalName;
    }
    
    public double getGoalCost() {   
        return goalCost;            
    }
    
    public double getBalance() {
    	return super.getBalance();
    }
    
	public double getProgress() {
        if (goalCost <= 0) return 0;
        return Math.min(100, (super.getBalance() / goalCost) * 100);
	}
	
    public boolean isComplete() {
        return super.getBalance() >= goalCost;
    }
}
