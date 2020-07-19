package fine.fractals.fractal.primes;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.Mathematician;
import fine.fractals.math.common.HH;

public class Magician extends Fractal {

	private Mathematician mathematician = new Mathematician();

	public Magician() {
		super("Magician");
		ITERATION_MAX = 14800;
		ITERATION_MIN = 420;

		INIT_AREA_DOMAIN_SIZE = 2.5;
		INIT_DOMAIN_TARGET_reT = -0.5;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 2.5;
		INIT_IMAGE_TARGET_reT = -0.5;
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

	public void colorsFor(HH hh, int counter, int size) {
		hh.calculation.r = false;
		hh.calculation.g = false;
		hh.calculation.b = false;
		boolean change = false;

		if (!change && mathematician.isPrime(size)) {
			hh.calculation.g = true;
			change = true;
		}
		if (!change && mathematician.isPrime(counter)) {
			hh.calculation.r = true;
			change = true;
		}

		if (!change) {
			hh.calculation.b = true;
		}
	}

}