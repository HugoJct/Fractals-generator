import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args) {
		
		//System.out.println(new Complex(4,2).times(new Complex(4,-2)));
		
		double x1 = -1;
		double x2 = 1;
		double y1 = -1;
		double y2 = 1;
		
		double zoom = 300;
		double imagex=(x2-x1)*zoom;
		double imagey=(y2-y1)*zoom;
		
		var img=new BufferedImage((int)imagex, (int)imagey, BufferedImage.TYPE_INT_RGB);
		int r = 64; int g = 224; int b = 208; //turquoise
		int col = (r << 16) | (g << 8) | b;
		File f = new File("MyFile2.png");
		
		
		double i = x1;
		while(i < x2) {
			double j = y1;
			while(j < y2) {
				
				Complex c = new Complex(i,j);
				int k = divergenceIndex(c);
				
			//	System.out.println(k);
				
				if(k == 1000)
					col = 255 | 0 | 0;
				else
					col = 0 | 0 | (k*255/1000);
				
				img.setRGB((int)((i-x1)*zoom), (int)((j-y1)*zoom), RGBFromIndex(k));
				
				
				j+= 1/imagey;
			}
			i+=1/imagex;
		}
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static Complex f(Complex c) {
		return c.times(c).plus(new Complex(-0.7269,0.1889));
	}
	
	static int divergenceIndex(Complex c) {
		int i = 0;
		while(i < 1000-1 & c.getModulus() <= 2.0) {
			c = f(c);
			i++;
		}
		return i;
	}
	
	static int RGBFromIndex(int index) {
		int r = 0, g = 0, b = 0;
		for(int i=0;i<index%256;i++) {
			g++;
		}
		return (r << 16) | (g << 8) | b;
	}
	
}
