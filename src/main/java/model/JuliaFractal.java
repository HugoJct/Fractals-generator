package model;

public class JuliaFractal extends Fractal{

	public JuliaFractal(FractalBuilder fb) {
		super(fb);
	}

	public Complex next() {
		Complex zn = f(lastComputed,this.complexConstant);
		if(this.domain.contains(lastComputed)) {
			
			lastComputed = lastComputed.plus(new Complex(0, gap));
			
			return zn;
		}
		return null;
	}
}
