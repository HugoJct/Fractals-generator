package application;

import controller.FractController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Complex;
import model.Fractal;
import model.FractalBuilder;
import model.FractalDefinitionDomain;
import model.FractalDesigner2;
import view.ViewManager;

public class Main extends Application {

	AutoCloseable controller;

	public static void main(String[] args) {
		Fractal f = new FractalBuilder().setComplexConstant(new Complex(-0.7269, 0.1889)).buildJulia();
		FractalDesigner2 fd = new FractalDesigner2(f);
		fd.writeImage(fd.drawFractal());
	}

	@Override
	public void start(Stage primaryStage) {
		/*try {
			new ViewManager();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
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
