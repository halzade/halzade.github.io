package fine.fractals.fractal.longorbits;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public class FatStarZoom extends Fractal {


	private static final int r = 3000;
	private static final int m = 61;

	public FatStarZoom() {
		super("FatStar");
		ITERATION_MAX = 81_000;
		ITERATION_MIN = 8;

		INIT_AREA_DOMAIN_SIZE = 3.5;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 0.15;
		INIT_IMAGE_TARGET_reT = 0.5425;
		INIT_IMAGE_TARGET_imX = -0.31;

		ONLY_LONG_ORBITS = true;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.square();
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
