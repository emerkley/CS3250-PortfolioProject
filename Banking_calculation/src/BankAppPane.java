import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BankAppPane extends Application {
    private Stage primaryStage;
    private ObservableList<User> users = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showHomeScene();
        primaryStage.setTitle("Merkley's Underground Banking");
        primaryStage.show();
    }
    // create the homeScene(PResent the home data)
    public void showHomeScene() {
        HomePane homePane = new HomePane(this, users);
        Scene homeScene = new Scene(homePane, 600, 500);
        primaryStage.setScene(homeScene);
    }
    
    // Create the User List and show all the users scene
    public void showUserListScene() {
        UserListPane userListPane = new UserListPane(this, users);
        Scene userListScene = new Scene(userListPane, 600, 500);
        primaryStage.setScene(userListScene);
    }
    
    // Show the Users account (Draw out how screen should work options would include:
    // Option 1: make the screen just display all the accounts on one screen with another scene to show transaction history.
    // Option 2: HAve this start to display the savings account scene and then have in the top a bar the goes to checking scene, goal scene, and transaction scene
    public void UserAcctScene(User selectedUser) {
        UserAcctPane userAcctPane = new UserAcctPane(this, selectedUser);
        Scene userAcctScene = new Scene(userAcctPane, 600, 500);
        primaryStage.setScene(userAcctScene);
    }
    public void CheckingScene(User selectedUser) {
        CheckingPane checkingPane = new CheckingPane(this, selectedUser);
        Scene checkingScene = new Scene(checkingPane, 600, 500);
        primaryStage.setScene(checkingScene);
    }

    public void SavingsScene(User selectedUser) {
        SavingsPane savingsPane = new SavingsPane(this, selectedUser);
        Scene savingsScene = new Scene(savingsPane, 600, 500);
        primaryStage.setScene(savingsScene);
    }
    
    public void GoalsScene(User selectedUser) {
        GoalsPane goalsPane = new GoalsPane(this, selectedUser);
        Scene goalsScene = new Scene(goalsPane, 600, 500);
        primaryStage.setScene(goalsScene);
    }
    

}

/* TODO
 * lower the logic in the Home pane so your panes are just scenes and not being cluttered.
 * lower the amount of logic in the main scene so its just create users and can either have a list of who has an account on the side  
 * Have the BankAPp Pane just be to create scenes
 * 
 * use JAva.sql and chat to help write up a database for the users
*/
