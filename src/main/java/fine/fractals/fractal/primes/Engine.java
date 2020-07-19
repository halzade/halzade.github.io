package fine.fractals.fractal.primes;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.Mathematician;
import fine.fractals.math.common.HH;

public class Engine extends Fractal {

	private Mathematician mathematician = new Mathematician();

	public Engine() {
		super("Engine");

		ITERATION_MAX = 8000;
		// ITERATION_MAX = 22000;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 3;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 3;
		INIT_IMAGE_TARGET_reT = 0.0;
		INIT_IMAGE_TARGET_imX = 0.0;

		Mathematician.initialize();
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.binomial3();
		hh.plus(originReT, originImX);
		hh.conjugation();
	}

	@Override
	public boolean optimize(double reT, double imX) {
		return true;
	}

	@Override
	public void colorsFor(HH hh, int counter, int size) {
		hh.calculation.r = true;
		hh.calculation.g = true;
		hh.calculation.b = true;
	}
}
