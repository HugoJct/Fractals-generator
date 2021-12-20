package model;

public class MandelbrotFractal extends Fractal{

	public MandelbrotFractal(FractalBuilder fb) {
		super(fb);
	}

	public Complex next() {
		Complex zn = f(this.complexConstant,this.lastComputed);
		if(this.domain.contains(lastComputed)) {
			
			lastComputed = lastComputed.plus(new Complex(0, gap));
			
			return zn;
		}
		return null;
	}

}
