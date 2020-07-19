package fine.fractals.fractal.phoenix;

import fine.fractals.math.common.HH;

public class Napoleon extends Phoenix {

	public Napoleon() {
		super("Napoleon");
		super.init(-0.25, 0.65);

		ITERATION_MAX = 8000;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 2.5;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 2.5;
		INIT_IMAGE_TARGET_reT = 0.0;
		INIT_IMAGE_TARGET_imX = 0.0;

		OPTIMIZE_SYMMETRY = true;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		phoenix(hh, originReT, originImX);
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
