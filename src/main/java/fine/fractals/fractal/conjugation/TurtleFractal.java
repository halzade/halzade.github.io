package fine.fractals.fractal.conjugation;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public class TurtleFractal extends Fractal {

	public TurtleFractal() {
		super("Turtle");
		ITERATION_MAX = 4000;
		ITERATION_MIN = 8;

		INIT_AREA_DOMAIN_SIZE = 3.0;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 3.0;
		INIT_IMAGE_TARGET_reT = 0.0;
		INIT_IMAGE_TARGET_imX = 0.0;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.square();
		hh.square();
		hh.plus(originReT, originImX);
		hh.conjugation();
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
