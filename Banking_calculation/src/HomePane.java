
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
	 
	 private ListView<User> userListView = new ListView<>(); 
	 // W3 schools for more List info https://www.w3schools.com/java/java_arraylist.asp
	
	 private Label checkingLabel = new Label("Checking: ");
	 private Label savingsLabel = new Label("Savings: ");
	 private Label goalLabel = new Label("Goal: ");
	 private Label userInfoLabel = new Label("User: ");
	
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
        
        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().add(createUserBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        setBottom(buttonBox);
        
        //Add accounts to left side 
        VBox accountBox = new VBox(5, userInfoLabel, checkingLabel, savingsLabel, goalLabel);
        accountBox.setAlignment(Pos.CENTER_LEFT);
        setLeft(accountBox);
        
        // Write the list of users 10/3
        VBox userListBox = new VBox(5, new Label("Users:"), userListView);
        setRight(userListBox);
        userListBox.setAlignment(Pos.CENTER_LEFT);
        
        
        // Asked Chat to help with onclick actions most of the code was by it
        // Had some struggles getting the it to work.
        createUserBtn.setOnAction(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                User newUser = new User(name, 0);
                
                // Set initial  balance and a base/starter deposit
                newUser.getCheckingAccount().deposit(100);
                newUser.getSavingsAccount().deposit(50);
                newUser.getGoalAccount().setGoal("Vacation", 500);
                newUser.getGoalAccount().deposit(25);

                // add and users to list
                users.add(newUser);
                statsPane.updateUsers(users);
                
                userListView.getItems().add(newUser);
                userListView.getSelectionModel().select(newUser);
               
                nameField.clear();
            } else {
                System.out.println("Please enter a name first!");
            }
            
        });
        // Chat GPT helped with the display user info onClick/when highlighted
        userListView.getSelectionModel().selectedItemProperty().addListener((obs, oldUser, newUser) -> {
            if (newUser != null) {
                displayUserInfo(newUser);
            }
        });
        
	}
    private void displayUserInfo(User user) {
        userInfoLabel.setText("User: " + user.getName() + " (Acct: " + user.getUserAccountNum() + ")");
        checkingLabel.setText("Checking: $" + user.getCheckingAccount().getBalance());
        savingsLabel.setText("Savings: $" + user.getSavingsAccount().getBalance());
        goalLabel.setText("Goal (" + user.getGoalAccount().getGoalName() + "): $" + user.getGoalAccount().getBalance());
    }
}
