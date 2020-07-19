package fine.fractals.fractal.longorbits;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public class Spirit extends Fractal {

	public Spirit() {
		super("Spirit");
		ITERATION_MAX = 18000;
		ITERATION_MIN = 420;

		INIT_AREA_DOMAIN_SIZE = 5.0;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 5.0;
		INIT_IMAGE_TARGET_reT = 0.0;
		INIT_IMAGE_TARGET_imX = 0.0;

		ONLY_LONG_ORBITS = true;
		OPTIMIZE_SYMMETRY = true;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.square();
		hh.conjugation();
		hh.sin();
		hh.plus(originReT, originImX);
		hh.reciprocal();
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
