package fine.fractals.fractal;

import fine.fractals.Main;
import fine.fractals.math.Mathematician;
import fine.fractals.math.common.HH;

public class TestFractal extends Fractal {

	private Mathematician mathematician = new Mathematician();

	public TestFractal() {
		super("TestFractal");

		if (Main.DO_FILES) {
			throw new RuntimeException("Don't use fractal files for TestFractal");
		}

		ITERATION_MAX = 8000;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 4;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 4.0;
		INIT_IMAGE_TARGET_reT = 0.0;
		INIT_IMAGE_TARGET_imX = 0.0;

		// ONLY_LONG_ORBITS = true;

		Mathematician.initialize();
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {

		hh.square();
		hh.plus(originReT, originImX);
		hh.glassChange(mathematician);

	}

	@Override
	public boolean optimize(double reT, double imX) {
		return true;
	}

	// @Override
	// public void colorsFor(HH hh, int counter, int size) {
	// 	hh.calculation.r = true;
	// 	hh.calculation.g = true;
	// 	hh.calculation.b = true;
	// }

	@Override
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
			hh.calculation.g = true;
			change = true;
		}

		if (!change) {
			hh.calculation.b = true;
		}
	}


}
