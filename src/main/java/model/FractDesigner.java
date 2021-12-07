package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FractDesigner {
    
    private double real;
	private double imaginary;

	private double x1 = -0.1;
	private double x2 = 0.1;
	private double y1 = -0.1;
	private double y2 = 0.1;
	
	private double gap = 0.001;
	
	private int r = 64; int g = 224; int b = 208; //turquoise
	private int col = (r << 16) | (g << 8) | b;
	private File f;
	private BufferedImage img;

	private int nbrThreads = 4;

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
		f = new File(real + " " + imaginary + ".png");
		createFract();
    }
    public FractDesigner() {
        this.real = -1.476;
        this.imaginary = 0;
		f = new File(real + " " + imaginary + ".png");
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
	
    static boolean isPowerOfTwo(int n) {
        return (int)(Math.ceil((Math.log(n) / Math.log(2))))
            == (int)(Math.floor(((Math.log(n) / Math.log(2)))));
    }

	private void createFract() {
		double standardDevX = standardDev(x1, x2);
		double standardDevY = standardDev(y1, y2);
		double imagex=(x2-x1)/gap;
		double imagey=(y2-y1)/gap;
		img = new BufferedImage((int)imagex, (int)imagey, BufferedImage.TYPE_INT_RGB);
		
		// we want nbrThread power of two
		if (!isPowerOfTwo(nbrThreads)) {
			nbrThreads+=1;
			while (!isPowerOfTwo(nbrThreads)) {
				nbrThreads+=1;
			}
		}

		double portionX = standardDevX/(nbrThreads/2);
		double portionY = standardDevY/(nbrThreads/2);
		double startX = x1;
		double startY = y1;

		imagex = imagex/(nbrThreads/2);
		imagey = imagey/(nbrThreads/2);
		
		double endX = portionX;
		double endY = portionY;

		FractThread[] listOfProsses = new FractThread[nbrThreads];
		int k = 0;
		for (int i = 0; i<nbrThreads/2 ; i++) {
			for (int j = 0 ; j <nbrThreads/2 ; j++) {
				listOfProsses[k] = new FractThread(startX, endX, startY, endY, gap, imagex, imagey, real, imaginary, this, col);
				listOfProsses[k].start();
				startY += portionY;
				endY += portionY;
				imagey += imagey;
				k+=1;
			}
			startX += portionX;
			endX += portionX;
			imagex += imagex;
			startY = y1;
			endY = standardDevY/(nbrThreads/2);
			imagey = ((y2-y1)/gap)/(nbrThreads/2);
		}
		int j = 0;
		while(j != nbrThreads) {
			for (int i = 0 ; i<nbrThreads ; i++) {
				if (!listOfProsses[i].isAlive()) {
					j+=1;
				}
			}
		} 
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImg() {
		return this.img;
	}

}
