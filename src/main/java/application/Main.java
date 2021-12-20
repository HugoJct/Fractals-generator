package application;

import model.Complex;
import model.Fractal;
import model.FractalBuilder;
import model.FractalDefinitionDomain;
import model.FractalDesigner2;

public class Main {

	AutoCloseable controller;

	public static void main(String[] args) {
		Fractal f = new FractalBuilder().setDefinitionDomain(new FractalDefinitionDomain(-1, 1, -1, 1))
				.setGap(0.001).setComplexConstant(new Complex(-0.7269, 0.1889)).buildJulia();
		FractalDesigner2 fd = new FractalDesigner2(f);
		fd.writeImage(fd.drawFractal());
	}
}
