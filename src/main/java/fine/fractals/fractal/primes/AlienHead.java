package fine.fractals.fractal.primes;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.Mathematician;
import fine.fractals.math.common.HH;

public class AlienHead extends Fractal {

	private Mathematician mathematician = new Mathematician();

	public AlienHead() {
		super("AlienHead");
		ITERATION_MAX = 8000;
		ITERATION_MIN = 42;
		INIT_AREA_DOMAIN_SIZE = 3.5;
		INIT_DOMAIN_TARGET_reT = -0.25;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 3.8;
		INIT_IMAGE_TARGET_reT = 0.4;
		INIT_IMAGE_TARGET_imX = 0.0;

		OPTIMIZE_SYMMETRY = true;

		Mathematician.initialize();
	}

	@Override
	public void colorsFor(HH hh, int counter, int size) {
		hh.calculation.r = false;
		hh.calculation.g = false;
		hh.calculation.b = false;
		boolean change = false;

		if (mathematician.isPrime(counter)) {
			hh.calculation.r = true;
			change = true;
		}
		if (mathematician.isPrime(size)) {
			hh.calculation.g = true;
			change = true;
		}

		if (!change) {
			hh.calculation.b = true;
		}
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.kingChange(mathematician);
		hh.plus(originReT, originImX);
		hh.square();
		hh.reciprocal();
	}

	@Override
	public boolean optimize(double reT, double imX) {
		return true;
	}

}
