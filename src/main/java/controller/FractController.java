package controller;

import model.FractDesigner;

public class FractController {
    
    public FractController(String real, String imaginary) {
        new FractDesigner(real, imaginary);
    }
    public FractController() {
        new FractDesigner();
    }
}
