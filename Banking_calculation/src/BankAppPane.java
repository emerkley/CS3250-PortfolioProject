import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BankAppPane  extends Application {
    public static void main(String[] args) {
        launch(args); 
    }
    
    // create the pane to connect to the MainPane file
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new MainPane(), 500, 400);

        primaryStage.setTitle("Banking App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
