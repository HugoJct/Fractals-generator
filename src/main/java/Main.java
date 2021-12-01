import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {
	
	static double real = -1.476;
	static double imaginary = 0;

	public static void main(String[] args) {
		
		//System.out.println(new Complex(4,2).times(new Complex(4,-2)));
		for (int i = 0 ; i<args.length ; i++) {
			System.out.println(args[i]);
		}
		
		if (args.length == 3) {
			String[] cpxValAsString = {args[1],args[2]};
			setCustomCpxParameter(cpxValAsString);
		}

		// File[] files = new File("./").listFiles();

		double x1 = -0.1;
		double x2 = 0.1;
		double y1 = -0.1;
		double y2 = 0.1;
		
		double gap = 0.001;

		double imagex=(x2-x1)/gap;
		double imagey=(y2-y1)/gap;
		
		var img=new BufferedImage((int)imagex, (int)imagey, BufferedImage.TYPE_INT_RGB);
		int r = 64; int g = 224; int b = 208; //turquoise
		int col = (r << 16) | (g << 8) | b;
		File f = new File(real + imaginary + ".png");
		
		
		double i = x1;
		while(i < x2-gap) {
			double j = y1;
			while(j < y2-gap) {
				
				Complex c = new Complex(i,j);
				int k = divergenceIndex(c);
				
				System.out.println("x: "+imagex+" y: "+imagey+" "+(i-x1)/gap+" "+(j-y1)/gap);

				
				if(k == 1000) {
					img.setRGB((int)((i-x1)/gap), (int)((j-y1)/gap), 0);
				} else {
					col = 0 | 0 | (k*255/1000);
					img.setRGB((int)((i-x1)/gap), (int)((j-y1)/gap), RGBFromIndex(k));
				}
				
				
				
				j += gap;
			}
			i += gap;
		}
		try {
			ImageIO.write(img, "PNG", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	static void setCustomCpxParameter(String[] valuesAsString) {
		String v1S, v2S = "";
		
		if (valuesAsString[0].charAt(0) == 'm') {
			v1S = "-";
			for (int i = 0 ; i < valuesAsString[0].length()-1 ; i++) {
				v1S += valuesAsString[0].charAt(i+1);
			}
		} else {
			v1S = valuesAsString[0];
		}

		if (valuesAsString[1].charAt(0) == 'm') {
			v2S = "-";
			for (int i = 0 ; i < valuesAsString[1].length()-1 ; i++) {
				v2S += valuesAsString[1].charAt(i+1);
			}
		} else {
			v2S = valuesAsString[1];
		}

		real = Double.parseDouble(v1S);
		imaginary = Double.parseDouble(v2S);
	}
	
	static Complex f(Complex c) {
		return c.times(c).plus(new Complex(real,imaginary));
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
