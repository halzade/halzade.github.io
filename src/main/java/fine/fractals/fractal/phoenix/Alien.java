package fine.fractals.fractal.phoenix;

import fine.fractals.math.common.HH;

public class Alien extends Phoenix {

	public Alien() {
		super("Alien");
		super.init(0.35, -0.25);

		ITERATION_MAX = 2500;
		ITERATION_MIN = 8;

		INIT_AREA_DOMAIN_SIZE = 2.5;
		INIT_DOMAIN_TARGET_reT = -0.25;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 2.5;
		INIT_IMAGE_TARGET_reT = -0.25;
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
