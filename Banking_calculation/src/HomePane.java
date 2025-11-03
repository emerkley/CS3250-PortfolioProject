import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;

public class HomePane extends BorderPane {
    public HomePane(BankAppPane app, ObservableList<User> users) {

        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);

        Label title = new Label("Welcome to Merkley’s Family Banking");
        Label nameLabel = new Label("Enter Your Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Type name here");
        
        Label statusLabel = new Label();

        Button createUserBtn = new Button("Create User");
        Button viewUsersBtn = new Button("View User List");

        centerBox.getChildren().addAll(title, nameLabel, nameField, createUserBtn, statusLabel, viewUsersBtn);
        setCenter(centerBox);
        
        createUserBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                User newUser = new User(name, 18);
                newUser.getCheckingAccount().deposit(100);
                newUser.getSavingsAccount().deposit(50);
                newUser.getGoalAccount().setGoal("Vacation", 500);
                newUser.getGoalAccount().deposit(25);

                users.add(newUser);
                statusLabel.setText("User: " + name + " created!");
                nameField.clear();
            } else {
                statusLabel.setText("Please enter a name first!");
            }
        });

        viewUsersBtn.setOnAction(e ->{
        app.showUserListScene();
        });
    }
}