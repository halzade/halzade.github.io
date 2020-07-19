package fine.fractals.math.common;

import fine.fractals.math.Mathematician;

/**
 * Do calculation only on HH.
 */
public class HH {

	public double tTemp;
	public double xTemp;
	// public static double yTemp;
	// public static double zTemp;

	/**
	 * calculation Thread context
	 */
	public Calculation calculation = new Calculation();

	public static int NOT = Integer.MIN_VALUE;

	protected int it = 0;

	public void res() {
		it = 0;
	}


	public class Calculation {

		public Calculation() {
		}

		public double reT;
		public double imX;
		// public static double imY;
		// public static double imZ;

		public int pxT;
		public int pxX;
		// public static int pxY;
		// public static int pxZ;

		/* For fractals coloring to screen */
		public boolean r;
		public boolean g;
		public boolean b;

		/* previous iteration */
		public double pT;
		public double pX;
		public double ppT;
		public double ppX;
	}

	public HH() {
	}

	public void plus(double reT, double imX) {
		calculation.reT = calculation.reT + reT;
		calculation.imX = calculation.imX + imX;
		// calculation.imY = me.imY + h.imY;
		// calculation.imZ = me.imZ + h.imZ;
	}

	public String plusOriginGpu() {
		return "imX = imX + oImX;\n"
				+ "imY = imY + oImY;\n"
				+ "imZ = imZ + oImZ;\n"
				+ "reT = reT + oReT;\n";
	}

	public void multiplyBy(double reT, double imX) {
		// tTemp = (me.reT * h.reT) - (me.imX * h.imX) - (me.imY * h.imY) - (me.imZ * h.imZ);
		// xTemp = (me.reT * h.imX) + (h.reT * me.imX) + (me.imY + h.imZ) - (h.imY + me.imZ);
		// yTemp = (me.reT * h.imY) + (h.reT * me.imY) + (me.imZ * h.imX) - (h.imZ * me.imX);
		// zTemp = (me.reT * h.imZ) + (h.reT * me.imZ) + (me.imX * h.imY) - (h.imX * me.imY);
		tTemp = (calculation.reT * reT) - (calculation.imX * imX);
		xTemp = (calculation.reT * imX) + (reT * calculation.imX);

		calculation.reT = tTemp;
		calculation.imX = xTemp;
		// HH.t.imY = yTemp;
		// HH.t.imZ = zTemp;
	}

	public void square() {
		// tTemp = (me.reT * h.reT) - (me.imX * h.imX) - (me.imY * h.imY) - (me.imZ * h.imZ);
		// xTemp = (me.reT * h.imX) + (h.reT * me.imX) + (me.imY + h.imZ) - (h.imY + me.imZ);
		// yTemp = (me.reT * h.imY) + (h.reT * me.imY) + (me.imZ * h.imX) - (h.imZ * me.imX);
		// zTemp = (me.reT * h.imZ) + (h.reT * me.imZ) + (me.imX * h.imY) - (h.imX * me.imY);
		tTemp = (calculation.reT * calculation.reT) - (calculation.imX * calculation.imX);
		xTemp = 2 * calculation.reT * calculation.imX;

		calculation.reT = tTemp;
		calculation.imX = xTemp;
		// HH.t.imY = yTemp;
		// HH.t.imZ = zTemp;
	}

	public void squareA() {
		double t = calculation.reT;
		double x = calculation.imX;
		tTemp = (t * t) - (x * x);
		xTemp = 4 * t * x;

		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}


	public void plusInvert() {
		double a = calculation.reT;
		double b = calculation.imX;

		double quadrance = (a * a) + (b * b);

		tTemp = a / quadrance;
		xTemp = -b / quadrance;

		calculation.reT = calculation.reT + tTemp;
		calculation.imX = calculation.imX + xTemp;
	}

	public void minusInvert() {
		double a = calculation.reT;
		double b = calculation.imX;

		double quadrance = (a * a) + (b * b);

		tTemp = a / quadrance;
		xTemp = -b / quadrance;

		calculation.reT = calculation.reT - tTemp;
		calculation.imX = calculation.imX - xTemp;
	}


	@Deprecated
	public String multiplySelfGpu() {
		return "tTemp = (reT * reT) - (imX * imX) - (imY * imY) - (imZ * imZ);\n"
				+ "xTemp = (reT * imX) + (reT * imX) + (imY + imZ) - (imY + imZ);\n"
				+ "yTemp = (reT * imY) + (reT * imY) + (imZ * imX) - (imZ * imX);\n"
				+ "zTemp = (reT * imZ) + (reT * imZ) + (imX * imY) - (imX * imY);\n"
				+ "reT = tTemp;\n"
				+ "imX = xTemp;\n"
				+ "imY = yTemp;\n"
				+ "imZ = zTemp;\n";
	}

	public void multiplyByScalar(double scalar) {
		calculation.reT = calculation.reT * scalar;
		calculation.imX = calculation.imX * scalar;
		// calculation.imY = me.imY * skalar;
		// calculation.imZ = me.imZ * skalar;
	}

	/**
	 * Q(a*b) = Q(a) * Q(b)
	 */
	public double quadrance() {
		// return (calculation.reT * calculation.reT) + (calculation.imX * calculation.imX) + (calculation.imY * calculation.imY) + (calculation.imZ * calculation.imZ);
		return (calculation.reT * calculation.reT) + (calculation.imX * calculation.imX);
	}

	public String quadranceGPU() {
		return "(reT * reT) + (imX * imX) + (imY * imY) + (imZ * imZ)";
	}

	public void innerProduct(double reT, double imX) {
		calculation.reT = calculation.reT * reT;
		calculation.imX = calculation.imX * imX;
		// calculation.imY = calculation.imY * h.imY;
		// calculation.imZ = calculation.imZ * h.imZ;
	}

