package view;

import java.util.Objects;

import javax.swing.text.View;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ViewManager {
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    public ViewManager() {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

    }

}
