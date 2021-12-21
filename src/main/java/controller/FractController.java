package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Complex;
import model.Fractal;
import model.FractalBuilder;
import model.FractalDefinitionDomain;
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
    private TextField gap;
    @FXML
    private Button createFract;

    public FractController(String real, String imaginary) {

    }
    public FractController() {
        //FractalDesigner fractal = new FractalDesigner();
    }

    @FXML
    void generateFractal(ActionEvent event) {
        System.out.println("Hello World");
        Fractal f = new FractalBuilder().setDefinitionDomain(new FractalDefinitionDomain(Double.parseDouble(dimX1.getText()), Double.parseDouble(dimX2.getText()), 
            Double.parseDouble(dimY1.getText()), Double.parseDouble(dimY2.getText()))).setGap(Double.parseDouble(gap.getText())).setComplexConstant(new Complex(Double.parseDouble(real.getText()), Double.parseDouble(imaginary.getText()))).buildJulia();
        FractalDesigner leDesigner = new FractalDesigner(f);
        leDesigner.writeImage(leDesigner.drawFractal());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        real.setText("-0.7269");
    	imaginary.setText("0.1889");
        dimX1.setText("-1");
        dimX2.setText("1");
        dimY1.setText("-1");
        dimY2.setText("1");
        gap.setText("0.001");
        
    }

}
