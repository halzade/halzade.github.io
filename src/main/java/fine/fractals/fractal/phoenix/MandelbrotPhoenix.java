package fine.fractals.fractal.phoenix;

import fine.fractals.math.common.HH;

public class MandelbrotPhoenix extends Phoenix {

	/*
	 * Mandelbrot Video #1 - Top
	 * TARGET_RE = -0.10675625916322415;
	 * TARGET_IM = 0.8914368889277283;
	 */

	public MandelbrotPhoenix() {
		super("MandelbrotPhoenix");
		ITERATION_MAX = 14800;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 2.5;
		INIT_DOMAIN_TARGET_reT = -0.5;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = INIT_AREA_DOMAIN_SIZE;
		INIT_IMAGE_TARGET_reT = INIT_DOMAIN_TARGET_reT;
		INIT_IMAGE_TARGET_imX = INIT_DOMAIN_TARGET_imX;

		// OPTIMIZE_SYMMETRY = true;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		phoenix(hh, originReT, originImX);
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

	public void init(double c, double p) {
		super.init(c, p);
	}

}