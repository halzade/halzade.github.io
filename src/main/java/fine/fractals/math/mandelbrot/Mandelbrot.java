package fine.fractals.math.mandelbrot;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.Main;
import fine.fractals.data.FractalFiles;
import fine.fractals.data.objects.Bool;
import fine.fractals.engine.FractalEngine;
import fine.fractals.engine.FractalMachine;
import fine.fractals.fractal.Fractal;
import fine.fractals.math.AreaDomain;
import fine.fractals.math.AreaImage;
import fine.fractals.math.Design;
import fine.fractals.math.common.Element;
import fine.fractals.math.precise.PathThread;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mandelbrot {

	private Time time = new Time(Mandelbrot.class);

	private AreaDomain areaDomain;
	private AreaImage areaImage;

	public MandelbrotDomain DOMAIN;

	private Design design;

	private final int[] RING_SEQUENCE_TT = new int[24];
	private final int[] RING_SEQUENCE_XX = new int[24];

	protected int maxScrValueNow = 0;
	protected int maxScrValueTotal = 0;
	/* Also initialize to zero. New min values are update when smaller min value drifts out of the screen. */
	protected int minScrValue = 0;

	// private final GPU GPU = new GPU();

	/*
	 * MANDELBROT FRACTAL IS THE DOMAIN
	 */
	public Mandelbrot(Design design, AreaDomain areaDomain, AreaImage areaImage) {
		this.design = design;
		this.areaDomain = areaDomain;
		this.areaImage = areaImage;
		/* Create new points for calculation */

		DOMAIN = new MandelbrotDomain(areaDomain, areaImage);
		DOMAIN.domainScreenCreateInitialization();

		FractalMachine.initRingSequence(RING_SEQUENCE_TT, RING_SEQUENCE_XX);
	}

	public void resetAsNew() {
		DOMAIN.elementsScreen = new Element[Application.RESOLUTION_DOMAIN_T][Application.RESOLUTION_DOMAIN_X];
		DOMAIN.domainScreenCreateInitialization();

		DOMAIN.elementsToRemember.clear();
		DOMAIN.domainNotFinished = true;

		maxScrValueNow = 0;
		maxScrValueTotal = 0;
		minScrValue = 0;

		DOMAIN.partStartT = 0;
		DOMAIN.partRowT = 0;
		// DOMAIN.wrapDomain = false;

		DOMAIN.conflictsResolved = 0;
		DOMAIN.bestMatch = null;
		DOMAIN.bestMatchAtT = 0;
		DOMAIN.bestMatchAtX = 0;
		DOMAIN.dist = 0;

		DOMAIN.maskDone = true;
	}

	/*
	 * Calculate Domain Values
	 */
	public void calculate() {
		time.now("CALCULATE");

		// int counterLines = 0;

		/* * 0.001 ... for resolution 400 x 400 it is 160 elements */
		/* To be allowed to diverge and iterationMax won't increase for new calculation */
		// final int maxToNotDivergeYet = (int) (Application.RESOLUTION.t * Application.RESOLUTION.x * 0.0003);
		// time.now(".maxToNotDivergeYet: " + maxToNotDivergeYet);

		FractalFiles.start();

		ArrayList<Element> domainPart;

		int index = 0;
		while (DOMAIN.domainNotFinished) {
			time.red("******************************** 1");
			domainPart = DOMAIN.fetchDomainPart();

			time.red("CALCULATE: " + domainPart.size() + ", domain part remains: " + DOMAIN.domainNotFinished);

			final ExecutorService executor = Executors.newFixedThreadPool(Main.COREs);

			for (Element el : domainPart) {

				Runnable worker = new PathThread(index++, el, areaImage, design);
				executor.execute(worker);

				// countPart++;
				// FractalMachine.calculateProgressPercentage(countPart, countPartAll, partRowT, Application.RESOLUTION_DOMAIN_T, first);
			}

			executor.shutdown();
			while (!executor.isTerminated()) {
				try {
					Thread.currentThread().sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			time.now("Finished all threads");
			time.red("******************************** 2");
		}

		time.now("CALCULATE while END");

		FractalFiles.finish();

		design.calculationFinished();

		FractalEngine.calculationProgress = "";


		DOMAIN.domainNotFinished = true;

		Fractal.update(areaImage, 0);

		time.red("-------------CALCULATE END---------------");
	}

	public void resetOptimizationSoft() {
		Element element;
		for (int t = 0; t < Application.RESOLUTION_DOMAIN_T; t++) {
			for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
				element = DOMAIN.elementsScreen[t][x];
				if (element.isHibernatedBlack() || element.isHibernatedBlack_Neighbour()) {
					element.resetForOptimization();
				}
			}
		}
	}

	public void resetOptimizationHard() {
		// Application.colorPaletteMandelbrot.reset();
		Element element;
		for (int t = 0; t < Application.RESOLUTION_DOMAIN_T; t++) {
			for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
				element = DOMAIN.elementsScreen[t][x];
				if (!element.isActiveMoved()
						&& !element.isHibernatedFinished()
						&& !element.isHibernatedFinishedInside()) {
					element.resetAsNew();
				}
			}
		}
	}

	/* Used for OneTarget */
	public Element getElementAt(int t, int x) {
		try {
			return DOMAIN.elementsScreen[t][x];
		} catch (Exception e) {
			time.e("getElementAt()", e);
			return null;
		}
	}

	public boolean fixOptimizationBreak() {

		time.red(" === fixOptimizationBreak ===");

		/* Last tested pixel is Hibernated as Converged (Calculation finished) */
		Bool lastIsWhite = new Bool();
		/* Last tested pixel is Hibernated as Skipped for calculation (Deep black) */
		Bool lastIsBlack = new Bool();
		ArrayList<Integer> failedNumbersRe = new ArrayList<>();
		ArrayList<Integer> failedNumbersIm = new ArrayList<>();
		/* Test lines left and right */
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				FractalMachine.testOptimizationBreakElement(xx, yy, DOMAIN.elementsScreen[xx][yy], failedNumbersRe, failedNumbersIm, lastIsWhite, lastIsBlack);
			}
			lastIsBlack.setFalse();
			lastIsWhite.setFalse();
		}
		/* Test lines up and down */
		for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
			for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
				FractalMachine.testOptimizationBreakElement(xx, yy, DOMAIN.elementsScreen[xx][yy], failedNumbersRe, failedNumbersIm, lastIsWhite, lastIsBlack);
			}
			lastIsBlack.setFalse();
			lastIsWhite.setFalse();
		}
		/* Fix failed positions */
		/* In worst case failed positions contains same position twice */
		int size = failedNumbersRe.size();
		for (int i = 0; i < size; i++) {
			// Time.now("FIXING: " + position.x + ". " + position.y);
			final int r = Application.TEST_OPTIMIZATION_FIX_SIZE;
			for (int x = -r; x < r; x++) {
				for (int y = -r; y < r; y++) {
					if ((x * x) + (y * y) < (r * r)) {
						// These thing should be much optimized to not do same for points it was already done
						FractalMachine.setActiveMovedIfBlack(failedNumbersRe.get(i) + x, failedNumbersIm.get(i) + y, DOMAIN.elementsScreen);
					}
				}
			}
		}
		return !failedNumbersRe.isEmpty();
	}

	public void fixOptimizationOnClick(int xx, int yy) {
		final int r = 10;
		for (int x = -r; x < r; x++) {
			for (int y = -r; y < r; y++) {
				if ((x * x) + (y * y) < (r * r)) {
					FractalMachine.setActiveMovedIfBlack(xx + x, yy + y, DOMAIN.elementsScreen);
				}
			}
		}
	}

	public void fixDomainOptimizationOnClick(int xx, int yy) {
		final int r = Main.neighbours;
		for (int x = -r; x < r; x++) {
			for (int y = -r; y < r; y++) {
				if ((x * x) + (y * y) < (r * r)) {
					FractalMachine.setActiveToAddToCalculation(xx + x, yy + y, DOMAIN.elementsScreen);
				}
			}
		}
	}

	public Element elementAt(int t, int x) {
		return DOMAIN.elementsScreen[t][x];
	}

	public int getMinScrValue() {
		return this.minScrValue;
	}
}
	
