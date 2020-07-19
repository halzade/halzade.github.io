package fine.fractals.engine;

import fine.fractals.Application;
import fine.fractals.Main;
import fine.fractals.Time;
import fine.fractals.data.FractalFiles;
import fine.fractals.data.Key;
import fine.fractals.data.objects.Data;
import fine.fractals.fractal.Fractal;
import fine.fractals.math.Design;
import fine.fractals.math.common.Element;
import fine.fractals.math.mandelbrot.Mandelbrot;
import fine.fractals.ui.Formatter;
import fine.fractals.ui.OneTarget;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FractalEngine {

	private static Time time = new Time(FractalEngine.class);
	private static boolean fixInProgress = false;

	private final BufferedImage designImage;

	private Design design;
	private Mandelbrot mandelbrot;

	public static boolean calculationInProgress;
	public static String calculationProgress;
	public static String calculationText;
	public static int[] calculationProgressPoint = null;

	private boolean updateDomain = false;

	public static FractalEngine ME;

	public FractalEngine(BufferedImage designImage) {
		this.designImage = designImage;
		this.design = new Design();
		this.design.setAreaImage(Application.ME.areaImage);

		this.mandelbrot = new Mandelbrot(this.design, Application.ME.areaDomain, Application.ME.areaImage);
		FractalEngine.ME = this;
	}

	public void reset() {
		this.mandelbrot.resetAsNew();
		this.design.resetAsNew();

		updateDomain = false;

		calculationProgress = null;
		calculationText = null;
		calculationProgressPoint = null;
		fixInProgress = false;
	}

	synchronized public void calculateFromThread(int counter) {

		time.now("calculateFromThread");

		Time.reset();
		Data.set(Key.A_ITERATION_MAX, Fractal.ITERATION_MAX);
		Data.set(Key.A_ITERATION_MIN, Fractal.ITERATION_MIN);

		design.clearScreenValues();

		calculationInProgress = true;
		try {

			if (Main.DO_FILES && FractalFiles.existsDesign()) {
				// LOAD Design

				time.now("---------------------");
				time.now("LOAD DESIGN FROM FILE");
				time.now("---------------------");

				FractalFiles.initDesign();
				design.calculationFinished();
			} else {
				// Calculate Design
				if (updateDomain) {
					mandelbrot.DOMAIN.domainForThisZoom();
					time.now("new domain done");
					updateDomain = false;
				}
				time.now("CALCULATE");
				mandelbrot.calculate();

				/* sort escape path to spectrum for ZOOM */
				// design.addAllEscapePathsToSpectra();

				// time.red("--- processing mandelbrot values");
				// mandelbrot.processValues();
				// time.red("--- processing mandelbrot values DONE");

				if (Application.REPEAT) {
					/* Test if Optimization didn't break anything */
					mandelbrot.fixOptimizationBreak();
				}
			}
			time.now("ScreenValuesToImages 1");
			screenValuesToImages();

			/* save file based on screen height; don't save it for testing */
			if (Main.RESOLUTION_IMAGE_T >= Application.RESOLUTION_IMAGE_SAVE_FOR) {
				time.now("Save images");
				FractalMachine.saveImage(designImage, "" + counter);
				time.now("Save images DONE");
			}
		} catch (Exception e) {
			time.e("calculateFromThread()", e);
			time.red("---------------------------------------");
			time.red("Images not saved, use 's' to save them.");
			time.red("---------------------------------------");

			Application.REPEAT = false;
			e.printStackTrace();
		}
		time.now("DONE");
		calculationInProgress = false;
		calculationProgressPoint = null;
		Application.ME.repaint();
	}

	synchronized public void calculateFractalColoring() {
		time.now("calculateFractalColoring");
		calculationInProgress = true;
		// design.addAllEscapePathsToSpectra(); for ZOOM

		time.now("ScreenValuesToImages 2");
		screenValuesToImages();

		/* save file based on screen height; don't save it for testing */
		if (Main.RESOLUTION_IMAGE_T >= Application.RESOLUTION_IMAGE_SAVE_FOR) {
			time.now("Save images");
			FractalMachine.saveImage(designImage, "" + Formatter.now());
			time.now("Save images done");
		}

		time.now("DONE");
		calculationInProgress = false;
		calculationProgressPoint = null;
		Application.ME.repaint();
	}

	private void screenValuesToImages() {

		design.createValueIntegral();

		Color color;
		time.now("ScreenValuesToImages Design");
		for (int x = 0; x < Main.RESOLUTION_IMAGE_X; x++) {
			for (int t = 0; t < Main.RESOLUTION_IMAGE_T; t++) {
				/* Design pattern */
				color = design.colorAt(t, x);
				designImage.setRGB(t, x, color.getRGB());
			}
		}
	}

	public void updateDomain() {
		this.updateDomain = true;
	}

	public void resetDomainOptimizationHardCareful() {
		this.mandelbrot.resetOptimizationHard();
	}

	public void resetDomainOptimizationSoftCareful() {
		this.mandelbrot.resetOptimizationSoft();
	}

	public void createMandelbrotMask(BufferedImage mandelbrotMask) {
		this.mandelbrot.DOMAIN.createMask(mandelbrotMask);
	}

	public void fixOnClick(OneTarget target) {
		this.mandelbrot.fixOptimizationOnClick(target.getScreenFromCornerT(), target.getScreenFromCornerX());
		Application.ME.repaint();
	}

	public void fixDomainOnClick(OneTarget target) {
		this.mandelbrot.fixDomainOptimizationOnClick(target.getScreenFromCornerT(), target.getScreenFromCornerX());
		Application.ME.repaint();
	}

	public Element getMandelbrotElementAt(int mousePositionT, int mousePositionX) {
		return this.mandelbrot.getElementAt(mousePositionT, mousePositionX);
	}

	public static void setFixInProgress(boolean fixInProgress) {
		FractalEngine.fixInProgress = fixInProgress;
	}

	public static boolean isFixInProgress() {
		return FractalEngine.fixInProgress;
	}

	public static void save() {
		FractalMachine.saveImage(ME.designImage, Formatter.now());
		Application.ME.repaint();
	}
}
