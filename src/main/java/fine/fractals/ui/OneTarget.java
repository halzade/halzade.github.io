package fine.fractals.ui;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.engine.FractalEngine;
import fine.fractals.math.common.Element;
import fine.fractals.math.common.HH;

public class OneTarget {

	private static Time time = new Time(OneTarget.class);

	private String cTextReT = "";
	private String cTextImX = "";
	/* Coordinates counted from top left corner */
	private int scrT;
	private int scrX;
	private int scrCornerT;
	private int scrCornerX;

	private Element mandelbrotElement = null;

	public OneTarget() {
	}

	public void update(int mousePositionT, int mousePositionX) {
		this.scrCornerT = mousePositionT;
		this.scrCornerX = mousePositionX;
		this.scrT = this.scrCornerT - (Application.RESOLUTION_DOMAIN_T / 2);
		this.scrX = this.scrCornerX - (Application.RESOLUTION_DOMAIN_X / 2);

		HH hh = new HH();

		if (!FractalEngine.calculationInProgress) {
			String[] tmp = Application.ME.areaDomain.cToString(hh, this.scrCornerT, this.scrCornerX);
			this.cTextReT = tmp[0];
			this.cTextImX = tmp[1];
			mandelbrotElement = Application.ME.getMandelbrotElementAt(mousePositionT, mousePositionX);
		} else {
			mandelbrotElement = null;
			try {
				String[] tmp = Application.ME.areaDomain.cToString(hh, this.scrCornerT, this.scrCornerX);
				this.cTextReT = tmp[0];
				this.cTextImX = tmp[1];
			} catch (Exception e) {
				time.red("OneTarget.update(" + mousePositionT + ", " + mousePositionX + "): " + e.getMessage());
				this.cTextReT = "-";
				this.cTextImX = "-";
			}
		}
		/* I don't want to Move area domain and area image coordinates in case I want just Zoom in with Space */
	}

	public int getScreenFromCenterT() {
		return this.scrT;
	}

	public int getScreenFromCenterX() {
		return this.scrX;
	}

	public int getScreenFromCornerT() {
		return this.scrCornerT;
	}

	public int getScreenFromCornerX() {
		return this.scrCornerX;
	}

	public String getTextReT() {
		return this.cTextReT;
	}

	public String getTextImX() {
		return this.cTextImX;
	}

	public String getMandelbrotValue() {
		if (this.mandelbrotElement != null) {
			try {
				int value = this.mandelbrotElement.getValue();
				return String.valueOf(value);
			} catch (NullPointerException ex) {
				/* expect int corrected = null */
				// Time.m("Exception: MouseMove -> OneTarget -> getMandelbrotValue - There probably was a calculation exception.");
				time.now(".");
			}
		}
		return "-";
	}

	public String getMandelbrotState() {
		if (this.mandelbrotElement != null) {
			return this.mandelbrotElement.getState().toString();
		}
		return "-";
	}

	public void move(int x, int y) {
		UIRobot.moveMouseBy(x, y);
	}
}


