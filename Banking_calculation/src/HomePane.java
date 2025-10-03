
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class HomePane extends BorderPane{
	private BankStatsPane statsPane;
	 private ObservableList<User> users = FXCollections.observableArrayList();
	 
	 private ListView<String> userListView = new ListView<>(); 
	 // W3 schools for more List info https://www.w3schools.com/java/java_arraylist.asp
	
	 private Label checkingLabel = new Label("Checking: ");
	 private Label savingsLabel = new Label("Savings: ");
	 private Label goalLabel = new Label("Goal: ");
	
	public HomePane(BankStatsPane statsPane) {
		this.statsPane = statsPane;
		
		Label homeMessage = new Label("Welcome User");
		setTop(homeMessage);
		BorderPane.setAlignment(homeMessage, Pos.CENTER);
		
		// Let user type in their name to be able to create an account
		 Label nameLabel = new Label("Enter Your Name:");
	     TextField nameField = new TextField();
	     nameField.setPromptText("Type name here...");
	     VBox nameBox = new VBox(5);
	     nameBox.getChildren().addAll(nameLabel, nameField);
	     setCenter(nameBox);
	     
		// Make the buttons to create or view the users
        Button createUserBtn = new Button("Create New User");
        Button viewUsersBtn = new Button("View Existing Users");
        
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(createUserBtn, viewUsersBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        setBottom(buttonBox);
        
        // Write the list of users 10/3
        VBox userListBox = new VBox(5, new Label("Users:"), userListView);
        setRight(userListBox);
        userListBox.setAlignment(Pos.CENTER_LEFT);
        
        // Asked Chat to help with onclick actions most of the code was by it
        // Had some struggles getting the it to work. This 
        createUserBtn.setOnAction(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                User newUser = new User(name, 0);
                users.add(newUser);

                // Update statsPane 
                statsPane.updateUsers(users);
                userListView.getItems().add(newUser.getName() + " (Acct: " + newUser.getUserAccountNum() + ")");
                
                nameField.clear();
            } else {
                System.out.println("Please enter a name first!");
            }
        });
        
        viewUsersBtn.setOnAction(e ->{
        	statsPane.updateUsers(users);
        });
        
        userListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            int index = newVal.intValue();
            if (index >= 0 && index < users.size()) {
                User selected = users.get(index);
                checkingLabel.setText("Checking: $" + selected.getCheckingAccount().getBalance());
                savingsLabel.setText("Savings: $" + selected.getSavingsAccount().getBalance());
                goalLabel.setText("Goal (" + selected.getGoalAccount().getGoalName() + "): $" + selected.getGoalAccount().getBalance());
            }
        });
        
	}
}
