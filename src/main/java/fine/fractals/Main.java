package fine.fractals;

import fine.fractals.color.Palette3_RGB;
import fine.fractals.color.things.ColoringMethod;
import fine.fractals.color.things.Palette;
import fine.fractals.fractal.FineMandelbrot;
import fine.fractals.fractal.Fractal;

public class Main {

	/**
	 * The Fine Fractal to be calculated
	 */
	public static Fractal FRACTAL = new FineMandelbrot();


	/**
	 * Image resolution
	 */
	private static final int r = 800;
	// private static final int r = 10000;

	/**
	 * Calculation points per pixel
	 * m x m
	 * Keep it odd, so that the center point is in the center of a pixel
	 */
	private static final int m = 3;
	// private static final int m = 101;

	/**
	 * Image in resolution Application.RESOLUTION_IMAGE_SAVE_FOR = 2000 will be saved to the location below
	 *
	 * Create the folder in your home directory or change the path
	 */
	public static final String FILE_PATH = Application.USER_HOME + "/Fractals/";


	public static Palette colorPalette = new Palette3_RGB();

	public static final boolean DO_FILES = false;

	public static final int COLORING_METHOD = ColoringMethod.DATA_INTEGRAL;

	public static final int neighbours = 4;
	public static final int RESOLUTION_MULTIPLIER = m;
	public static final int RESOLUTION_IMAGE_T = r;
	public static final int RESOLUTION_IMAGE_X = r;

	public static int COREs = Runtime.getRuntime().availableProcessors() - 1;

	private static Time time = new Time(Main.class);

	private Main() {
	}

	public static void main(String[] args) {

		if (COREs < 1) {
			COREs = 1;
		}

		time.red("cores: " + COREs);

		if (RESOLUTION_IMAGE_T < 1000) {
			Application.RESOLUTION_DOMAIN_T = RESOLUTION_IMAGE_T;
		}
		if (RESOLUTION_IMAGE_X < 1000) {
			Application.RESOLUTION_DOMAIN_X = RESOLUTION_IMAGE_X;
		}

		time.red(Application.RESOLUTION_DOMAIN_T + " <-> " + RESOLUTION_IMAGE_T);
		time.red(Application.RESOLUTION_DOMAIN_X + " <-> " + RESOLUTION_IMAGE_X);


		new Application().execute();

		time.now("Application END");
	}

}
