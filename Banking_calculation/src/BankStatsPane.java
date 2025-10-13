import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BankStatsPane extends VBox{
	 private Label usersLabel;
	 private Label appLabel;
	 private Label userCountLabel;
	 
	    // just basic text for the home page 
	public BankStatsPane() {
		appLabel = new Label("Merkley's underground banking");
		userCountLabel = new Label("Total users: 0");
		usersLabel = new Label("No users yet.");
		
		getChildren().addAll(appLabel, userCountLabel);
		setSpacing(10);
        setAlignment(Pos.CENTER);
		
	}
	
    public void updateUsers(List<User> users) {
    	int count = users.size();
    	userCountLabel.setText("Total users: " + count);
    	
        if (users.isEmpty()) {
            usersLabel.setText("No users created yet.");
        } else {
            StringBuilder sb = new StringBuilder("Users:\n");
            for (User u : users) {
                sb.append("- ").append(u.getName()).append("\n");
            }
            usersLabel.setText(sb.toString());
        }
    }
}