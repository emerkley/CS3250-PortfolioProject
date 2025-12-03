import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CheckingPane extends BorderPane {
    private Label checkingLabel;
    private UserSelection userSelection;


    public CheckingPane(BankAppPane app, User selectedUser) {
        this.userSelection = new UserSelection(selectedUser);

        Label title = new Label("Checking Account");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10 0 20 0;");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Account info on the left
        checkingLabel = new Label();
        checkingLabel.setStyle("-fx-font-size: 14px;");
        updateLabels();

        VBox infoBox = new VBox(10, checkingLabel);
        infoBox.setAlignment(Pos.TOP_LEFT);
        infoBox.setStyle("-fx-padding: 20;");
        setLeft(infoBox);

        // Amount for trasnsacitons box
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        HBox amountBox = new HBox(10, amountLabel, amountField);
        amountBox.setAlignment(Pos.CENTER_LEFT);

        // deposit & withdraw buttons
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");

        HBox buttonBox = new HBox(10, depositBtn, withdrawBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox centerBox = new VBox(15, amountBox, buttonBox);
        centerBox.setAlignment(Pos.CENTER_LEFT);
        centerBox.setStyle("-fx-padding: 20;");
        setCenter(centerBox);

        Button backBtn = new Button("Back");
        HBox bottomBox = new HBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 10;");
        setBottom(bottomBox);

        backBtn.setOnAction(e -> app.UserAcctScene(selectedUser));
        
        //Withdraw logic
        withdrawBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                Account checking = userSelection.getSelectedUser().getCheckingAccount();

                boolean success = TransactionManager.withdraw(checking, amount);

                if (!success) {
                    showAlert("Withdrawal Error", "You do not have enough money to withdraw $" + amount);
                } else {
                    updateLabels();
                    BankDatabase.getDatabase().updateBalance(
                        checking.getAccountId(),
                        checking.getBalance(),
                        checking.getAccountType()
                    );
                }

                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric amount.");
                amountField.clear();
            }
        });
        
        // Deposit logic
        depositBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                Account checking = userSelection.getSelectedUser().getCheckingAccount();

                boolean success = TransactionManager.deposit(checking, amount);

                if (!success) {
                    showAlert("Deposit Error", "Please enter an amount greater than 0.");
                } else {
                    updateLabels();
                    BankDatabase.getDatabase().updateBalance(
                        checking.getAccountId(),
                        checking.getBalance(),
                        checking.getAccountType()
                    );
                }

                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric amount.");
                amountField.clear();
            }
        });
    }
    
    private void updateLabels() {
        User user = userSelection.getSelectedUser();
        checkingLabel.setText("Checking: $" + user.getCheckingAccount().getBalance());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
    
}
   




