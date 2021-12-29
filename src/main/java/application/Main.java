package application;

import javafx.application.Application;
import javafx.stage.Stage;

import view.ViewManager;

public class Main extends Application {

	AutoCloseable controller;

	public static void main(String[] args) {
		TestLoadAverage.testCompute();
		launch(args);
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
                System.err.println("Problem : closing. " + ex.toString());
            }
        }
        super.stop();
    }

}
