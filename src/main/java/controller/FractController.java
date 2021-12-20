package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.FractalDesigner;
import view.ViewManager;

public class FractController implements Initializable {
    
    @FXML
    private TextField imaginary;
    @FXML
    private TextField real;
    @FXML
    private TextField dimX2;
    @FXML
    private TextField dimY1;
    @FXML
    private TextField dimX1;
    @FXML
    private TextField dimY2;
    @FXML
    private Button createFract;

    public FractController(String real, String imaginary) {
        new FractalDesigner(real, imaginary);
    }
    public FractController() {
        //FractalDesigner fractal = new FractalDesigner();
    }

    @FXML
    void generateFractal(ActionEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }

}
