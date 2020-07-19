package fine.fractals.color.things;

public class ColoringMethod {

	public static final int DATA_INTEGRAL = 0;

	/**
	 * Color individual paths by their length.
	 */
	public static final int PATH_LENGTH = 1;

	/**
	 * Color screen element based on how many times they were hit by any diverging path.
	 * To change a palette the fractals doesn't need to be recalculated.
	 */
	public static final int ORBITAL_DENSITY = 2;

	/**
	 * Color screen elements based how many times they were hit by any diverging path of a specific length.
	 * - short paths: 1st spectrum (e.g. red)
	 * - length between: 2nd spectrum (e.g. green)
	 * - long paths: 3rd spectrum (e.g. blue)
	 * These then get to be combined into RGB image by
	 * orbitalDensityShortPaths[t][x].getRed() etc.
	 * <p>
	 * For simplicity I want to always use three spectra. Not more or less.
	 */
	public static final int SPECTRA_BY_LENGTH = 3;

}
