package fine.fractals;

import fine.fractals.engine.FractalEngine;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Time {

	public static final String S = " - ";
	private static final String SS = " ~ ";
	public static final String I = " | ";
	private static long timeFromReset;
	private static long timeFromStart;

	private String className;

	public Time(Class clazz) {
		String temp = clazz.getSimpleName() + ": ";
		int length = "Mandelbrot: ".length();
		while (temp.length() < length) {
			temp += " ";
		}
		this.className = temp;
	}

	@SuppressWarnings("unused")
	private Time() {
	}

	public static void init() {
		Time.timeFromReset = ctm();
		Time.timeFromStart = ctm();
	}

	public static void reset() {
		Time.timeFromReset = ctm();
	}

	public void now(String message) {
		message = className + message;
		FractalEngine.calculationText = message;
		Time.out(format(millis()) + S + message);
	}

	public void total(String message) {
		message = className + message;
		FractalEngine.calculationText = message;
		Time.out(format(millisTotal()) + SS + message);
	}

	public void red(int message) {
		red(message + "");
	}

	public void red(String message) {
		message = className + message;
		FractalEngine.calculationText = message;
		outRed(format(millis()) + S + message);
	}

	public void e(String message, Exception e) {
		message = className + message;
		FractalEngine.calculationText = message;
		outRed(format(millis()) + S + "EXCEPTION " + message + ": " + e.getMessage());
	}

	public void color(Color c) {
		outRed(format(millis()) + S + "rgb: " + c.getRGB());
	}

	private static String format(long millis) {
		return Application.iteration + S
				+ String.format("%02d:%02d:%02d.%03d",
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
				millis % 1000);
	}

	private static void out(String s) {
		System.out.println(s);
	}

	private static void outRed(String s) {
		System.err.println(s);
	}

	synchronized public static void waitHere() {
		try {
			Thread.currentThread();
			Thread.sleep(Application.TIME_OUT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	synchronized public static void waitSeconds(Time time, int seconds) {
		try {
			time.now("Time: wait for " + seconds + "s");
			Thread.currentThread();
			Thread.sleep(seconds * 1000);
			time.now("Time: wait finished");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static long millis() {
		return ctm() - Time.timeFromReset;
	}

	private static long millisTotal() {
		return ctm() - Time.timeFromStart;
	}

	private static long ctm() {
		return System.currentTimeMillis();
	}

}
