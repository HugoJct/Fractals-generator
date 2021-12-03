package view;

import javax.swing.text.View;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class ViewManager {
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    public ViewManager() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, 800, 600);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
    }

	public Stage getMainStage() {
		return mainStage;
	}

}
