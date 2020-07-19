package fine.fractals.fractal;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.math.AreaImage;
import fine.fractals.math.common.HH;
import fine.fractals.ui.Formatter;

public abstract class Fractal {

	private static Time time = new Time(Fractal.class);

	public static String NAME;

	public static int ITERATION_MAX;
	public static int ITERATION_MIN;
	public static double INIT_AREA_IMAGE_SIZE;
	public static double INIT_IMAGE_TARGET_reT;
	public static double INIT_IMAGE_TARGET_imX;

	public static double INIT_AREA_DOMAIN_SIZE;
	public static double INIT_DOMAIN_TARGET_reT;
	public static double INIT_DOMAIN_TARGET_imX;


	public static boolean ONLY_LONG_ORBITS = false;
	public static boolean OPTIMIZE_SYMMETRY = false;

	/* 4 worked well for all fractals so far */
	public static int CALCULATION_BOUNDARY = 4;

	public static Fractal ME;

	public Fractal(String name) {
		NAME = name;
		ME = this;
	}

	/*
	 * Calculate only next position.
	 * Color can be known only after full path finished.
 	 */
	public abstract void math(HH hh, double reT, double imX);

	public abstract boolean optimize(double reT, double imX);

	/**
	 * Coloring magic
	 */
	abstract public void colorsFor(HH hh, int counter, int size);

	private static double sizeNow;
	private static double sizePrev;
	private static int iterationMax0;
	private static int iterationMaxNow;

	public static void update(AreaImage areaImage, int i) {
		if (iterationMax0 == 0) {
			sizePrev = areaImage.sizeImX * (1 + Application.ZOOM);
			iterationMax0 = ITERATION_MAX;
		}
		iterationMaxNow = ITERATION_MAX;
		sizePrev = sizeNow;

		// final double size0 = INIT_AREA_IMAGE_SIZE * INIT_AREA_IMAGE_SIZE;
		// final double sizeNow = areaImage.sizeImX * areaImage.sizeReT;
		final double size0 = INIT_AREA_IMAGE_SIZE;
		final double sizeNow = areaImage.sizeImX;

		final double magn = (size0 / sizeNow); // length 8 4 2 1 1/2 1/4 1/8


		final double iterationDiv = (magn * iterationMax0) - iterationMax0;
		time.now("(" + magn + " | " + iterationDiv + ")");

		ITERATION_MAX = (int) (iterationMax0 + (iterationDiv / 5000.0));

		// 1320 : 38157515


		if (ITERATION_MAX > 300_000_000) {
			ITERATION_MAX = 300_000_000;
			time.red("ITERATION_MAX " + ITERATION_MAX);
		}

		time.now(i + " : " + Formatter.roundString(areaImage.sizeReT) + " : " + ITERATION_MAX);

		sizePrev = sizeNow;
	}

}
