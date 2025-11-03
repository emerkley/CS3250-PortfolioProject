import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class GoalsPane extends BorderPane {
    public GoalsPane(BankAppPane app, User selectedUser) {
        Label title = new Label("Goals");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        Button backBtn = new Button("Back");
        HBox bottomBox = new HBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        setBottom(bottomBox);

        backBtn.setOnAction(e -> app.UserAcctScene(selectedUser));
    }
}