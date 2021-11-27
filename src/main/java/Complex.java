public class Complex {

	//Attributes
	private final double realPart;
	private final double imaginaryPart;

	//Constructors 
	public Complex(double real, double imaginary) {
		this.realPart = real;
		this.imaginaryPart = imaginary;
	}

	//Functions 
	public Complex plus(Complex x) {
		double real = this.realPart + x.realPart;
		double imaginary = this.imaginaryPart + x.imaginaryPart;
		return new Complex(real,imaginary);
	}

	public Complex minus(Complex x){
		double real = this.realPart - x.realPart;
		double imaginary = this.imaginaryPart - x.imaginaryPart;
		return new Complex(real,imaginary);
	}

	public Complex times(Complex x) {
		double real = (this.realPart * x.realPart) - (this.imaginaryPart * x.imaginaryPart);
		double imaginary = (this.realPart * x.imaginaryPart) + (this.imaginaryPart * x.realPart);
		return new Complex(real,imaginary);
	}

	public Complex divide(Complex x) {	// I don't remember how to do it 
		return 0;
	}
	
	//Getters
	public double getRealPart() {
		return this.realPart;
	}
	
	public double getImaginaryPart() {
		return this.imaginaryPart;
	}
	
	public Complex getConjugate() {
		return new Complex(this.realPart, -this.imaginaryPart);
	}
	
	public double getModulus() {
		return Math.sqrt(Math.pow(this.realPart,2) + Math.pow(this.imaginaryPart, 2));
	}
	
	public double getArgument() { 	//I don't know how to do it with a computer ^^'
		return 0;
	}

	//Tests
	public boolean equals(Complex x) {
		return (this.imaginaryPart == x.imaginaryPart) && (this.realPart == x.realPart);
	}

	//toString
	public String toString() {
		return realPart + "+" + imaginaryPart + "i";
	}
}
