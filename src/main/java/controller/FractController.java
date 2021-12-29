package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
import model.JuliaFractalList;

public class FractController implements Initializable {

    @FXML
    private Label imaginaryValue;
    @FXML
    private Button zoomOut;
    @FXML
    private Button createMultipleView;
    @FXML
    private Label x1Value;
    @FXML
    private Label resolution;
    @FXML
    private TextField dimX2;
    @FXML
    private CheckBox feedback;
    @FXML
    private TextField dimX1;
    @FXML
    private TextField nbrView;
    @FXML
    private Label stuff6;
    @FXML
    private Label y2Value;
    @FXML
    private Label stuff7;
    @FXML
    private Label stuff4;
    @FXML
    private TextField gap;
    @FXML
    private Label stuff5;
    @FXML
    private Button createFract;
    @FXML
    private Label y1Value;
    @FXML
    private Label zoomValue;
    @FXML
    private Label gapValue;
    @FXML
    private Button zoomIn;
    @FXML
    private Label stuff2;
    @FXML
    private Label stuff3;
    @FXML
    private TextField real;
    @FXML
    private Label stuff1;
    @FXML
    private TextField zoomCoef;
    @FXML
    private Label x2Value;
    @FXML
    private TextField dimY1;
    @FXML
    private Label realValue;
    @FXML
    private TextField dimY2;
    @FXML
    private TextField imaginary;
    @FXML
    private ImageView fractalView;
    @FXML
    private Button up;
    @FXML
    private Button left;
    @FXML
    private Button right;
    @FXML
    private Button down;
    @FXML
    private Button cam;
    @FXML
    private ComboBox<String> fractalComboBox;
    
    private ArrayList<Node> feedbackComponentList = new ArrayList<Node>();
    private boolean feedbackState = true;

    private Fractal f;
    private BufferedImage buffImg;
    private int nbrScreenshort = 1;

    public FractController(String real, String imaginary) {

    }
    public FractController() {
        //FractalDesigner fractal = new FractalDesigner();
    }

