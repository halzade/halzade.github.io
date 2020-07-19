package fine.fractals.math;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;
import fine.fractals.ui.Formatter;
import fine.fractals.ui.OneTarget;

public class AreaImage {

	private static Time time = new Time(AreaImage.class);

	/* position of the centre of image area */
	public double centerReT;
	public double centerImX;

	/* size of image area */
	public double sizeReT;
	public double sizeImX;

	private final double[] numbersReT;
	private final double[] numbersImX;

	private double borderLowReT;
	private double borderLowImX;
	private double borderHighReT;
	private double borderHighImX;

	/* Plank's length */
	/* It depends on Height which is resolution domain Y */
	private double plank;

	private int resolutionT;
	private int resolutionX;
	private int resolutionHalfT;
	private int resolutionHalfX;

	public AreaImage(double size, double centerReT, double centerImX, int resolutionT, int resolutionX) {
		this.resolutionT = resolutionT;
		this.resolutionX = resolutionX;
		this.resolutionHalfT = this.resolutionT / 2;
		this.resolutionHalfX = this.resolutionX / 2;

		double scrRatioX = (double) resolutionX / (double) resolutionT;

		this.sizeReT = size;
		this.sizeImX = size * scrRatioX;


		this.centerReT = centerReT;
		this.centerImX = centerImX;
		this.plank = size / resolutionT;

		time.now("plank: " + plank);

		this.numbersReT = new double[resolutionT];
		this.numbersImX = new double[resolutionX];

		// Transformation.init(0);
		initiate();
	}

	public boolean contains(HH hh) {
		return hh.calculation.reT > this.borderLowReT
				&& hh.calculation.reT < this.borderHighReT
				&& hh.calculation.imX > this.borderLowImX
				&& hh.calculation.imX < this.borderHighImX;
	}

	public boolean domainToScreenCarry(HH hh, double reT, double imX) {
		hh.calculation.pxT = (int) Math.round((resolutionT * (reT - this.centerReT) / this.sizeReT) + resolutionHalfT);
		if (hh.calculation.pxT >= resolutionT || hh.calculation.pxT < 0) {
			hh.calculation.pxT = HH.NOT;
			return false;
		}

		hh.calculation.pxX = (int) Math.round(((resolutionX * (imX - this.centerImX)) / this.sizeImX) + resolutionHalfX);
		if (hh.calculation.pxX >= resolutionX || hh.calculation.pxX < 0) {
			hh.calculation.pxX = HH.NOT;
			return false;
		}
		return true;
	}


	/** circle inversion **/
	public void transformCircle(HH hh, double re, double im) {
		double cDiam = 0.25;
		double cOriginRe = -1;
		double cOriginIm = 0;

		double kk = cDiam * cDiam;
		double abs = (re - cOriginRe) * (re - cOriginRe) + (im - cOriginIm) * (im - cOriginIm);
		hh.calculation.reT = kk * (re - cOriginRe) / abs;
		hh.calculation.imX = kk * (im - cOriginIm) / abs;
	}


	public double screenToDomainCreateReT(int t) {
		return numbersReT[t];
	}

	public double screenToDomainCreateImX(int x) {
		return numbersImX[x];
	}

	public void screenToDomainCarry(HH hh, int t, int x) {
		hh.calculation.reT = numbersReT[t];
		hh.calculation.imX = numbersImX[x];
	}


	/**
	 * call after Zoom in or out
	 */
	private void initiate() {
		this.borderLowReT = centerReT - (sizeReT / 2);
		this.borderHighReT = centerReT + (sizeReT / 2);
		this.borderLowImX = centerImX - (sizeImX / 2);
		this.borderHighImX = centerImX + (sizeImX / 2);

		// this.borderLow.imY = center.imY - (size.imY / 2);
		// this.borderHigh.imY = center.imY + (size.imY / 2);
		// this.borderLow.imZ = center.imZ - (size.imZ / 2);
		// this.borderHigh.imZ = center.imZ + (size.imZ / 2);

		calculatePoints();
	}

	public void zoomIn() {
		sizeReT = sizeReT * Application.ZOOM;
		sizeImX = sizeImX * Application.ZOOM;
		this.plank = sizeReT / resolutionT;
		initiate();
	}

	public void zoomOut() {
		sizeReT = sizeReT * (1 / Application.ZOOM);
		sizeImX = sizeImX * (1 / Application.ZOOM);
		this.plank = sizeReT / resolutionT;
		initiate();
	}

	public String[] cToString(HH hh, int t, int x) {
		screenToDomainCarry(hh, t, x);
		return new String[]{
				Formatter.roundString(hh.calculation.reT),
				Formatter.roundString(hh.calculation.imX)
		};
	}

	public String sizeReTString() {
		return Formatter.roundString(this.sizeReT);
	}

	public String sizeImXString() {
		return Formatter.roundString(this.sizeImX);
	}


	public String sizeTString4() {
		return Formatter.round4(this.sizeReT);
	}

	public String sizeImXString4() {
		return Formatter.round4(this.sizeImX);
	}

	public void moveToCoordinates(OneTarget target) {
		this.centerReT = screenToDomainCreateReT(target.getScreenFromCornerT());
		this.centerImX = screenToDomainCreateImX(target.getScreenFromCornerX());
		//this.center.imY = h.imY;
		//this.center.imZ = h.imZ;
		time.now("Move to: " + this.centerReT + "," + this.centerImX);
	}

	/* Generate domain elements */
	private void calculatePoints() {
		for (int tt = 0; tt < resolutionT; tt++) {
			numbersReT[tt] = borderLowReT + (this.plank * tt);
		}
		for (int xx = 0; xx < resolutionX; xx++) {
			numbersImX[xx] = borderLowImX + (this.plank * xx);
		}

		//for (int yy = 0; yy < resolution.y; yy++) {
		//	numbersImY[yy] = borderLow.imY + (this.plank * yy);
		//}
		//for (int zz = 0; zz < resolution.z; zz++) {
		//	numbersImZ[zz] = borderLow.imZ + (this.plank * zz);
		//}
	}

	public double left() {
		return this.borderLowReT;
	}

	public double right() {
		return this.borderHighReT;
	}

	public double top() {
		return this.borderHighImX;
	}

	public double bottom() {
		return this.borderLowImX;
	}

	/**
	 * move to zoom target
	 */
	public void moveToInitialCoordinates() {
		this.centerReT = Fractal.INIT_IMAGE_TARGET_reT;
		this.centerImX = Fractal.INIT_IMAGE_TARGET_imX;
	}
}
