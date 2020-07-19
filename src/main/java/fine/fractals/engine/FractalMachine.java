package fine.fractals.engine;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.Main;
import fine.fractals.color.things.Palette;
import fine.fractals.color.things.ScreenColor;
import fine.fractals.data.objects.Bool;
import fine.fractals.fractal.Fractal;
import fine.fractals.fractal.phoenix.Phoenix;
import fine.fractals.math.common.Element;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public abstract class FractalMachine {

	private static Time time = new Time(FractalMachine.class);

	private static int counter = 0;


	// To make images of individual paths
	public static void saveImage(int[][] array) {

		final BufferedImage image = new BufferedImage(Main.RESOLUTION_IMAGE_T, Main.RESOLUTION_IMAGE_X, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < Main.RESOLUTION_IMAGE_X; x++) {
			for (int t = 0; t < Main.RESOLUTION_IMAGE_T; t++) {
				/* Design pattern */
				if (array[t][x] == 1) {
					image.setRGB(t, x, Color.WHITE.getRGB());
				}
			}
		}
		saveImage(image, "_PATH_" + (counter++) + "_");
	}

	public static void saveImage(BufferedImage image, String add) {
		try {
			JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
			jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			jpegParams.setCompressionQuality(0.98f);

			String fileName = Application.FILE_PATH
					+ Fractal.NAME + Application.APP_NAME
					+ iteration()
					+ "_" + Fractal.ITERATION_MAX
					+ "_" + Fractal.ITERATION_MIN
					+ "_" + add
					+ "_" + Main.RESOLUTION_MULTIPLIER
					+ "_" + Palette.getName()
					+ ".jpg";

			File outputFile = new File(fileName);
			time.now("Image: " + fileName);
			final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();

			if (Application.addText) {
				FractalMachine.addText(image);
			}

			try (FileImageOutputStream fis = new FileImageOutputStream(outputFile)) {
				writer.setOutput(fis);
				writer.write(null, new IIOImage(image, null, null), jpegParams);
				fis.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		// FractalMachine.saveFileTexts();
	}

	private static String iteration() {
		if (Application.REPEAT) {
			return "_" + String.format("%06d", Application.iteration);
		}
		return "";
	}

	public static void addText(BufferedImage image) {
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.white);
		graphics.setFont(new Font("Arial", Font.PLAIN, 12));

		Phoenix phoenix = (Phoenix) Main.FRACTAL;

		graphics.drawString("c: " + roundString3(phoenix.getC()) + "  p: " + roundString3(phoenix.getP()), 20, 20);
	}

	synchronized public static String roundString3(double d) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.setScale(3, RoundingMode.HALF_UP);
		return String.format("%1$,.3f", bd.doubleValue());
	}


	public static boolean isVeryDeepBlack(int tt, int xx, Element[][] elements) {
		if (elements[tt][xx].getValue() > 0) {
			return false;
		}
		Integer value;
		/* r is a radius of a circle to verify non zero values around*/
		final int r = 5;
		for (int t = -r; t < r; t++) {
			for (int x = -r; x < r; x++) {
				if ((t * t) + (x * x) < (r * r)) {
					/* Find value to be carried by HH.calculation */
					int t2 = tt + t;
					int x2 = xx + x;
					value = valueAt(t2, x2, elements);
					if (value == null || value > 0) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean someNeighboursFinishedInside(int tt, int xx, Element[][] elements) {
		/* r is a radius of a circle to verify finished inside (red) elements*/
		Element el;
		final int r = Main.neighbours;
		for (int t = -r; t < r; t++) {
			for (int x = -r; x < r; x++) {
				if ((t * t) + (x * x) <= (r * r)) {
					int t2 = tt + t;
					int x2 = xx + x;
					if (checkDomain(t2, x2)) {
						el = elements[t2][x2];
						if (el != null && el.isHibernatedFinishedInside()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static boolean checkDomain(int t, int x) {
		return t >= 0 && t < Application.RESOLUTION_DOMAIN_T
				&& x >= 0 && x < Application.RESOLUTION_DOMAIN_X;
	}

	public static boolean checkImage(int t, int x) {
		return t >= 0 && t < Main.RESOLUTION_IMAGE_T
				&& x >= 0 && x < Main.RESOLUTION_IMAGE_X;
	}

	private static Integer valueAt(int t, int x, Element[][] mandelbrot) {
		if (checkDomain(t, x)) {
			Element element = mandelbrot[t][x];
			return element.getValue();
		}
		return null;
	}

	public static void setAsDeepBlack(int t, int x, Element[][] elements) {
		if (checkDomain(t, x)) {
			/* Most probably Element of Mandelbrot set, don're calculate */
			elements[t][x].setHibernatedBlackNeighbour();
		}
	}

	public static void testOptimizationBreakElement(int t, int x, Element element, ArrayList<Integer> failedNumbersT, ArrayList<Integer> failedNumbersX, Bool lastIsWhite, Bool lastIsBlack) {
		if (element.isHibernatedBlack_Neighbour() || element.isHibernatedBlack()) {
			if (lastIsWhite.is()) {
				failedNumbersT.add(t);
				failedNumbersX.add(x);
			}
			lastIsBlack.setTrue();
			lastIsWhite.setFalse();

		} else if (element.isHibernatedFinished()) {

			if (lastIsBlack.is()) {
				failedNumbersT.add(t);
				failedNumbersX.add(x);
			}
			lastIsBlack.setFalse();
			lastIsWhite.setTrue();
		} else {
			lastIsBlack.setFalse();
			lastIsWhite.setFalse();
		}
	}

	// not correct;
	public static void setActiveMovedIfBlack(int t, int x, Element[][] mandelbrot) {
		Element element;
		if (checkDomain(t, x)) {
			element = mandelbrot[t][x];
			if (element.isHibernatedBlack() || element.isHibernatedBlack_Neighbour()) {
				element.setActiveFixed();
			}
		}
	}

	public static void setActiveToAddToCalculation(int t, int x, Element[][] mandelbrot) {
		Element element;
		if (checkDomain(t, x)) {
			element = mandelbrot[t][x];
			if (element != null && (element.isActiveAny() || element.isHibernatedFinished())) {
				if (!ScreenColor._MARK.equals(element.getColor()) && !ScreenColor._MARK_FINISHED.equals(element.getColor())) {
					element.setActiveRecalculate();
				}
			}
		}
	}

	private static String lastProgress;

	/* Returns int last progress */
	/* Changes made here will be painted by calculationProgressPoint() */
	public static void calculateProgressPercentage(long countPart, long countPartAll, long partRowT, long rowsAll, boolean first) {
		FractalEngine.calculationProgress = String.valueOf(countPart * 100 / countPartAll);
		if (!FractalEngine.calculationProgress.equals(lastProgress)) {
			lastProgress = FractalEngine.calculationProgress;
			if (!Application.REPEAT) {

				String partText = String.valueOf(partRowT);
				if (first) {
					partText = "0*";
				}

				time.now("progress: " + FractalEngine.calculationProgress + " (" + partText + " of " + rowsAll + ")");
				Application.rep();
				System.gc();
			}
		}
	}

	/* Coordinates of elements closest around xx, yy */
	public static void initRingSequence(final int[] RING_SEQUENCE_XX, final int[] RING_SEQUENCE_YY) {
		/* 3 x 3 */
		RING_SEQUENCE_XX[0] = 0;
		RING_SEQUENCE_YY[0] = 1;
		RING_SEQUENCE_XX[1] = 1;
		RING_SEQUENCE_YY[1] = 0;
		RING_SEQUENCE_XX[2] = 0;
		RING_SEQUENCE_YY[2] = -1;
		RING_SEQUENCE_XX[3] = -1;
		RING_SEQUENCE_YY[3] = 0;
		RING_SEQUENCE_XX[4] = -1;
		RING_SEQUENCE_YY[4] = 1;
		RING_SEQUENCE_XX[5] = 1;
		RING_SEQUENCE_YY[5] = 1;
		RING_SEQUENCE_XX[6] = 1;
		RING_SEQUENCE_YY[6] = -1;
		RING_SEQUENCE_XX[7] = -1;
		RING_SEQUENCE_YY[7] = -1;
		/* 5 x 5 sides */
		RING_SEQUENCE_XX[8] = 0;
		RING_SEQUENCE_YY[8] = 2;
		RING_SEQUENCE_XX[9] = 2;
		RING_SEQUENCE_YY[9] = 0;
		RING_SEQUENCE_XX[10] = 0;
		RING_SEQUENCE_YY[10] = -2;
		RING_SEQUENCE_XX[11] = -2;
		RING_SEQUENCE_YY[11] = 0;
		RING_SEQUENCE_XX[12] = -1;
		RING_SEQUENCE_YY[12] = 2;
		RING_SEQUENCE_XX[13] = 1;
		RING_SEQUENCE_YY[13] = 2;
		RING_SEQUENCE_XX[14] = 2;
		RING_SEQUENCE_YY[14] = 1;
		RING_SEQUENCE_XX[15] = 2;
		RING_SEQUENCE_YY[15] = -1;
		RING_SEQUENCE_XX[16] = -1;
		RING_SEQUENCE_YY[16] = -2;
		RING_SEQUENCE_XX[17] = 1;
		RING_SEQUENCE_YY[17] = -2;
		RING_SEQUENCE_XX[18] = -2;
		RING_SEQUENCE_YY[18] = -1;
		RING_SEQUENCE_XX[19] = -2;
		RING_SEQUENCE_YY[19] = 1;
		RING_SEQUENCE_XX[20] = -2;
		RING_SEQUENCE_YY[20] = 2;
		RING_SEQUENCE_XX[21] = 2;
		RING_SEQUENCE_YY[21] = 2;
		RING_SEQUENCE_XX[22] = 2;
		RING_SEQUENCE_YY[22] = -2;
		RING_SEQUENCE_XX[23] = -2;
		RING_SEQUENCE_YY[23] = -2;
	}

}
