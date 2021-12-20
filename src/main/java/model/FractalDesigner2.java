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
	
	public BufferedImage drawFractal() {
		double imagex=(fr.getDomain().getMax().getRealPart() - fr.getDomain().getMin().getRealPart()) / fr.getGap();
		double imagey=(fr.getDomain().getMax().getImaginaryPart() - fr.getDomain().getMin().getImaginaryPart()) / fr.getGap();
		BufferedImage img = new BufferedImage((int)imagex, (int)imagey, BufferedImage.TYPE_INT_RGB);
		
		int nbThreads = 1;
		int portionSize = (int) imagey / nbThreads;
		
		System.out.println(imagex +" "+imagey);
		
		for(int i=0;i<nbThreads;i++) {
			FractalThread t = new FractalThread(fr, img, i, portionSize);
			t.start();
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