    @FXML
    void generateFractal(ActionEvent event) {

        if(isFractalMenuFilled()) {

            f = new FractalBuilder().setDefinitionDomain(new FractalDefinitionDomain(
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
            buffImg = designer.drawFractal();
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
            resolution.setText((int)(Double.parseDouble(dimX2.getText())-Double.parseDouble(dimX1.getText())/Double.parseDouble(gap.getText()))
            + " * " 
            + ((int)(Double.parseDouble(dimY2.getText())-(Double.parseDouble(dimY1.getText()))/Double.parseDouble(gap.getText()))));
        }
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
        if (isZoomFilled()) {
            gap.setText(Double.toString(Double.parseDouble(gap.getText())/Double.parseDouble(zoomCoef.getText())));
            dimX1.setText(Double.toString(Double.parseDouble(dimX1.getText())/Double.parseDouble(zoomCoef.getText())));
            dimX2.setText(Double.toString(Double.parseDouble(dimX2.getText())/Double.parseDouble(zoomCoef.getText())));
            dimY1.setText(Double.toString(Double.parseDouble(dimY1.getText())/Double.parseDouble(zoomCoef.getText())));
            dimY2.setText(Double.toString(Double.parseDouble(dimY2.getText())/Double.parseDouble(zoomCoef.getText())));
            generateFractal(event);
        }
    }
    @FXML
    void zoomOut(ActionEvent event) {
        if (isZoomFilled()) {
            gap.setText(Double.toString(Double.parseDouble(gap.getText())*Double.parseDouble(zoomCoef.getText())));
            dimX1.setText(Double.toString(Double.parseDouble(dimX1.getText())*Double.parseDouble(zoomCoef.getText())));
            dimX2.setText(Double.toString(Double.parseDouble(dimX2.getText())*Double.parseDouble(zoomCoef.getText())));
            dimY1.setText(Double.toString(Double.parseDouble(dimY1.getText())*Double.parseDouble(zoomCoef.getText())));
            dimY2.setText(Double.toString(Double.parseDouble(dimY2.getText())*Double.parseDouble(zoomCoef.getText())));
            generateFractal(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        real.setText("-0.7269");
    	imaginary.setText("0.1889");
        dimX1.setText("-1");
        dimX2.setText("1");
        dimY1.setText("-1");
        dimY2.setText("1");
        gap.setText("0.002");
        zoomCoef.setText("2");
        feedbackComponentList.add(realValue);
        feedbackComponentList.add(imaginaryValue);
        feedbackComponentList.add(gapValue);
        feedbackComponentList.add(x1Value);
        feedbackComponentList.add(x2Value);
        feedbackComponentList.add(y1Value);
        feedbackComponentList.add(y2Value);
        feedbackComponentList.add(zoomValue);
        feedbackComponentList.add(stuff1);
        feedbackComponentList.add(stuff2);
        feedbackComponentList.add(stuff3);
        feedbackComponentList.add(stuff4);
        feedbackComponentList.add(stuff5);
        feedbackComponentList.add(stuff6);
        feedbackComponentList.add(stuff7);
        feedbackComponentList.add(resolution);

        List<JuliaFractalList> list = (Arrays.asList(JuliaFractalList.values()));
        ArrayList<String> strList = new ArrayList<String>();
        for (int i = 0 ; i<list.size() ; i++) {
            strList.add(list.get(i).toString());
        }
        fractalComboBox.getItems().setAll(strList);
    }

    @FXML
    void checkFeedbackState(ActionEvent event) {
        
        if (feedbackState) {
            for (int i = 0 ; i<feedbackComponentList.size() ; i++) {
                feedbackComponentList.get(i).setVisible(false);
                feedbackState = false;
            }
        } else {
            for (int i = 0 ; i<feedbackComponentList.size() ; i++) {
                feedbackComponentList.get(i).setVisible(true);
                feedbackState = true;
            }   
        }
    }

    @FXML
    void generateMultipleView(ActionEvent event) {
        if(isFractalMenuFilled() && isZoomFilled() && isNbrOfViewFilled()) {
            int nbrView = Integer.parseInt(this.nbrView.getText());

            for (int i = 0 ; i<nbrView ; i++) {
                f = new FractalBuilder().setDefinitionDomain(new FractalDefinitionDomain(
                    Double.parseDouble(dimX1.getText()), 
                    Double.parseDouble(dimX2.getText()), 
                    Double.parseDouble(dimY1.getText()), 
                    Double.parseDouble(dimY2.getText())))
                    .setGap(Double.parseDouble(gap.getText()))
                    .setComplexConstant(new Complex(
                        Double.parseDouble(real.getText()), 
                        Double.parseDouble(imaginary.getText())))
                        .buildJulia();
                
                FractalDesigner designer = new FractalDesigner(f, (i+1));
                buffImg = designer.drawFractal();
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
                resolution.setText((int)(Double.parseDouble(dimX2.getText())-Double.parseDouble(dimX1.getText())/Double.parseDouble(gap.getText()))
                + " * " 
                + ((int)(Double.parseDouble(dimY2.getText())-(Double.parseDouble(dimY1.getText()))/Double.parseDouble(gap.getText())))); 
                zoomIn(event);   
            }
        }
    }

    @FXML
    void screenshot(ActionEvent event) {
        if (f != null && buffImg != null) {
            FractalDesigner designer = new FractalDesigner(f, "screenshot", nbrScreenshort);
            nbrScreenshort+=1;
            designer.writeImage(buffImg);
        } else {
            System.out.println("fractal not created");
        }
    }

    private boolean isFractalMenuFilled() {
        if(imaginary.getText().trim().isEmpty() 
        || real.getText().trim().isEmpty()
        || dimX2.getText().trim().isEmpty()
        || dimX2.getText().trim().isEmpty()
        || dimY1.getText().trim().isEmpty()
        || dimY2.getText().trim().isEmpty()
        || gap.getText().trim().isEmpty()) {
            System.out.println("Some fields of fractal menu are empty, please check");
            return false;
        } else {
            return true;
        }
    }
    private boolean isZoomFilled() {
        if (zoomCoef.getText().trim().isEmpty()) {
            System.out.println("zoom coef is empty, please check");
            return false;
        } else {
            return true;
        }
    }
    private boolean isNbrOfViewFilled() {
        if (nbrView.getText().trim().isEmpty()) {
            System.out.println("number of view is empty, please check");
            return false;
        } else {
            return true;
        }    
    }

    @FXML
    void shiftUp(ActionEvent event) {
        if (isFractalMenuFilled()) {
            dimY1.setText(Double.toString(Double.parseDouble(dimY1.getText())-0.5));
            dimY2.setText(Double.toString(Double.parseDouble(dimY2.getText())-0.5));
            generateFractal(event);
        }
    }

    @FXML
    void shiftLeft(ActionEvent event) {
        if (isFractalMenuFilled()) {
            dimX1.setText(Double.toString(Double.parseDouble(dimX1.getText())-0.5));
            dimX2.setText(Double.toString(Double.parseDouble(dimX2.getText())-0.5));
            generateFractal(event);
        }
    }

    @FXML
    void shiftRight(ActionEvent event) {
        if (isFractalMenuFilled()) {
            dimX1.setText(Double.toString(Double.parseDouble(dimX1.getText())+0.5));
            dimX2.setText(Double.toString(Double.parseDouble(dimX2.getText())+0.5));
            generateFractal(event);
        }
    }

    @FXML
    void shiftDown(ActionEvent event) {
        if (isFractalMenuFilled()) {
            dimY1.setText(Double.toString(Double.parseDouble(dimY1.getText())+0.5));
            dimY2.setText(Double.toString(Double.parseDouble(dimY2.getText())+0.5));
            generateFractal(event);
        }
    }

}
