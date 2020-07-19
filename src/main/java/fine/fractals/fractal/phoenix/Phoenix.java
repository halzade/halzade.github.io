package fine.fractals.fractal.phoenix;

import fine.fractals.fractal.Fractal;
import fine.fractals.math.common.HH;

public abstract class Phoenix extends Fractal {

	protected double c;
	protected double p;

	public Phoenix(String name) {
		super(name);
	}

	protected void init(double c, double p) {
		this.c = c;
		this.p = p;
	}

	/* zk+1 = zk2 + c + p·zk-1 */
	protected void phoenix(HH hh, double originReT, double originImX) {
		hh.square();

		hh.calculation.reT += c;
		hh.calculation.reT += p * hh.calculation.ppT;
		hh.calculation.imX += p * hh.calculation.ppX;

		// previous iteration
		hh.calculation.ppT = hh.calculation.pT;
		hh.calculation.ppX = hh.calculation.pX;
		hh.calculation.pT = hh.calculation.reT;
		hh.calculation.pX = hh.calculation.imX;

		hh.plus(originReT, originImX);
	}

	public double getC() {
		return this.c;
	}

	public double getP() {
		return this.p;
	}

}
