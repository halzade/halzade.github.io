package fine.fractals;

import fine.fractals.data.FractalFiles;
import fine.fractals.engine.CalculationThread;
import fine.fractals.engine.FractalEngine;
import fine.fractals.fractal.Fractal;
import fine.fractals.math.AreaDomain;
import fine.fractals.math.AreaImage;
import fine.fractals.math.common.Element;
import fine.fractals.ui.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Application {

	/**
	 * Change this if changes affects Files for Domain or Fractal
	 */
	public static final String VERSION = "v3";
	public static final String USER_HOME = System.getProperty("user.home");

	/**
	 * To Render Images for a ZOOM Video.
	 * 1.
	 * set REPEAT = true
	 * Don't use any fractals with optimization.
	 * Try FineMandelbrotZoom()
	 *
	 * 2.
	 * set DESIGN_STATIC = Boolean.FALSE;
	 * To remember all the element paths calculated in the previous iterations
	 */
	public static boolean REPEAT = false;
	public static final double ZOOM = 0.7; // 0.98

	private static Time time = new Time(Application.class);

	public static final String APP_NAME = "_" + Formatter.now();

	public static int RESOLUTION_DOMAIN_T = 1000;
	public static int RESOLUTION_DOMAIN_X = 1000;

	public static int RESOLUTION_IMAGE_SAVE_FOR = 2000; // 720

	/**
	 * Use:
	 * - TRUE to make images
	 * - FALSE to make videos
	 */
	public static boolean DESIGN_STATIC = Boolean.TRUE;

	/* Design should have approximately this many elements per pixel */
	// public static final int AVERAGE_ELEMENTS_PP = 34;

	public static int TIME_OUT = 100; // ms

	public static final int TEST_OPTIMIZATION_FIX_SIZE = 4; // 6 | from -it to it.
	/* Increase this only in CalculationThread */
	public static int iteration = 0;
	// public static int FRACTAL_TOTAL_SIZE; // 1
	// public static boolean SKIP_CALCULATION = false;


	public AreaDomain areaDomain;
	public AreaImage areaImage;
	// private boolean countingWithoutPrecision = true;

	private FractalEngine fractalEngine;
	private final ApplicationWindow applicationWindow;
	private FractalWindow fractalWindow;

	final BufferedImage designImage = new BufferedImage(Main.RESOLUTION_IMAGE_T, Main.RESOLUTION_IMAGE_X, BufferedImage.TYPE_INT_RGB);

	private final OneTarget target;
	private boolean drawRectangle = false;
	public static boolean addText = false;

	public static Application ME;

	public Application() {
		ME = this;

		Time.init();

		/* moveToInitialCoordinates after first rendering in CalculationThread, To make a ZOOM video so that so that first frame is rendered correctly */
		// this.area = new Area(Library.INITIAL_AREA_SIZE, 0, 0, RESOLUTION_X, RESOLUTION_Y);

		/* moveToInitialCoordinates immediately */
		this.areaDomain = new AreaDomain(Fractal.INIT_AREA_DOMAIN_SIZE, Fractal.INIT_DOMAIN_TARGET_reT, Fractal.INIT_DOMAIN_TARGET_imX, RESOLUTION_DOMAIN_T, RESOLUTION_DOMAIN_X);
		this.areaImage = new AreaImage(Fractal.INIT_AREA_IMAGE_SIZE, Fractal.INIT_IMAGE_TARGET_reT, Fractal.INIT_IMAGE_TARGET_imX, Main.RESOLUTION_IMAGE_T, Main.RESOLUTION_IMAGE_X);

		this.target = new OneTarget();
		UIMouseListener uiMouseListener = new UIMouseListener();
		UIKeyDispatcher uiKeyDispatcher = new UIKeyDispatcher();
		UIMouseWheelListener uiMouseWheelListener = new UIMouseWheelListener();

		this.fractalEngine = new FractalEngine(designImage);

		this.applicationWindow = new ApplicationWindow(target, uiMouseListener, uiMouseWheelListener, uiKeyDispatcher, RESOLUTION_DOMAIN_T, RESOLUTION_DOMAIN_X, "Application - " + Fractal.NAME + " - " + APP_NAME);
		this.fractalWindow = new FractalWindow(target, uiMouseListener, uiMouseWheelListener, uiKeyDispatcher, designImage, Main.RESOLUTION_IMAGE_T, Main.RESOLUTION_IMAGE_X, Fractal.NAME + " - " + APP_NAME);

		this.fractalWindow.setApplicationWindow(applicationWindow);
		this.applicationWindow.setDesignWindow(fractalWindow);


		/* Minimize windows */
		if (Main.RESOLUTION_IMAGE_X > 1000) {
			if (Main.DO_FILES && FractalFiles.existsDesign()) {
				time.now("== hide application window");
				this.applicationWindow.frame.setState(Frame.ICONIFIED);
			} else {
				time.now("== hide fractal window");
				this.fractalWindow.frame.setState(Frame.ICONIFIED);
			}
		}

		/* set window positions*/
		int scrWidth = 1920;
		int scrHeight = 1080;
		if (Main.RESOLUTION_IMAGE_X < scrHeight && Main.RESOLUTION_IMAGE_T < scrWidth) {
			int panel = 50;
			int borders = 2;
			int top = scrHeight - panel - Main.RESOLUTION_IMAGE_X;
			if (top < 0) {
				top = 0;
			}
			fractalWindow.frame.setLocation(scrWidth - (RESOLUTION_DOMAIN_T + borders), top);
			applicationWindow.frame.setLocation(scrWidth - (2 * (RESOLUTION_DOMAIN_T + borders)), top);
		}
	}


	public void execute() {
		CalculationThread.calculate(0);
		CalculationThread.joinMe();
	}

	/*********************************************************************************************/

	public boolean getDrawRectangle() {
		return this.drawRectangle;
	}

	public void toggleRectangle() {
		this.drawRectangle = !this.drawRectangle;
	}

	public void repaint() {
		this.fractalWindow.frame.repaint();
		this.applicationWindow.frame.repaint();
	}

	public static void rep() {
		Application.ME.repaint();
	}

	public void repaintMandelbrot() {
		applicationWindow.frame.repaint();
	}

	public void dispose() {
		this.fractalWindow.frame.dispose();
		this.applicationWindow.frame.dispose();
	}

	public OneTarget getTarget() {
		return this.target;
	}

	public void fixDomainOptimizationSoftCareful() {
		this.fractalEngine.resetDomainOptimizationSoftCareful();
	}

	public void fixDomainOptimizationHardCareful() {
		this.fractalEngine.resetDomainOptimizationHardCareful();
	}

	public void zoomIn() {
		time.now("ZOOM IN");
		this.areaDomain.zoomIn();
		this.areaImage.zoomIn();
		this.fractalEngine.updateDomain();
	}

	public void zoomOut() {
		time.now("ZOOM OUT");
		this.areaImage.zoomOut();
		this.areaDomain.zoomOut();
		this.fractalEngine.updateDomain();
	}

	public FractalEngine getEngine() {
		return this.fractalEngine;
	}

	public Element getMandelbrotElementAt(int mousePositionT, int mousePositionX) {
		return this.fractalEngine.getMandelbrotElementAt(mousePositionT, mousePositionX);
	}

}
