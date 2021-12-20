package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FractalDesigner2 {

	private Fractal fr;
	private File f;

	public FractalDesigner2(Fractal f) {
		this.fr = f;
		this.f = new File(f.getComplexConstant().getRealPart() + "_" + f.getComplexConstant().getImaginaryPart() + ".png");
	}
	
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
	
	public BufferedImage drawFractal() {
		double imagex=(fr.getDomain().getMax().getRealPart() - fr.getDomain().getMin().getRealPart()) / fr.getGap();
		double imagey=(fr.getDomain().getMax().getImaginaryPart() - fr.getDomain().getMin().getImaginaryPart()) / fr.getGap();
		BufferedImage img = new BufferedImage((int)Math.round(imagex), (int)Math.round(imagey), BufferedImage.TYPE_INT_RGB);
		
		int nbThreads = 1;
		int portionSize = (int) imagey / nbThreads;
		
		System.out.println(imagex +" "+imagey);
		
		double startY = fr.getDomain().getMin().getImaginaryPart();
		double portionY = standardDev(startY, fr.getDomain().getMax().getImaginaryPart())/nbThreads;
		double endY = startY + portionY;
		for(int i=0;i<nbThreads;i++) {
			
			FractalDefinitionDomain fdd = new FractalDefinitionDomain(fr.getDomain().getMin().getRealPart(), fr.getDomain().getMax().getRealPart(), startY, endY);
			System.out.println(fdd);
			Fractal tmp = new FractalBuilder().setComplexConstant(fr.getComplexConstant())
					.setGap(fr.getGap()).setDefinitionDomain(fdd).buildJulia();
			FractalThread t = new FractalThread(tmp, img, i, portionSize);
			t.start();
			System.out.println("Thread "+i+" launched");
			startY += portionY;
			endY += portionY;
			try {
				t.join();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		return img;
	}
	
	public void writeImage(BufferedImage img) {
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {e.printStackTrace();}
	}

}
