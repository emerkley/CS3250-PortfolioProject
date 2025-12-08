import java.time.LocalDateTime;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GoalsPane extends BorderPane {
    private Label goalsLabel;
    private Label progressLabel;     
    private Label goalCompleteLabel;  
    private UserSelection userSelection;
    private ObservableList<TransactionRecord> transactionList;
    private ListView<TransactionRecord> transactionListView;

    public GoalsPane(BankAppPane app, User selectedUser) {
        this.userSelection = new UserSelection(selectedUser);

        // Title
        Label title = new Label("Goals");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        // Account label
        goalsLabel = new Label();
        progressLabel = new Label("Progress: 0%");
        goalCompleteLabel = new Label();     
        updateLabels();
        
        Button createGoalBtn = new Button("Create/Change Goal");  
        createGoalBtn.setOnAction(e -> handleGoalCreation(
                userSelection.getSelectedUser().getGoalAccount()
        ));
        
        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        
        Label descLabel = new Label("Reason:");
        TextField descField = new TextField();
        descField.setPromptText("Max 20 chars");
        
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");
        HBox buttonBox = new HBox(10, depositBtn, withdrawBtn);
        buttonBox.setAlignment(Pos.TOP_LEFT);

        VBox leftColumn = new VBox(10, goalsLabel, progressLabel, createGoalBtn, amountLabel, amountField, 
        		 descLabel, descField, buttonBox);
        leftColumn.setAlignment(Pos.TOP_LEFT);
        leftColumn.setStyle("-fx-padding: 15;");
        
        transactionList = FXCollections.observableArrayList();
        transactionListView = new ListView<>(transactionList);
        transactionListView.setPrefWidth(300);
        transactionListView.setPrefHeight(400);
        loadTransactions(selectedUser.getGoalAccount().getAccountId());

        VBox rightColumn = new VBox(transactionListView);
        rightColumn.setAlignment(Pos.TOP_CENTER);
        rightColumn.setStyle("-fx-padding: 0;");
        
        HBox centerColumns = new HBox(40, leftColumn, rightColumn);
        centerColumns.setAlignment(Pos.TOP_CENTER);
        centerColumns.setStyle("-fx-padding: 20;");

        setCenter(centerColumns);

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
                GoalSaving acc = userSelection.getSelectedUser().getGoalAccount();

                boolean success = TransactionManager.withdraw(acc, amount);

                if (!success) {
                    showAlert("Withdrawal Error", "You do not have enough money to withdraw $" + amount);
                } else {
                    updateLabels();
                    BankDatabase.getDatabase().updateBalance(acc.getAccountId(), acc.getBalance(), acc.getAccountType());

                    TransactionRecord tr = new TransactionRecord(
                            amount * -1,
                            descField.getText(),
                            LocalDateTime.now(),
                            "Goal",
                            "Withdraw"
                    );

                    addTransaction(tr, acc.getAccountId());
                }

                amountField.clear();
                descField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric amount.");
            }
        });

        // Deposit logic
        depositBtn.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                GoalSaving acc = userSelection.getSelectedUser().getGoalAccount();

                boolean success = TransactionManager.deposit(acc, amount);

                if (!success) {
                    showAlert("Deposit Error", "Please enter an amount greater than 0.");
                } else {
                    updateLabels();
                    BankDatabase.getDatabase().updateBalance(acc.getAccountId(), acc.getBalance(), acc.getAccountType());

                    TransactionRecord tr = new TransactionRecord(
                            amount,
                            descField.getText(),
                            LocalDateTime.now(),
                            "Goal",
                            "Deposit"
                    );

                    addTransaction(tr, acc.getAccountId());
                }

                amountField.clear();
                descField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric amount.");
            }
        });
    }
    
    private void addTransaction(TransactionRecord tr, int userId) {
        transactionList.add(tr);
        BankDatabase.getDatabase().insertTransaction(userId, tr);
    }

    private void loadTransactions(int userId) {
        List<TransactionRecord> records = BankDatabase.getDatabase().getTransactions(userId);
        transactionList.setAll(records);
    }
    public void setSelectedUser(User newUser) {
        userSelection.setSelectedUser(newUser);
        GoalSaving acc = newUser.getGoalAccount();
        loadTransactions(acc.getAccountId());   
        updateLabels();                  
    }
    
    // If there is already a goal (asked chatGPT to help load goal and update goals to and from DB)
    private void handleGoalCreation(GoalSaving goalAcc) {
        if (goalAcc.getGoalName() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Goal Already Set!");
            alert.setHeaderText("You are currently working toward: " + goalAcc.getGoalName());
            alert.setContentText("Would you like to change goals?");
            ButtonType yes = new ButtonType("Yes");
            ButtonType no = new ButtonType("No");
            alert.getButtonTypes().setAll(yes, no);

            var result = alert.showAndWait();
            if (result.get() == no) return;
        }

        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("New Goal");
        nameDialog.setHeaderText("Enter the name of your new goal:");
        String goalName = nameDialog.showAndWait().orElse(null);
        if (goalName == null || goalName.isBlank()) return;

        TextInputDialog costDialog = new TextInputDialog();
        costDialog.setTitle("Goal Cost");
        costDialog.setHeaderText("Enter the cost of your goal:");
        String costStr = costDialog.showAndWait().orElse(null);
        if (costStr == null) return;

        try {
            double cost = Double.parseDouble(costStr);
            goalAcc.setGoal(goalName, cost);
            updateLabels();
            // Save goal name and cost to DB
            BankDatabase.getDatabase().updateGoalInfo(goalAcc.getAccountId(), goalName, cost);
            // Also save current saved amount toward the goal
            BankDatabase.getDatabase().updateBalance(goalAcc.getAccountId(), goalAcc.getBalance(), goalAcc.getAccountType());

        } catch (NumberFormatException e) {
            showError("Invalid Cost", "Please enter a valid number.");
        }
    } 

    // Update what the current goal is
    private void updateLabels() {
        GoalSaving acc = userSelection.getSelectedUser().getGoalAccount();

        if (acc.getGoalName() == null) {
            goalsLabel.setText("No goal created yet.");
            progressLabel.setText("Progress: 0%");
            goalCompleteLabel.setText("");
            return;
        }

        goalsLabel.setText("Goal: " + acc.getGoalName() + 
            "\nSaved: $" + String.format("%,.2f",  acc.getBalance()) + " / $" + String.format("%,.2f", acc.getGoalCost()));

        progressLabel.setText("Progress: " + String.format("%.2f", acc.getProgress()) + "%");

        // Check for completion
        if (acc.isComplete()) {
            goalCompleteLabel.setText("Goal Completed! You can now purchase your " + acc.getGoalName());
        } else {
            goalCompleteLabel.setText(""); // hide if not completed
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.getButtonTypes().setAll(ButtonType.OK);
        alert.showAndWait();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}


