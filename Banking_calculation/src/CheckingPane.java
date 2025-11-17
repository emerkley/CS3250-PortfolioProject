import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
        amountLabel.setStyle("-fx-font-size: 14px;");
        TextField amountField = new TextField();
        amountField.setPromptText("Type money amount here");
        amountField.setPrefWidth(100);

        HBox amountBox = new HBox(10, amountLabel, amountField);
        amountBox.setAlignment(Pos.CENTER_LEFT);

        // deposit & withdraw buttons
        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");

        HBox buttonBox = new HBox(10, depositBtn, withdrawBtn);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        // Center layout
        VBox centerBox = new VBox(15, amountBox, buttonBox);
        centerBox.setAlignment(Pos.CENTER_LEFT);
        centerBox.setStyle("-fx-padding: 20;");
        setCenter(centerBox);

        // Button to return to UserAcct
        Button backBtn = new Button("Back");
        HBox bottomBox = new HBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setStyle("-fx-padding: 10;");
        setBottom(bottomBox);

        backBtn.setOnAction(e -> app.UserAcctScene(selectedUser));
    }
    
    //TODO: add trasnsaciton history to the side & a reason box to withdraw or deposit (either do a type in or a dropbox with list of options
    // Possibly allow a note with it of 20 characters or so ex(Bills Note: Electric, etc) this way you can be able to filter through the transaction histroy

    private void updateLabels() {
        User user = userSelection.getSelectedUser();
        checkingLabel.setText("Checking: $" + user.getCheckingAccount().getBalance());
    }
    
    //TODO: set on aciton event for deposit and withdraw
    

}