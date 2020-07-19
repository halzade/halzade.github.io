package fine.fractals.fractal;

import fine.fractals.math.AreaDomain;
import fine.fractals.math.Mathematician;
import fine.fractals.math.common.HH;

public class FineMandelbrot extends Fractal {

	private Mathematician mathematician = new Mathematician();

	public FineMandelbrot() {
		super("FineMandelbrot");
		ITERATION_MAX = 14800;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 3.0;
		INIT_DOMAIN_TARGET_reT = -0.5;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = INIT_AREA_DOMAIN_SIZE;
		INIT_IMAGE_TARGET_reT = INIT_DOMAIN_TARGET_reT;
		INIT_IMAGE_TARGET_imX = INIT_DOMAIN_TARGET_imX;

		OPTIMIZE_SYMMETRY = true;

		Mathematician.initialize();
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.square();
		hh.plus(originReT, originImX);
	}

	@Override
	public boolean optimize(double reT, double imX) {
		double delta = AreaDomain.ME.plank() * 2;
		if (-0.75 - reT < delta * 2 && reT + 0.75 < delta * 2) {
			// gap between circle and cardioid
			return true;
		}

		boolean a = isOutsideCardioid(reT, imX) && isOutsideCircle(reT, imX);
		if (a) return true;
		a = isOutsideCardioid(reT + delta, imX) && isOutsideCircle(reT + delta, imX);
		if (a) return true;
		a = isOutsideCardioid(reT - delta, imX) && isOutsideCircle(reT - delta, imX);
		if (a) return true;
		a = isOutsideCardioid(reT, imX + delta) && isOutsideCircle(reT, imX + delta);
		if (a) return true;
		a = isOutsideCardioid(reT, imX - delta) && isOutsideCircle(reT, imX - delta);
		return a;
	}

	public static boolean isOutsideCardioid(double reT, double imX) {

		/*
		 * (t^2 + x^2 - 2at)^2 = 4a^2 (t^2 + x^2)
		 */
		final double a = 0.25;
		final double t = reT - 0.25;
		final double t2 = t * t;
		final double x2 = imX * imX;
		final double leftSide = t2 + x2 + 2 * a * t;

		return leftSide * leftSide > 4 * a * a * (t2 + x2);
	}

	public static boolean isOutsideCircle(double reT, double imX) {
		/*
		 * circle with center at -1 and radius 1/4
		 */
		return ((reT + 1) * (reT + 1)) + (imX * imX) > 0.0625;
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