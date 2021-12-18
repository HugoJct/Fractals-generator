package model;

import model.FractDesigner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FractThread extends Thread {
    private double x1, x2;
    private double y1, y2;
    private double gap;
    private double imageX, imageY;
    private Complex c;
    private int k;
    private double real;
	  private double imaginary;
    private FractDesigner fract;
    private int col;

    public FractThread(double x1, double x2, double y1, double y2, double gap, double imageX, double imageY, double real, double imaginary, FractDesigner fract, int col) {
      this.x1 = x1;
      this.x2 = x2;
      this.y1 = y1;
      this.y2 = y2;
      this.gap = gap;
      this.imageX = imageX;
      this.imageY = imageY;
      this.real = real;
      this.imaginary = imaginary;
      this.fract = fract;
      this.col = col;
    }

    @Override
    public void run() {
      for (double i = x1 ; i < x2-gap ; i+= gap) {
        for (double j = y1 ; j < y2-gap ; j+= gap) {
          
          c = new Complex(i,j);
          k = divergenceIndex(c);
          
          System.out.println("x: "+imageX+" y: "+imageY+" "+(i-x1)/gap+" "+(j-y1)/gap);
          synchronized(fract.getImg()) {
            if(k == 1000) {
              fract.getImg().setRGB((int)((i-x1)/gap), (int)((j-y1)/gap), 0);
            } else {
              col = 0 | 0 | (k*255/1000);
              fract.getImg().setRGB((int)((i-x1)/gap), (int)((j-y1)/gap), RGBFromIndex(k));
            }
          }
        }
      }
    }

    private int RGBFromIndex(int index) {
      int r = 0, g = 0, b = 0;
      for(int i=0;i<index%256;i++) {
        g++;
      }
      return (r << 16) | (g << 8) | b;
    }

    private int divergenceIndex(Complex c) {
      int i = 0;
      while(i < 1000-1 & c.getModulus() <= 2.0) {
        c = f(c);
        i++;
      }
      return i;
    }

    private Complex f(Complex c) {
      return c.times(c).plus(new Complex(real,imaginary));
    }
}
