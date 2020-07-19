package fine.fractals.fractal;

import fine.fractals.math.Mathematician;
import fine.fractals.math.common.HH;

public class FineMandelbrotZoom extends Fractal {

	/*
	 * Mandelbrot Video #1 - Top
	 * TARGET_RE = -0.10675625916322415;
	 * TARGET_IM = 0.8914368889277283;
	 */

	private Mathematician mathematician = new Mathematician();

	public FineMandelbrotZoom() {
		super("FineMandelbrotZoom");
		ITERATION_MAX = 14800;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 6.0;
		INIT_DOMAIN_TARGET_reT = -1.40115518909199;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = INIT_AREA_DOMAIN_SIZE;
		INIT_IMAGE_TARGET_reT = INIT_DOMAIN_TARGET_reT;
		INIT_IMAGE_TARGET_imX = INIT_DOMAIN_TARGET_imX;

		Mathematician.initialize();
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.square();
		hh.plus(originReT, originImX);
	}

	@Override
	public boolean optimize(double reT, double imX) {
		return true;
	}

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
			hh.calculation.r = true;
			change = true;
		}

		if (counter % 3 == 0) {
			hh.calculation.g = true;
			change = true;
		}
		if (size % counter == 0) {
			hh.calculation.g = true;
			change = true;
		}

		if (!change) {
			hh.calculation.b = true;
		}
	}

}