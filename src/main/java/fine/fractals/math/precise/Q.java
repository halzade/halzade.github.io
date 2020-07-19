//package fine.fractals.math.precise;
//
//import fine.fractals.Time;
//import org.apache.commons.math3.dfp.Dfp;
//import org.apache.commons.math3.dfp.DfpField;
//
//public abstract class Q {
//
//	private static Time time = new Time(Q.class);
//
//	/* "precision" is the number of significant digits */
//	/* This is in Radix 10 000 */
//	/* 0 ... 10^-11 | 20 ... 10^-15 | 30 ... 10^-27 */
//	private static int PRECISION = 25;
//	/* "scale" is the number of digits to the right of the decimal point */
//
//	private static DfpField field;
//
//	public static Dfp ZERO;
//	public static Dfp TWO;
//	public static Dfp FOUR;
//	public static Dfp _42;
//
//	static {
//		field = new DfpField(PRECISION);
//		field.setRoundingMode(DfpField.RoundingMode.ROUND_HALF_UP);
//
//		ZERO = dfp(0);
//		TWO = dfp(2);
//		FOUR = dfp(4);
//		_42 = dfp(42);
//	}
//
//	public static void init(int precision) {
//		time.now(" === " + precision + "=== ");
//		field = new DfpField(precision);
//	}
//
//	private Q() {
//	}
//
//	public static Dfp dfp(double d) {
//		return field.newDfp(d);
//	}
//
//	public static Dfp dfp(String s) {
//		return field.newDfp(s);
//	}
//
//	public static String round14(Dfp dfp) {
//		return dfp.toString();
//	}
//
//	public static Dfp sq(Dfp d) {
//		return d.multiply(d);
//	}
//
//}
