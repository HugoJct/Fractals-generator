package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

	/*
	 * Constructeur avec valeurs (réel/img) passées en argument
	 * exemple : m0.45 0.2 <=> -0.45 + 0.2
	 */
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
		f = new File(real + "_" + imaginary + ".png");
		createFract();
    }

	/*
	 * Constructeur avec valeurs (réel/img) par défaut
	 */
    public FractDesigner() {
        this.real = -1.476;
        this.imaginary = 0;
		f = new File(real + "_" + imaginary + ".png");
		createFract();
    }

	/*
	 * Calcul de l'écart type entre a et b
	 */
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
		double standardDevY = standardDev(y1, y2);
		double imagex=(x2-x1)/gap;
		double imagey=(y2-y1)/gap;

		img = new BufferedImage((int)imagex, (int)imagey, BufferedImage.TYPE_INT_RGB);

		// intervalle d'écriture à répartir entre chaque thread (pour 4 thread : portionY = 1/4)
		double portionY = standardDevY/nbrThreads;
		// valeur de départ en écriture pour y
		double startY = y1;
		// valeur de fin en écriture pour y
		double endY = startY + portionY;
		
		double imageyBis = imagey/nbrThreads;
		imagey = imageyBis;
		


		ArrayList<FractThread> listOfProsses = new ArrayList<>();

		// création de tous les threads avec leur intervalle d'écriture en y
		for (int i = 0; i<nbrThreads ; i++) {
			listOfProsses.add(new FractThread(x1, x2, startY, endY, gap, imagex, imagey, real, imaginary, img, col));
			System.out.println("x1 : " + x1 + "\nx2 : " + x2 + "\ny1 : " + startY + "\ny2 : " + endY + "\nportion : " + portionY + "\n#######");
			startY += startY;
			endY += startY;
			imagey += imageyBis;
		}

		// start des threads
		for (int i = 0 ; i<listOfProsses.size() ; i++) {
			listOfProsses.get(i).start();
		}

		// ajout des threads
		for (int i = 0 ; i<listOfProsses.size() ; i++) {
			try {
				listOfProsses.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} 

		// écriture du contenu du buffer (de la fractale) dans le fichier image
		try {
			ImageIO.write(img, "PNG", f);
			System.out.println("Ecriture buffer dans fichier");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public BufferedImage getImg() {
		return this.img;
	}

}
