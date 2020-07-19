package fine.fractals.fractal.phoenix;

import fine.fractals.math.common.HH;

public class GirlWithAButterfly extends Phoenix {

	public GirlWithAButterfly() {
		super("GirlWithAButterfly");
		super.init(-0.1, 0.75);

		ITERATION_MAX = 8000;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 2.5;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 1.5;
		INIT_IMAGE_TARGET_reT = -0.2;
		INIT_IMAGE_TARGET_imX = 0.0;
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
