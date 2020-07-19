package fine.fractals.fractal.conjugation;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public class Lotus extends Fractal {

	public Lotus() {
		super("Lotus");
		ITERATION_MAX = 8000;
		ITERATION_MIN = 42;
		// ITERATION_MAX = 80000;
		// ITERATION_MIN = 420;

		INIT_AREA_DOMAIN_SIZE = 3.5;
		INIT_DOMAIN_TARGET_reT = -0.2;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = INIT_AREA_DOMAIN_SIZE;
		INIT_IMAGE_TARGET_reT = INIT_DOMAIN_TARGET_reT;
		INIT_IMAGE_TARGET_imX = INIT_DOMAIN_TARGET_imX;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.conjugation();
		hh.square();
		hh.plus(originReT, originImX);
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
