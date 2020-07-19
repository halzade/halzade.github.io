package fine.fractals.engine;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.data.objects.Data;
import fine.fractals.ui.UIRefreshThread;

public class CalculationThread extends Thread {

	private static Time time = new Time(CalculationThread.class);

	public static CalculationThread ME;

	private int counter;
	private static String message;

	private CalculationThread(int counter) {
		ME = this;
		this.counter = counter;
	}

	@Override
	public void run() {
		do {
			Data.archive();
			Time.reset();
			time.total("Iteration: " + Application.iteration++);

			UIRefreshThread.runRefreshThread();

			/* Calculate Fractal values */
			FractalEngine.ME.calculateFromThread(this.counter);

			if (Application.REPEAT) {
				Application.ME.zoomIn();
				FractalEngine.setFixInProgress(false);
				Time.waitHere();
				Application.ME.repaint();
			}
			if (Application.iteration == 1) {
				/* Move to coordinates after initialization of FractalEngine, tj Mandelbrot initial domain */
				Application.ME.areaDomain.moveToInitialCoordinates();
				Application.ME.areaImage.moveToInitialCoordinates();
			}

			FractalEngine.setFixInProgress(false);
		} while (Application.REPEAT);

		if (message != null) {
			time.now(message);
		}
	}

	synchronized public static void calculate(int counter) {
		calculate(counter, null);
	}

	synchronized public static void calculate(int counter, String message) {
		CalculationThread thread = new CalculationThread(counter);
		thread.start();
		Application.ME.repaint();

		if (message != null) {
			CalculationThread.message = message;
		}
	}

	public static void joinMe() {
		try {
			ME.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
