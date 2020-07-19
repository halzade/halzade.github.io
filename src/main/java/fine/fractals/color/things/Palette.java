package fine.fractals.color.things;

import fine.fractals.Time;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Palette {

	private static Time time = new Time(Palette.class);

	public static final int rgb255 = 255;

	protected Color white = new Color(255, 255, 255);
	protected Color black = new Color(0, 0, 0);

	protected ArrayList<Color> spectrumR;
	protected ArrayList<Color> spectrumG;
	protected ArrayList<Color> spectrumB;

	private Integer currentMaxValue = null;
	private Integer currentMinValue = null;

	private static String name;

	private int currentMaxValue3[] = null;
	private int currentMinValue3[] = null;

	protected Palette(String name) {
		if (Palette.name != null) {
			throw new RuntimeException("Only one palette");
		}
		Palette.name = name;
	}

	public void reset() {
		System.err.println("RESET");
		/* It will be set by maxValueScr on update() */
		currentMaxValue3 = null;
		currentMinValue3 = null;
	}

	protected void spectrumColorsToLinear(ArrayList<Color> spec, LinkedList<Color> colors) {
		double step = 1;
		Color colorA = colors.get(0);
		Color colorB = colors.get(1);
		this.fromToLinear(spec, colorA, colorB, step);
		for (int i = 1; i < colors.size(); i++) {
			colorA = colorB;
			colorB = colors.get(i);
			this.fromToLinear(spec, colorA, colorB, step);
		}
	}

	protected void fromTo(ArrayList<Color> spec, Color from, Color to, String function) {
		fromTo(spec, from, to, function, false);
	}

	protected void fromTo(ArrayList<Color> spec, Color from, Color to, String function, boolean debug) {
		final int rFrom = from.getRed();
		final int gFrom = from.getGreen();
		final int bFrom = from.getBlue();

		int rDif = to.getRed() - rFrom;
		int gDif = to.getGreen() - gFrom;
		int bDif = to.getBlue() - bFrom;

		d(rFrom + "**" + to.getRed(), debug);
		d(gFrom + "**" + to.getGreen(), debug);
		d(bFrom + "**" + to.getBlue(), debug);

		final double maxDif = a(max(rDif, gDif, bDif));

		d("maxDif " + maxDif, debug);
		d("rDif " + rDif, debug);
		d("gDif " + gDif, debug);
		d("bDif " + bDif, debug);

		double rStep = ((double) rDif / maxDif);
		double gStep = ((double) gDif / maxDif);
		double bStep = ((double) bDif / maxDif);

		d("rStep " + rStep, debug);
		d("gStep " + gStep, debug);
		d("bStep " + bStep, debug);

		double d = 0;
		double v = 0;
		double value = 0;
		int i = 0;

		//if (rDif < 0) {
		//	rStep *= (-1);
		//}
		//if (gDif < 0) {
		//	gStep *= (-1);
		//}
		//if (bDif < 0) {
		//	bStep *= (-1);
		//}

		d(rDif + "==" + rStep, debug);
		d(gDif + "==" + gStep, debug);
		d(bDif + "==" + bStep, debug);

		int rr = 0;
		int gg = 0;
		int bb = 0;

		boolean stop = false;

		int tr = to.getRed();
		int tg = to.getGreen();
		int tb = to.getBlue();

		try {
			for (i = 0; i < a(maxDif); i++) {
				d = i / maxDif;
				v = Function.f(d, function);
				value = v * maxDif;
				// dots((int) value);

				rr = (int) (rFrom + (value * rStep));
				gg = (int) (gFrom + (value * gStep));
				bb = (int) (bFrom + (value * bStep));

				if (rr > rgb255) {
					rr = rgb255;
					stop = true;
				}
				if (gg > rgb255) {
					gg = rgb255;
					stop = true;
				}
				if (bb > rgb255) {
					bb = rgb255;
					stop = true;
				}

				if (rr < 0) {
					rr = 0;
					stop = true;
				}
				if (gg < 0) {
					gg = 0;
					stop = true;
				}
				if (bb < 0) {
					bb = 0;
					stop = true;
				}

				boolean rStop = rDif > 0 ? tr < rr : tr > rr;
				boolean gStop = gDif > 0 ? tg < gg : tg > gg;
				boolean bStop = bDif > 0 ? tb < bb : tb > bb;

				if (rStop) {
					rr = tr;
					stop = true;
				}
				if (gStop) {
					gg = tg;
					stop = true;
				}
				if (bStop) {
					bb = tb;
					stop = true;
				}

				spec.add(new Color(rr, gg, bb));

				if (stop) {
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("----- error -----");
			System.err.println("i: " + i);
			System.err.println("d: " + d);
			System.err.println("v: " + v);
			System.err.println("val: " + value);
			System.err.println("rr: " + rr);
			System.err.println("gg: " + gg);
			System.err.println("bb: " + bb);
		}
	}

	public static Integer max(int r, int g, int b) {
		if (a(r) >= a(g) && a(r) >= a(b)) {
			/* red has the biggest difference */
			return r;
		} else if (a(g) >= a(r) && a(g) >= a(b)) {
			/* green has the biggest difference */
			return g;
		} else if (a(b) >= a(r) && a(b) >= a(g)) {
			/* red has the biggest difference */
			return b;
		}
		throw new RuntimeException("Palette.fromToLinear() Unknown Max; " + r + " | " + g + " | " + b);
	}

	public static Integer min(int r, int g, int b) {
		if (a(r) <= a(g) && a(r) <= a(b)) {
			/* red has the biggest difference */
			return r;
		} else if (a(g) <= a(r) && a(g) <= a(b)) {
			/* green has the biggest difference */
			return g;
		} else if (a(b) <= a(r) && a(b) <= a(g)) {
			/* red has the biggest difference */
			return b;
		}
		throw new RuntimeException("Palette.fromToLinear() Unknown min; " + r + " | " + g + " | " + b);
	}

	protected void fromToLinear(ArrayList<Color> spec, Color from, Color to, double step) {
		final int rf = from.getRed();
		final int gf = from.getGreen();
		final int bf = from.getBlue();
		final int r = to.getRed() - rf;
		final int g = to.getGreen() - gf;
		final int b = to.getBlue() - bf;

		Integer m = max(r, g, b);

		final double rr = ((double) r / (double) m);
		final double gg = ((double) g / (double) m);
		final double bb = ((double) b / (double) m);

		if (m < 0) {
			step *= (-1);
		}
		for (double i = 0; a(i) < a(m); i = i + step) {
			spec.add(new Color(
					(int) (rf + (i * rr)),
					(int) (gf + (i * gg)),
					(int) (bf + (i * bb))
			));
		}
	}

	public Color getFirstR() {
		return spectrumR.get(0);
	}

	public Color getFirstG() {
		return spectrumG.get(0);
	}

	public Color getFirstB() {
		return spectrumB.get(0);
	}


	public Color getSpectrumValueR(int index) {
		return spectrumR.get(index);
	}

	public Color getSpectrumValueG(int index) {
		return spectrumG.get(index);
	}

	public Color getSpectrumValueB(int index) {
		return spectrumB.get(index);
	}


	public void update3(int[] maxValueScr, int minValueScr[]) {

		System.out.println("update...");
		if (currentMaxValue3 == null) {
			currentMaxValue3 = new int[3];
			currentMaxValue3[0] = maxValueScr[0];
			currentMaxValue3[1] = maxValueScr[1];
			currentMaxValue3[2] = maxValueScr[2];
			currentMinValue3 = new int[3];
			System.out.println("updated.a.. " + currentMinValue3);
			currentMinValue3[0] = minValueScr[0];
			currentMinValue3[1] = minValueScr[1];
			currentMinValue3[2] = minValueScr[2];
			System.out.println("updated.b.. " + currentMinValue3);
		}

		System.out.println("updated.c.. " + currentMinValue);
	}

	private static double a(double v) {
		return Math.abs(v);
	}

	public int colorResolutionR() {
		return spectrumR.size();
	}

	public int colorResolutionG() {
		return spectrumG.size();
	}

	public int colorResolutionB() {
		return spectrumB.size();
	}

	public static String getName() {
		return Palette.name;
	}


	private static void dots(int v) {
		for (int i = 0; i <= v; i++) {
			System.out.print(".");
		}
	}

	private void d(String str, boolean debug) {
		if (debug) {
			System.out.println(str);
		}
	}
}
