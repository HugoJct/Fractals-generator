package model;

public abstract class Fractal {
	
	protected final Complex complexConstant;
	protected final FractalDefinitionDomain domain;
	protected double gap;
	protected Complex lastComputed;
	
	public Fractal(FractalBuilder fb) {
		this.complexConstant = fb.getComplexConstant();
		this.domain = fb.getDomain();
		this.gap = fb.getGap();
		this.lastComputed = fb.getDomain().getMin();
	}
	
	protected Complex f(Complex z, Complex c) {
		return z.times(z).plus(c);
	}
	
	public abstract int getCurrentDivergenceIndex();
	
	public abstract Complex next();
	
	public int getNextDivergenceIndex() {
		if (next() != null)
			return getCurrentDivergenceIndex();
		else
			return -1;
	}
}
