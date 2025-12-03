import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;

public class HomePane extends BorderPane {
    public HomePane(BankAppPane app, ObservableList<User> users) {

        VBox formBox = new VBox(10);
        formBox.setAlignment(Pos.CENTER);
        
        Label title = new Label("Welcome to Merkley’s Family Banking");

        Label nameLabel = new Label("User:");
        TextField nameField = new TextField();
        nameField.setPromptText("Type name here");
        nameField.setPrefWidth(100);

        HBox nameBox = new HBox(10, nameLabel, nameField);
        nameBox.setAlignment(Pos.CENTER);

        Label ageLabel = new Label("Age:");
        TextField ageField = new TextField();
        ageField.setPromptText("Type age here");
        ageField.setPrefWidth(60);

        HBox ageBox = new HBox(10, ageLabel, ageField);
        ageBox.setAlignment(Pos.CENTER);
        
        Label statusLabel = new Label();

        Button createUserBtn = new Button("Create User");
        Label Other = new Label("Or");
        Button viewUsersBtn = new Button("View User List");

        formBox.getChildren().addAll(title, nameBox, ageBox, createUserBtn, Other,  viewUsersBtn,  statusLabel);
        setCenter(formBox);
        
        createUserBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            
            if (!name.isEmpty() && !ageText.isEmpty() ) 
            try {
            	int age = Integer.parseInt(ageText);
                User newUser = new User(name, age);
                
                BankDatabase.getDatabase().addUser(newUser);

                users.add(newUser);
                statusLabel.setText("User: " + name + " Age:" + age + " created!");
                nameField.clear();
                ageField.clear();
            } catch (NumberFormatException ex) {
            	statusLabel.setText("Please enter a valid number for age!");
            }
            else {
                statusLabel.setText("Please enter a name and age first!");
            }
        });

        viewUsersBtn.setOnAction(e ->{
        app.showUserListScene();
        });
    }
}