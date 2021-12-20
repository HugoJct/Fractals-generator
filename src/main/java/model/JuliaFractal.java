package model;

public class JuliaFractal extends Fractal{

	public JuliaFractal(FractalBuilder fb) {
		super(fb);
	}

	protected Complex next() {
		if(this.domain.contains(lastComputed)) {
			
			if(this.domain.contains((lastComputed.plus(new Complex(0, gap))))) {
				this.lastComputed = lastComputed.plus(new Complex(0, gap));
			} else {
				this.lastComputed = new Complex(this.lastComputed.getRealPart() + gap,this.domain.getMin().getImaginaryPart());
			}
			return lastComputed;	
		}
		return null;
	}

	protected int getCurrentDivergenceIndex() {
		int i = 0;
		Complex lastValue = f(lastComputed,complexConstant);
		while((i < 1000) && (lastValue.getModulus() < 2)) {
			lastValue = f(lastValue,complexConstant);
			i++;
		}
		return i;
	}
}
