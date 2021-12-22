package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import model.Complex;
import model.Fractal;
import model.FractalBuilder;
import model.FractalDefinitionDomain;
import model.FractalDesigner;

public class FractController implements Initializable {
    
    @FXML
    private Label imaginaryValue;
    @FXML
    private Label y1Value;
    @FXML
    private Label zoomValue;
    @FXML
    private Label gapValue;
    @FXML
    private Button zoomIn;
    @FXML
    private Button zoomOut;
    @FXML
    private Label x1Value;
    @FXML
    private TextField real;
    @FXML
    private TextField zoomCoef;
    @FXML
    private TextField dimX2;
    @FXML
    private Label x2Value;
    @FXML
    private TextField dimY1;
    @FXML
    private Label realValue;
    @FXML
    private TextField dimX1;
    @FXML
    private TextField dimY2;
    @FXML
    private TextField imaginary;
    @FXML
    private Label y2Value;
    @FXML
    private TextField gap;
    @FXML
    private Button createFract;
    @FXML
    private ImageView fractalView;

    public FractController(String real, String imaginary) {

    }
    public FractController() {
        //FractalDesigner fractal = new FractalDesigner();
    }

    @FXML
    void generateFractal(ActionEvent event) {

        if(imaginary.getText().trim().isEmpty() 
            || real.getText().trim().isEmpty()
            || dimX2.getText().trim().isEmpty()
            || dimX2.getText().trim().isEmpty()
            || dimY1.getText().trim().isEmpty()
            || dimY2.getText().trim().isEmpty()
            || gap.getText().trim().isEmpty()) {
                System.out.println("Some fields are empty, please check");
                return;
        }

        Fractal f = new FractalBuilder().setDefinitionDomain(new FractalDefinitionDomain(
            Double.parseDouble(dimX1.getText()), 
            Double.parseDouble(dimX2.getText()), 
            Double.parseDouble(dimY1.getText()), 
            Double.parseDouble(dimY2.getText())))
            .setGap(Double.parseDouble(gap.getText()))
            .setComplexConstant(new Complex(
                Double.parseDouble(real.getText()), 
                Double.parseDouble(imaginary.getText())))
                .buildJulia();
        
        FractalDesigner designer = new FractalDesigner(f);
        BufferedImage buffImg = designer.drawFractal();
        designer.writeImage(buffImg);
        Image image = convertToFxImage(buffImg);
        fractalView.setImage(image);

        realValue.setText(real.getText());
        imaginaryValue.setText(imaginary.getText());
        gapValue.setText(gap.getText());
        x1Value.setText(dimX1.getText());
        x2Value.setText(dimX2.getText());
        y1Value.setText(dimY1.getText());
        y2Value.setText(dimY2.getText());
        zoomValue.setText(zoomCoef.getText());
    }

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
        return new ImageView(wr).getImage();
    }

    @FXML
    void zoomIn(ActionEvent event) {
        gap.setText(Double.toString(Double.parseDouble(gap.getText())/Double.parseDouble(zoomCoef.getText())));
        dimX1.setText(Double.toString(Double.parseDouble(dimX1.getText())/Double.parseDouble(zoomCoef.getText())));
        dimX2.setText(Double.toString(Double.parseDouble(dimX2.getText())/Double.parseDouble(zoomCoef.getText())));
        dimY1.setText(Double.toString(Double.parseDouble(dimY1.getText())/Double.parseDouble(zoomCoef.getText())));
        dimY2.setText(Double.toString(Double.parseDouble(dimY2.getText())/Double.parseDouble(zoomCoef.getText())));
        generateFractal(event);
    }
    @FXML
    void zoomOut(ActionEvent event) {
        gap.setText(Double.toString(Double.parseDouble(gap.getText())*Double.parseDouble(zoomCoef.getText())));
        dimX1.setText(Double.toString(Double.parseDouble(dimX1.getText())*Double.parseDouble(zoomCoef.getText())));
        dimX2.setText(Double.toString(Double.parseDouble(dimX2.getText())*Double.parseDouble(zoomCoef.getText())));
        dimY1.setText(Double.toString(Double.parseDouble(dimY1.getText())*Double.parseDouble(zoomCoef.getText())));
        dimY2.setText(Double.toString(Double.parseDouble(dimY2.getText())*Double.parseDouble(zoomCoef.getText())));
        generateFractal(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        real.setText("-0.7269");
    	imaginary.setText("0.1889");
        dimX1.setText("-1");
        dimX2.setText("1");
        dimY1.setText("-1");
        dimY2.setText("1");
        gap.setText("0.004");
        zoomCoef.setText("2");
        
    }

}
