import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BankStatsPane extends VBox{
	   private Label appLabel;
	    private Label userCountLabel;
	    // just basic text for the home page 
	public BankStatsPane() {
		appLabel = new Label("Merkley's underground banking");
		userCountLabel = new Label("Total users: 0");
		
		getChildren().addAll(appLabel, userCountLabel);
		
		setSpacing(10);
        setAlignment(Pos.CENTER);
		
	}
	
	public void setUserCount(int count) {
		userCountLabel.setText("Users: " + count);
	}
}
