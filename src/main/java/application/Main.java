package application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import controller.FractController;
import view.ViewManager;

public class Main extends Application {

	AutoCloseable controller;

	public static void main(String[] args) {
		//launch(args);
		if (args.length == 3) {
			String[] cpxValAsString = {args[1],args[2]};
			new FractController(args[1], args[2]);
		} else {
			new FractController();
		}	
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			new ViewManager();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public void stop() throws Exception {
        if(controller != null) {
            try {
                controller.close();
            } catch (Exception ex) {
                System.err.println("Problem closing! " + ex.toString());
            }
        }
        super.stop();
    }
}
