package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.FractalDesigner;
import view.ViewManager;

public class FractController {
    
    public FractController(String real, String imaginary) {
        new FractalDesigner(real, imaginary);
    }
    public FractController(ViewManager view) {
        FractalDesigner fractal = new FractalDesigner();
    }

    @FXML
    private TextField imaginary;

    @FXML
    private TextField real;

    @FXML
    void generateFractal(ActionEvent event) {

    }
}
