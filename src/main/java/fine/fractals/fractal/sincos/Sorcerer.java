package fine.fractals.fractal.sincos;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public class Sorcerer extends Fractal {

	// private static final int r = 10500;
	// private static final int m = 31;

	public Sorcerer() {
		super("Sorcerer");
		ITERATION_MAX = 8000;
		ITERATION_MIN = 8;

		INIT_AREA_DOMAIN_SIZE = 4.0;
		INIT_DOMAIN_TARGET_reT = 0.5;
		INIT_DOMAIN_TARGET_imX = 0.0;

		OPTIMIZE_SYMMETRY = true;

		INIT_AREA_IMAGE_SIZE = 4.0;
		INIT_IMAGE_TARGET_reT = 0.5;
		INIT_IMAGE_TARGET_imX = 0.0;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.square();
		hh.conjugation();
		hh.plus(originReT, originImX);
		hh.sin();
		hh.plus(originReT, originImX);
	}

	@Override
	public void colorsFor(HH hh, int counter, int size) {
		hh.calculation.r = true;
		hh.calculation.g = true;
		hh.calculation.b = true;
	}

	@Override
	public boolean optimize(double reT, double imX) {
		return true;
	}

}
