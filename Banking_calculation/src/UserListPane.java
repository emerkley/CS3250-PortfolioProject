import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

import javafx.collections.ObservableList;

// Show the User list on  a scene instead of the home pane
public class UserListPane extends BorderPane {

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

        });
        
        deleteUserBtn.setOnAction(e ->{
        	 User selectedUser = userList.getSelectionModel().getSelectedItem();
        	 if (selectedUser != null) {
      
        		 // Asked chat to learn how to write code for confirm deleting a user.
        	        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        	        confirm.setTitle("Confirm Deletion");
        	        confirm.setHeaderText("Delete User");
        	        confirm.setContentText("Are you sure you want to delete " + selectedUser.getName() + "?");

        	        Optional<ButtonType> result = confirm.showAndWait();
        	        if (result.isPresent() && result.get() == ButtonType.OK) {
        	            userList.getItems().remove(selectedUser);
        	            BankDatabase.getDatabase().deleteUser(selectedUser);
        	        }
        	    }
        	 
        });

        VBox rightBox = new VBox(10, title, userList, viewUserBtn, deleteUserBtn, backBtn);
        rightBox.setAlignment(Pos.BOTTOM_LEFT);
        
        viewUserBtn.setOnAction(e -> {
            User selectedUser = userList.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                app.UserAcctScene(selectedUser);
            }
        });

        setCenter(rightBox);
    }
}