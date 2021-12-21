package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class FractalDesigner {

	private Fractal fr;
	private File f;
	private LinkedList<Thread> tList = new LinkedList<Thread>();

	public FractalDesigner(Fractal f) {
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
		
		int nbThreads = 2;
		int portionSize = (int) imagey / nbThreads;
		
		double startY = fr.getDomain().getMin().getImaginaryPart();
		double portionY = standardDev(startY, fr.getDomain().getMax().getImaginaryPart())/nbThreads;
		double endY = startY + portionY;
		
		long startTime = System.nanoTime();
		
		for(int i=0;i<nbThreads;i++) {
			
			FractalDefinitionDomain fdd = new FractalDefinitionDomain(fr.getDomain().getMin().getRealPart(), fr.getDomain().getMax().getRealPart(), startY, endY);
			Fractal tmp = new FractalBuilder().setComplexConstant(fr.getComplexConstant())
					.setGap(fr.getGap()).setDefinitionDomain(fdd).buildJulia();
			FractalThread t = new FractalThread(tmp, img, i, portionSize);
			t.start();
			tList.add(t);
			startY += portionY;
			endY += portionY;
		}
		
		for(Thread t : tList) {
			try {
				t.join();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		
		long stopTime = System.nanoTime();
		System.out.println("Runtime: "+((stopTime-startTime)/1000000)+"ms");
		
		return img;
	}
	
	public void writeImage(BufferedImage img) {
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {e.printStackTrace();}
	}

}
