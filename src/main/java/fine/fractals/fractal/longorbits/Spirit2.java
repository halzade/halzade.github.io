package fine.fractals.fractal.longorbits;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public class Spirit2 extends Fractal {

	public Spirit2() {
		super("Spirit");
		ITERATION_MAX = 18000;
		ITERATION_MIN = 420;

		INIT_AREA_DOMAIN_SIZE = 5.0;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 5.0;
		INIT_IMAGE_TARGET_reT = 0.0;
		INIT_IMAGE_TARGET_imX = 0.0;

		ONLY_LONG_ORBITS = true;
		OPTIMIZE_SYMMETRY = true;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.square();
		hh.conjugation();
		hh.sin();
		hh.plus(originReT, originImX);
		hh.reciprocal();
		hh.plus(originReT, originImX);
	}

	@Override
	public boolean optimize(double reT, double imX) {
		if (reT > 0.22 || imX >  1.23) {
			return false;
		}

		// if (reT > 0.63 && imX < 0.99) {
		// 	return false;
		// }
		// if (isInsideCircle(reT, imX)) {
		// 	return false;
		// }
		// if (reT > -0.42 && imX > 1.1) {
		// 	return false;
		// }

		return true;
	}

	@Override
	public void colorsFor(HH hh, int counter, int size) {
		hh.calculation.r = true;
		hh.calculation.g = true;
		hh.calculation.b = true;
	}


	public static boolean isInsideCircle(double reT, double imX) {
		double t = 0.64;
		double x = 1.19;
		/* circle with center at -1 and radius 1/4 */
		return ((reT - t) * (reT - t)) + ((imX - x) * (imX - x)) < 0.025;
	}

}
