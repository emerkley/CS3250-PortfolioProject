
public class GoalSaving extends Account {
	private String goalName;
	private double goalCost;
	
	public GoalSaving() {
		super("Goal");
	}
	public void setGoal(String name, double goalAmount) {
		this.goalName = name;
		this.goalCost = goalAmount;
	}
	
	public double getProgress() {
		if (goalCost > 0) {
			double progress = (getBalance() / goalCost) * 100;
	        return Math.round(progress * 100.0) / 100.0;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "Account #" + getAccountId() + " Goal: " + goalName +
				" Saved: $" + getBalance() + "/" + goalCost + " = (" +
				getProgress() + "%)";
	}

}
