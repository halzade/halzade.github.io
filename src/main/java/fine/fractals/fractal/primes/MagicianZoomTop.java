package fine.fractals.fractal.primes;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.Mathematician;
import fine.fractals.math.common.HH;

public class MagicianZoomTop extends Fractal {

	private Mathematician mathematician = new Mathematician();

	public MagicianZoomTop() {
		super("MagicianZoomTop");
		ITERATION_MAX = 14800;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 2.5;
		INIT_DOMAIN_TARGET_reT = -0.5;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 0.5;
		INIT_IMAGE_TARGET_reT = -1.28;
		INIT_IMAGE_TARGET_imX = 0.0;

		OPTIMIZE_SYMMETRY = true;

		Mathematician.initialize();
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.magicianChange(mathematician);
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