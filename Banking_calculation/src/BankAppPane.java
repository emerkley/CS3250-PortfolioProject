import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BankAppPane extends Application {
    private Stage primaryStage;
    private ObservableList<User> users;
    private BankDatabase database = BankDatabase.getDatabase();
    
    public BankDatabase getDatabase() {
        return database;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Loads users from database
        users = FXCollections.observableArrayList(database.getUsers());
        
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
    
    // Scene that shows the users account
    public void UserAcctScene(User selectedUser) {
        UserAcctPane userAcctPane = new UserAcctPane(this, selectedUser);
        Scene userAcctScene = new Scene(userAcctPane, 600, 500);
        primaryStage.setScene(userAcctScene);
    }
    // Scene that shows the checkingPane
    public void CheckingScene(User selectedUser) {
        CheckingPane checkingPane = new CheckingPane(this, selectedUser);
        Scene checkingScene = new Scene(checkingPane, 600, 500);
        primaryStage.setScene(checkingScene);
    }
    // Scene that shows the savingsPane
    public void SavingsScene(User selectedUser) {
        SavingsPane savingsPane = new SavingsPane(this, selectedUser);
        Scene savingsScene = new Scene(savingsPane, 600, 500);
        primaryStage.setScene(savingsScene);
    }
    // Scene that shows the Goalspane	
    public void GoalsScene(User selectedUser) {
        GoalsPane goalsPane = new GoalsPane(this, selectedUser);
        Scene goalsScene = new Scene(goalsPane, 600, 500);
        primaryStage.setScene(goalsScene);
    }
}
