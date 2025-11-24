import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SavingsPane extends BorderPane {
    private Label savingsLabel;
    private UserSelection userSelection;

    public SavingsPane(BankAppPane app, User selectedUser) {
        this.userSelection = new UserSelection(selectedUser);

        Label title = new Label("Savings Account");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Account info on the left
        savingsLabel = new Label();
        updateLabels();

        VBox infoBox = new VBox(10, savingsLabel);
        infoBox.setAlignment(Pos.TOP_CENTER);
        infoBox.setStyle("-fx-padding: 20;");
        setLeft(infoBox);

        // Amount for trasnsacitons box
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        HBox amountBox = new HBox(10, amountLabel, amountField);
        amountBox.setAlignment(Pos.CENTER_LEFT);

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");

        HBox buttonBox = new HBox(10, depositBtn, withdrawBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox centerBox = new VBox(15, amountBox, buttonBox);
        centerBox.setAlignment(Pos.CENTER_LEFT);
        centerBox.setStyle("-fx-padding: 20;");
        setCenter(centerBox);

        // Back button
        Button backBtn = new Button("Back");
        HBox bottomBox = new HBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 10;");
        setBottom(bottomBox);

        backBtn.setOnAction(e -> app.UserAcctScene(selectedUser));

        // Withdraw logic
        withdrawBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());

                boolean success = TransactionManager.withdraw(
                        userSelection.getSelectedUser().getSavingsAccount(),
                        amount
                );

                if (!success) {
                    Alert alert = new Alert(AlertType.NONE);
                    alert.setTitle("Withdrawal Error");
                    alert.setContentText("You do not have enough money to withdraw $" + amount);
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    alert.showAndWait();
                } else {
                    updateLabels();
                }

                amountField.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.NONE);
                alert.setTitle("Invalid Input");
                alert.setContentText("Please enter a valid numeric amount.");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                amountField.clear();
            }
        });

        // Deposit logic
        depositBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());

                boolean success = TransactionManager.deposit(
                        userSelection.getSelectedUser().getSavingsAccount(),
                        amount
                );

                if (!success) {
                    Alert alert = new Alert(AlertType.NONE);
                    alert.setTitle("Deposit Error");
                    alert.setContentText("Please enter an amount greater than 0.");
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    alert.showAndWait();
                } else {
                    updateLabels();
                }

                amountField.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.NONE);
                alert.setTitle("Invalid Input");
                alert.setContentText("Please enter a valid numeric amount.");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
                amountField.clear();
            }
        });
    }

    private void updateLabels() {
        User user = userSelection.getSelectedUser();
        savingsLabel.setText("Savings: $" + user.getSavingsAccount().getBalance());
    }
}