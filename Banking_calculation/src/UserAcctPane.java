import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserAcctPane extends BorderPane {
    private Label nameLabel;
    private Label checkingLabel;
    private Label savingsLabel;
    private Label goalLabel;

    private UserSelection userSelection;

    public UserAcctPane(BankAppPane app, User selectedUser) {
        this.userSelection = new UserSelection(selectedUser);

        // Title / User Info
        nameLabel = new Label("User: " + selectedUser.getName() + " (Acct#: " + selectedUser.getUserAccountNum() + ")");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        BorderPane.setAlignment(nameLabel, Pos.CENTER);
        setTop(nameLabel);

        // Account info labels
        checkingLabel = new Label();
        savingsLabel = new Label();
        goalLabel = new Label();
        updateLabels(); // Show current balances

        VBox infoBox = new VBox(10, checkingLabel, savingsLabel, goalLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setStyle("-fx-padding: 20;");
        setLeft(infoBox);

        // Navigation Buttons
        Button checkingBtn = new Button("View Checking");
        Button savingsBtn = new Button("View Savings");
        Button goalBtn = new Button("View Goal Progress");

        VBox centerBox = new VBox(15, checkingBtn, savingsBtn, goalBtn);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);

        // Back Button at Bottom
        Button backBtn = new Button("Back");
        HBox bottomBox = new HBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 20;");
        setBottom(bottomBox);

        // Button Actions
        checkingBtn.setOnAction(e -> app.CheckingScene(userSelection.getSelectedUser()));
        savingsBtn.setOnAction(e -> app.SavingsScene(userSelection.getSelectedUser()));
        goalBtn.setOnAction(e -> app.GoalsScene(userSelection.getSelectedUser()));
        backBtn.setOnAction(e -> app.showUserListScene());
    }

    // Helper to refresh displayed balances
    private void updateLabels() {
        User user = userSelection.getSelectedUser();
        checkingLabel.setText("Checking: $" + user.getCheckingAccount().getBalance());
        savingsLabel.setText("Savings: $" + user.getSavingsAccount().getBalance());
        goalLabel.setText("Goal (" + user.getGoalAccount().getGoalName() + "): $" 
            + user.getGoalAccount().getBalance() + " (" 
            + user.getGoalAccount().getProgress() + "%)");
    }
}