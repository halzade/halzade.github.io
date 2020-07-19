package fine.fractals.math;

import fine.fractals.Time;
import fine.fractals.data.Key;
import fine.fractals.data.objects.Data;
import fine.fractals.math.common.HH;

@SuppressWarnings("unused")
public class Transformation {

	private static Time time = new Time(Transformation.class);

	private static double a;
	private static double b;

	private static boolean initialized = false;

	private Transformation() {
	}

	public static void init(double t) {
		
		/* Unit circle parametrization */
		a = (1 - t * t) / (1 + t * t);
		b = (2 * t) / (1 + t * t);

		time.now("a: " + a);
		time.now("b: " + b);
		time.now("t: " + t + "; 1: " + ((a * a) + (b * b)));

		Data.set(Key.T_ROTATION, t);

		initialized = true;
	}

	/* Do the transformation */
	public static void rotate(HH hh, double re, double im) {
		if (initialized) {
			double am = 0.01;
			double bm = 0.01;
			/* a * re - b * im ...Rotation by multiplication */
			/* +- am ...move element to origin and back */
			hh.calculation.reT = a * (re - am) - b * (im - bm) + am;
			hh.calculation.imX = a * (im - bm) + b * (re - am) + bm;
		}
	}

}