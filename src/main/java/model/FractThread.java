package model;

public class FractThread extends Thread {
    double x1;
    double x2;
    double y1;
    double y2;
    double gap;
    
    public FractThread(double x1, double x2, double y1, double y2, double gap) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.gap = gap;
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
