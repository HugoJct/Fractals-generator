package application;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Complex;
import model.Fractal;
import model.FractalBuilder;
import model.FractalDefinitionDomain;
import controller.FractController;
import view.ViewManager;

public class Main extends Application {

	AutoCloseable controller;

	public static void main(String[] args) {
		Fractal f = new FractalBuilder().setComplexConstant(new Complex(-0.7269, 0.1889))
				.setDefinitionDomain(new FractalDefinitionDomain(-0.1, 0.1, -0.1, 0.1))
				.setGap(0.01)
				.buildJulia();
		
		Complex c = new Complex(0, 0);
		while(c != null)
			System.out.println(c = f.next());
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			new ViewManager();
		} catch(Exception e) {
			e.printStackTrace();
		}

		/* if (args.length == 3) {
			String[] cpxValAsString = {args[1],args[2]};
			new FractController(args[1], args[2]);
		} else { */
			//new FractController();
		// }	
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
