import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;

// Show the User list on  a scene instead of the home pane
public class UserListPane extends BorderPane {
    private Label userInfoLabel = new Label("Select a user to view details");

    public UserListPane(BankAppPane app, ObservableList<User> users) {
        Label title = new Label("User List: (Select a user)");
        ListView<User> userList = new ListView<>(users);

        Button backBtn = new Button("Back to Home");
        backBtn.setOnAction(e -> app.showHomeScene());
        
        Button viewUserBtn = new Button ("View User");
        viewUserBtn.setDisable(true);
        
        Button deleteUserBtn = new Button("Delete User");
        deleteUserBtn.setDisable(true);
        
        // Used chat to help present the user list information (Can delete when the next scene is set)
        userList.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
        	viewUserBtn.setDisable(newUser == null);
        	deleteUserBtn.setDisable(newUser == null);
        	
        	if (newUser != null) {
        		userInfoLabel.setText("Selected: " + newUser.getName() + " (Acct: " + newUser.getUserAccountNum() + ")");
            }
        	else {
        		userInfoLabel.setText("Select a user from the list");
        	}
        });
        
        VBox infoBox = new VBox(5, userInfoLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);

        VBox rightBox = new VBox(10, title, userList, backBtn, viewUserBtn, deleteUserBtn);
        rightBox.setAlignment(Pos.BOTTOM_LEFT);
        
        viewUserBtn.setOnAction(e -> {
            User selectedUser = userList.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                app.UserAcctScene(selectedUser);
            }
        });

        setCenter(rightBox);
        setLeft(infoBox);
    }
}