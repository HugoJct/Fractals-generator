package model;

import java.awt.image.BufferedImage;

public class FractalThread extends Thread {

	private Fractal f;
	BufferedImage img;
	private int count;
	private int offset;

	public FractalThread(Fractal f, BufferedImage i, int count, int offset) {
		this.f = f;
		this.img = i;
		this.count = count;
		this.offset = offset;
	}

	public void run() {
		int x = 0;
		double y = 0;

		try {
			int index;
			synchronized (img) {
				do {
					index = f.getNextDivergenceIndex();
					if(index < 0)
						break;

					double i = f.getLastComputed().getRealPart();
					double j = f.getLastComputed().getImaginaryPart();

					x = (int) (((i - f.getDomain().getMin().getRealPart())) / f.getGap());
					y = ((j - f.getDomain().getMin().getImaginaryPart()) / f.getGap()) + (count * offset) - count;
					
					int color = RGBFromIndex(index); // TODO process index to get color

					img.setRGB(x, (int) y, color);
				} while (index >= 0);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			System.out.println(x + " " + (int)y);
		}
		System.out.println(this.f.getDomain()+" Done !");
	}

	private int RGBFromIndex(int index) {
		int r = 0, g = 0, b = 0;
		for (int i = 0; i < index % 256; i++) {
			g++;
		}
		return (r << 16) | (g << 8) | b;
	}
}
