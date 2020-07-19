package fine.fractals.ui;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.engine.FractalEngine;

public class UIRefreshThread extends Thread {

	private static Time time = new Time(UIRefreshThread.class);

	private static final int INTERVAL = 500;

	private static boolean running = false;
	private static int index = 0;
	private int myIndex;

	private UIRefreshThread() {
		this.myIndex = UIRefreshThread.index++;
	}

	public static void runRefreshThread() {
		if (!running) {
			new UIRefreshThread().start();
		} else {
			time.now("don't start new refresh thread (" + index + ")");
		}
	}

	public void run() {
		running = true;
		time.now("ui: (" + this.myIndex + ") refresh START");
		try {
			synchronized (this) {
				wait(INTERVAL);
				while (FractalEngine.calculationInProgress) {
					// time.now("ui: " + this.myIndex + " refresh");
					System.out.print(".");
					Application.ME.repaintMandelbrot();
					wait(INTERVAL);
				}
				time.now("ui: " + this.myIndex + " refresh END");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		running = false;
	}

}
