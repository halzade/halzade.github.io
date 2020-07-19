package fine.fractals.fractal.sincos;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public class FineMandelbrotA extends Fractal {

	public FineMandelbrotA() {
		super("FineMandelbrotA");

		ITERATION_MAX = 8000;
		ITERATION_MIN = 42;

		/** There are two of them */
		INIT_AREA_DOMAIN_SIZE = 1.0;
		INIT_DOMAIN_TARGET_reT = -1.1;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 1;
		INIT_IMAGE_TARGET_reT = -1.1;
		INIT_IMAGE_TARGET_imX = 0.0;

		OPTIMIZE_SYMMETRY = true;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.minusInvert();
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
