package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import model.Complex;
import model.Fractal;
import model.FractalBuilder;
import model.FractalDefinitionDomain;
import model.FractalDesigner;

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
    @FXML
    private ImageView fractalView;

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
        BufferedImage img = leDesigner.drawFractal();
        leDesigner.writeImage(img);
        Image image = convertToFxImage(img);
        fractalView.setImage(image);
        /* Stage thisStage = (Stage) gap.getScene().getWindow();
        thisStage.show(); */ 
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
