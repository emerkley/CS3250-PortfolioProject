import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SavingsPane extends BorderPane {
	private Label savingsLabel;
	
    private UserSelection userSelection;

	
    public SavingsPane(BankAppPane app, User selectedUser) {
    	this.userSelection = new UserSelection(selectedUser);
    	
        Label title = new Label("Savings Account");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        
        savingsLabel = new Label();
        updateLables(); 
        VBox infoBox = new VBox(10, savingsLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setStyle("-fx-padding: 20;");
        setLeft(infoBox);

        Button backBtn = new Button("Back");
        HBox bottomBox = new HBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        setBottom(bottomBox);

        backBtn.setOnAction(e -> app.UserAcctScene(selectedUser));
    }
    
    private void updateLables() {
    	User user = userSelection.getSelectedUser();
    	savingsLabel.setText("Savings: $" + user.getSavingsAccount().getBalance());
    	}
}


