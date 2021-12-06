package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class FractDesigner implements Runnable {
    
    private double real;
	private double imaginary;

	private double x1 = -0.1;
	private double x2 = 0.1;
	private double y1 = -0.1;
	private double y2 = 0.1;
	
	private double gap = 0.0001;

	private double imagex=(x2-x1)/gap;
	private double imagey=(y2-y1)/gap;
	
	private int r = 64; int g = 224; int b = 208; //turquoise
	private int col = (r << 16) | (g << 8) | b;
	private File f = new File(real + imaginary + ".png");
	private BufferedImage img;

	private int nbrThreads = 4;

	private Complex c;
	private int k;

    public FractDesigner(String real, String imaginary) {
		String v1S, v2S = "";
		
		if (real.charAt(0) == 'm') {
			v1S = "-";
			for (int i = 0 ; i < real.length()-1 ; i++) {
				v1S += real.charAt(i+1);
			}
		} else {
			v1S = real;
		}

		if (imaginary.charAt(0) == 'm') {
			v2S = "-";
			for (int i = 0 ; i < imaginary.length()-1 ; i++) {
				v2S += imaginary.charAt(i+1);
			}
		} else {
			v2S = imaginary;
		}

		this.real = Double.parseDouble(v1S);
		this.imaginary = Double.parseDouble(v2S);
		createFract();
    }
    public FractDesigner() {
        this.real = -1.476;
        this.imaginary = 0;
		createFract();
    }

		// File[] files = new File("./").listFiles();
	private double standardDev(double a, double b) {
		if (a < 0 && b < 0) {
			return b<a?Math.abs(a)-Math.abs(b):Math.abs(b)-Math.abs(a);
		}
		else if (a>0 && b<0) {
			return a+Math.abs(b);
		}
		else if (a<0 && b>0) {
			return Math.abs(a)+b;
		}
		else {
			return b<a?a-b:b-a;
		}
	}	
	
	private void createFract() {

		img =new BufferedImage((int)imagex, (int)imagey, BufferedImage.TYPE_INT_RGB);
		double standardDevX = standardDev(x1, x2);
		double standardDevY = standardDev(y1, y2);
		// we want nbrThread pair
		if (nbrThreads%2==1) {
			nbrThreads+=1;
		}
		for (int i = 0; i<nbrThreads ; i++) {
			Thread t = new Thread();
			t.start();
		} 

		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		} /* finally {
			System.out.println("fractal generated");
		} */
	}	

	/* static boolean checkIfFileExist(File[] files) {
	    for (File file : files) {
	        if (file.isDirectory()) {
	            System.out.println("Directory: " + file.getName());
	            showFiles(file.listFiles()); // Calls same method again.
	        } else {
	            System.out.println("File: " + file.getName());
	        }
    	}
	} */
	
	private Complex f(Complex c) {
		return c.times(c).plus(new Complex(real,imaginary));
	}
	
	private int divergenceIndex(Complex c) {
		int i = 0;
		while(i < 1000-1 & c.getModulus() <= 2.0) {
			c = f(c);
			i++;
		}
		return i;
	}
	
	private int RGBFromIndex(int index) {
		int r = 0, g = 0, b = 0;
		for(int i=0;i<index%256;i++) {
			g++;
		}
		return (r << 16) | (g << 8) | b;
	}
	@Override
	public void run() {
		for (double i = x1 ; i < x2-gap ; i+= gap) {
			for (double j = y1 ; j < y2-gap ; j+= gap) {
				
				c = new Complex(i,j);
				k = divergenceIndex(c);
				
				System.out.println("x: "+imagex+" y: "+imagey+" "+(i-x1)/gap+" "+(j-y1)/gap);

				
				if(k == 1000) {
					img.setRGB((int)((i-x1)/gap), (int)((j-y1)/gap), 0);
				} else {
					col = 0 | 0 | (k*255/1000);
					img.setRGB((int)((i-x1)/gap), (int)((j-y1)/gap), RGBFromIndex(k));
				}
				
			}
		}
		
	}
}
