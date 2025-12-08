import java.time.LocalDateTime;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CheckingPane extends BorderPane {
    private Label checkingLabel;
    private UserSelection userSelection;
    private ListView<TransactionRecord> transactionListView;
    private ObservableList<TransactionRecord> transactionList;


    public CheckingPane(BankAppPane app, User selectedUser) {
        this.userSelection = new UserSelection(selectedUser);

        Label title = new Label("Checking Account");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10 0 20 0;");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Account info on the left
        checkingLabel = new Label();
        updateLabels();

        VBox infoBox = new VBox(10, checkingLabel);
        infoBox.setAlignment(Pos.TOP_LEFT);
        infoBox.setStyle("-fx-padding: 20;");
        setLeft(infoBox);

        // Amount for trasnsacitons box
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        
        Label descLabel = new Label("Reason:");
        TextField descField = new TextField();
        descField.setPromptText("Max 20 chars");

        // deposit & withdraw buttons
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");

        HBox buttonBox = new HBox(10, depositBtn, withdrawBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox leftBox = new VBox(10, checkingLabel, amountLabel, amountField, descLabel, descField, buttonBox);
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setStyle("-fx-padding: 20;");
        setLeft(leftBox);
        
        transactionList = FXCollections.observableArrayList();
        transactionListView = new ListView<>(transactionList);
        transactionListView.setPrefHeight(400);
        setCenter(transactionListView);
        loadTransactions(selectedUser.getCheckingAccount().getAccountId());// Load DB transactions

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

                    TransactionRecord tr = new TransactionRecord(
                    	    amount * -1,
                    	    descField.getText(),
                    	    LocalDateTime.now(),
                    	    "Checking", 
                    	    "Withdraw"
                    	);
                    addTransaction(tr, checking.getAccountId());
                }

                amountField.clear();
                descField.clear();

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

                    TransactionRecord tr = new TransactionRecord(
                    	    amount,
                    	    descField.getText(),
                    	    LocalDateTime.now(),
                    	    "Checking", 
                    	    "Deposit"
                    	);
                    addTransaction(tr, checking.getAccountId());
                }

                amountField.clear();
                descField.clear();

            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid amount.");
                amountField.clear();
            }
        });
    }
    
    private void updateLabels() {
        User user = userSelection.getSelectedUser();
        checkingLabel.setText("Checking: $" + String.format("%,.2f", user.getCheckingAccount().getBalance()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
    private void addTransaction(TransactionRecord tr, int userId) {
        transactionList.add(tr);
        BankDatabase.getDatabase().insertTransaction(userId, tr);
    }

    private void loadTransactions(int userId) {
        List<TransactionRecord> records = BankDatabase.getDatabase().getTransactions(userId);
        transactionList.setAll(records);
    }
}



