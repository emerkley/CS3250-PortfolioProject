import javafx.scene.layout.BorderPane;

public class MainPane extends BorderPane{
	public MainPane() {
		// call other panes to use in the BankAppPane for the home screen
        BankStatsPane statsPane = new BankStatsPane();
        setTop(statsPane);

        HomePane homePane = new HomePane();
        setCenter(homePane);
    }
}
