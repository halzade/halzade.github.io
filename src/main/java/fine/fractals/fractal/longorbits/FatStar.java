package fine.fractals.fractal.longorbits;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.Mathematician;
import fine.fractals.math.common.HH;

public class FatStar extends Fractal {

	private Mathematician mathematician = new Mathematician();

	// protected double c = -1.0;
	// protected double p = -1.0;

	public FatStar() {
		super("FatStar");
		ITERATION_MAX = 22000;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 3.5;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 3.5;
		INIT_IMAGE_TARGET_reT = 0.0;
		INIT_IMAGE_TARGET_imX = 0.0;

		OPTIMIZE_SYMMETRY = true;

		ONLY_LONG_ORBITS = true;

		Mathematician.initialize();
	}

	// protected void init(double c, double p) {
	// 	this.c = c;
	// 	this.p = p;
	// }

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.square();
		hh.conjugation();
		hh.square();


		//hh.calculation.reT += c;
		//hh.calculation.reT += p * hh.calculation.ppT;
		//hh.calculation.imX += p * hh.calculation.ppX;

		//// previous iteration
		//hh.calculation.ppT = hh.calculation.pT;
		//hh.calculation.ppX = hh.calculation.pX;
		//hh.calculation.pT = hh.calculation.reT;
		//hh.calculation.pX = hh.calculation.imX;


		hh.plus(originReT, originImX);
	}

	@Override
	public boolean optimize(double reT, double imX) {
		return true;
	}


	@Override
	public void colorsFor(HH hh, int counter, int size) {
		hh.calculation.r = false;
		hh.calculation.g = false;
		hh.calculation.b = false;
		boolean change = false;

		if (mathematician.isFibonacci(counter)) {
			hh.calculation.r = true;
			change = true;
		}
		if (mathematician.isSquare(counter)) {
			hh.calculation.g = true;
			change = true;
		}

		if (!change) {
			hh.calculation.b = true;
		}
	}

}
