package fine.fractals.color.things;

public class Function {

	public static final String linear1 = "linear1";
	public static final String linear7 = "linear7";
	public static final String quadratic = "quadratic";
	public static final String q3 = "q3";
	public static final String q4 = "q4";
	public static final String q5 = "q5";
	public static final String exp = "exp";
	public static final String exp2 = "exp2";
	public static final String circleDown = "circleDown";
	public static final String circleUp = "circleUp";

	/**
	 * 0 <= d <= 1
	 */
	public static double f(double d, String function) {
		switch (function) {
			case linear1:
				return d;
			case linear7:
				return d * 7;
			case quadratic:
				return d * d;
			case q3:
				return d * d * d;
			case q4:
				return d * d * d * d;
			case q5:
				return d * d * d * d * d;
			case exp:
				return Math.exp(d) - 1;
			case exp2:
				return Math.exp(d * d) - 1;
			case circleDown:
				return Math.sqrt(1 - (d * d));
			case circleUp:
				return 1 - Math.sqrt(1 - (d * d));
			default:
				throw new RuntimeException("Undefined function: " + function);
		}
	}
}