	public void conjugation() {
		calculation.imX = -calculation.imX;
		// calculation.imY = -calculation.imY;
		// calculation.imZ = -calculation.imZ;
	}

	public void inverse() {
		double q = quadrance();
		conjugation();
		calculation.reT /= q;
		calculation.imX /= q;
		// calculation.imY /= q;
		// calculation.imZ /= q;
	}

	// TODO
	public void rotate(HH center) {

		center.conjugation();

		this.multiplyBy(center.calculation.reT, center.calculation.imX);
		center.multiplyBy(this.calculation.reT, this.calculation.imX);

		double q = center.quadrance();

		this.calculation.reT /= q;
		this.calculation.imX /= q;
		// this.calculation.imY /= q;
		// this.calculation.imZ /= q;
	}

	/* (a + ib)^3 */
	public void binomial3() {
		tTemp = ((calculation.reT * calculation.reT * calculation.reT) - (3 * calculation.reT * calculation.imX * calculation.imX));
		xTemp = ((3 * calculation.reT * calculation.reT * calculation.imX) - (calculation.imX * calculation.imX * calculation.imX));
		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}

	/* (a + ib)^4 */
	public void binomial4() {
		tTemp = ((calculation.reT * calculation.reT * calculation.reT * calculation.reT)
				- (6 * calculation.reT * calculation.reT * calculation.reT * calculation.imX)
				+ (calculation.imX * calculation.reT * calculation.imX * calculation.imX));
		xTemp = ((4 * calculation.reT * calculation.reT * calculation.reT * calculation.imX)
				- (4 * calculation.reT * calculation.imX * calculation.imX * calculation.imX));
		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}

	/* (a + ib)^5 */
	public void binomial5() {
		tTemp = ((calculation.reT * calculation.reT * calculation.reT * calculation.reT * calculation.reT)
				- (10 * calculation.reT * calculation.reT * calculation.reT * calculation.imX * calculation.imX)
				+ (5 * calculation.reT * calculation.imX * calculation.imX * calculation.imX * calculation.imX));
		xTemp = ((5 * calculation.reT * calculation.reT * calculation.reT * calculation.reT * calculation.imX)
				- (10 * calculation.reT * calculation.reT * calculation.imX * calculation.imX * calculation.imX)
				+ (calculation.imX * calculation.imX * calculation.imX * calculation.imX * calculation.imX));
		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}


	@Deprecated
	public void exp() {
		tTemp = Math.exp(calculation.reT) * Math.cos(calculation.imX);
		xTemp = Math.exp(calculation.reT) * Math.sin(calculation.imX);
		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}

	public void reciprocal() {
		double scale = calculation.reT * calculation.reT + calculation.imX * calculation.imX;
		tTemp = calculation.reT / scale;
		xTemp = -calculation.imX / scale;
		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}

	public double reciprocalCreateReT() {
		double scale = calculation.reT * calculation.reT + calculation.imX * calculation.imX;
		return calculation.reT / scale;
	}

	public double reciprocalCreateImX() {
		double scale = calculation.reT * calculation.reT + calculation.imX * calculation.imX;
		return -calculation.imX / scale;
	}

	@Deprecated
	public void sin() {
		// TODO do much better without the use of transcendental functions
		tTemp = Math.sin(calculation.reT) * Math.cosh(calculation.imX);
		xTemp = Math.cos(calculation.reT) * Math.sinh(calculation.imX);
		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}

	@Deprecated
	public void cos() {
		tTemp = Math.cos(calculation.reT) * Math.cosh(calculation.imX);
		xTemp = -Math.sin(calculation.reT) * Math.sinh(calculation.imX);
		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}

	public void hodgepodgeA() {
		tTemp = calculation.reT * (1 - calculation.reT) - (calculation.imX) * (1 - calculation.imX);
		xTemp = calculation.reT * (1 - calculation.imX) * (calculation.reT) * (1 - calculation.imX);
		calculation.reT = tTemp;
		calculation.imX = xTemp;
	}

	@Deprecated
	public double cosCreateReT() {
		return Math.cos(calculation.reT) * Math.cosh(calculation.imX);
	}

	@Deprecated
	public double cosCreateImX() {
		return -Math.sin(calculation.reT) * Math.sinh(calculation.imX);
	}

	public void loverChange(Mathematician mathematician) {
		if (mathematician.isPrime(it)) {
			if (calculation.reT < 0) {
				calculation.imX = calculation.imX * 2;
			} else {
				calculation.imX = calculation.imX / 2;
			}
		}
		it++;
	}

	public void kingChange(Mathematician mathematician) {
		if (mathematician.isPrime(it)) {
			calculation.imX = -calculation.imX;
		}
		it++;
	}

	public void glassChange(Mathematician mathematician) {
		double x = calculation.imX;
		if (mathematician.isFibonacci(it)) {
			calculation.reT = x * x;
		}
		it++;
	}

	public void crossChange(Mathematician mathematician) {
		double x = calculation.imX;
		double t = calculation.reT;
		if (mathematician.isPrime(it)) {
			calculation.reT = 0.01 / x;
			calculation.imX = 0.01 / t;
		}
		it++;
	}


	public void magicianChange(Mathematician mathematician) {
		if (mathematician.isPrime(it)) {
			if (calculation.reT > 0) {
				calculation.imX = -calculation.imX;
			}
		}
		it++;
	}

	public void warriorChange() {
		if (it % 3 == 0) {
			calculation.reT = -calculation.reT;
		}
		it++;
	}

	public void circleInversion(double t, double x) {
		double d = (t * t) + (x * x);
		calculation.reT = t / d;
		calculation.imX = x / d;
	}
}
