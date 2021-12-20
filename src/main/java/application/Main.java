package application;

import model.Complex;
import model.Fractal;
import model.FractalBuilder;
import model.FractalDefinitionDomain;
import model.FractalDesigner;

public class Main {

	AutoCloseable controller;

	public static void main(String[] args) {
		Fractal f = new FractalBuilder().setComplexConstant(new Complex(-0.7269, 0.1889))
				.setDefinitionDomain(new FractalDefinitionDomain(-1, 1, -1, 1))
				.setGap(0.01)
				.buildJulia();

		if (args.length == 3) {
			String[] cpxValAsString = {args[1],args[2]};
			new FractController(args[1], args[2]);
		} else {
			launch(args);
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
}
