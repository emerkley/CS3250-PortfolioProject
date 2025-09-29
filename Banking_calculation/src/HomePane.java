
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class HomePane extends BorderPane{
	public HomePane() {
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
        
        
        // Asked Chat to help with onclick actions
        // When the button is clicked, it retrieves the text from the nameField and prints it in the console then clears the name
		// from the textbox.
        createUserBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameField.getText();
                if (!name.isEmpty()) {
                    System.out.println("Created user: " + name);
                    nameField.clear();
                } else {
                    System.out.println("Please enter a name first!");
                }
            }
        });
	}
}

