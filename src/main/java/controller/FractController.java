package controller;

import model.FractalDesigner;

public class FractController {
    
    public FractController(String real, String imaginary) {
        new FractalDesigner(real, imaginary);
    }
    public FractController() {
        new FractalDesigner();
    }
}
