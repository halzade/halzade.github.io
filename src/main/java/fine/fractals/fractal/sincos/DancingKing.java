package fine.fractals.fractal.sincos;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public class DancingKing extends Fractal {

	public DancingKing() {
		super("DancingKing");
		ITERATION_MAX = 8000;
		ITERATION_MIN = 42;

		INIT_AREA_DOMAIN_SIZE = 5.0;
		INIT_DOMAIN_TARGET_reT = 0.0;
		INIT_DOMAIN_TARGET_imX = 0.0;

		INIT_AREA_IMAGE_SIZE = 5.0;
		INIT_IMAGE_TARGET_reT = 0.0;
		INIT_IMAGE_TARGET_imX = 0.0;

		OPTIMIZE_SYMMETRY = true;
	}

	@Override
	public void math(HH hh, double originReT, double originImX) {
		hh.sin();
		hh.square();
		hh.plus(originReT, originImX);
		hh.multiplyBy(hh.cosCreateReT(), hh.cosCreateImX());
		hh.plus(originReT, originImX);
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
