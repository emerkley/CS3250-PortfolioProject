import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.List;

import javafx.collections.FXCollections;

public class SavingsPane extends BorderPane {
    private Label savingsLabel;
    private UserSelection userSelection;
    private ListView<TransactionRecord> transactionListView; 
    private ObservableList<TransactionRecord> transactionList;

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

        // Amount for transactions
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        
        // Take description for transaction
        Label descLabel = new Label("Reason:");
        TextField descField = new TextField();
        descField.setPromptText("Max 20 chars");

        // Transaction buttons
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        HBox buttonBox = new HBox(10, depositBtn, withdrawBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox leftBox = new VBox(10, savingsLabel, amountLabel, amountField, descLabel, descField, buttonBox);
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setStyle("-fx-padding: 20;");
        setLeft(leftBox);
        
        // Transaction list using ListView
        transactionList = FXCollections.observableArrayList();
        transactionListView = new ListView<>(transactionList);
        transactionListView.setPrefHeight(400);
        setCenter(transactionListView);
        loadTransactions(selectedUser.getSavingsAccount().getAccountId());// Load DB transactions

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
                Account savings = userSelection.getSelectedUser().getSavingsAccount();

                boolean success = TransactionManager.withdraw(savings, amount);

                if (!success) {
                    showError("Withdrawal Error", "You do not have enough money to withdraw $" + amount);
                } else {
                    updateLabels();
                    app.getDatabase().updateBalance(
                        savings.getId(), savings.getBalance(), savings.getAccountType()
                    );

                    // Save transaction
                    TransactionRecord tr = new TransactionRecord(
                    	    amount * -1,
                    	    descField.getText(),
                    	    LocalDateTime.now(),
                    	    "Savings",
                    	    "Withdraw"
                    	);
                    addTransaction(tr, savings.getAccountId());
                }

                amountField.clear();
                descField.clear();

            } catch (NumberFormatException ex) {
                showError("Invalid Input", "Please enter a valid numeric amount.");
                amountField.clear();
            }
        });

        // Deposit logic
        depositBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                Account savings = userSelection.getSelectedUser().getSavingsAccount();

                boolean success = TransactionManager.deposit(savings, amount);

                if (!success) {
                    showError("Deposit Error", "Please enter an amount greater than 0.");
                } else {
                    updateLabels();
                    app.getDatabase().updateBalance(
                        savings.getId(), savings.getBalance(), savings.getAccountType()
                    );

                    // Save transaction
                    TransactionRecord tr = new TransactionRecord(
                    	    amount * 1,
                    	    descField.getText(),
                    	    LocalDateTime.now(),
                    	    "Savings",
                    	    "Deposit"
                    	);
                    addTransaction(tr, savings.getAccountId());
                }

                amountField.clear();
                descField.clear();

            } catch (NumberFormatException ex) {
                showError("Invalid Input", "Please enter a valid amount.");
                amountField.clear();
            }
        });
    }

    private void updateLabels() {
        User user = userSelection.getSelectedUser();
        savingsLabel.setText("Savings: $" + user.getSavingsAccount().getBalance());
    }
    
    // ChatGPT suggested to put all alerts below and call to them 
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }
    
    // Add transaction to list
    private void addTransaction(TransactionRecord tr, int userId) {
        transactionList.add(tr);
        BankDatabase.getDatabase().insertTransaction(userId, tr);
    }

    // Load transactions to database
    private void loadTransactions(int userId) {
        transactionList.clear();
        List<TransactionRecord> transactions = BankDatabase.getDatabase().getTransactions(userId);
        transactionList.addAll(transactions);
    }
}