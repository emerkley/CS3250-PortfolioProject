import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CheckingPane extends BorderPane {
	private Label checkingLabel;
	
    private UserSelection userSelection;
	
    public CheckingPane(BankAppPane app, User selectedUser) {
    	this.userSelection = new UserSelection(selectedUser);
    	
        Label title = new Label("Checking Account");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        
        checkingLabel = new Label();
        updateLables(); 
        VBox infoBox = new VBox(10, checkingLabel);
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
        checkingLabel.setText("Checking: $" + user.getCheckingAccount().getBalance());
    }
}