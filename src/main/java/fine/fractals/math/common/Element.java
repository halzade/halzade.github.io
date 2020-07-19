package fine.fractals.math.common;

import fine.fractals.Time;
import fine.fractals.data.objects.State;

public class Element extends ElementAbstract implements Comparable<Element> {

	public final double originReT;
	public final double originImX;
	// public final double originImY;
	// public final double originImZ;

	public double lastVisitedReT;
	public double lastVisitedImX;
	// public double lastVisitedImY;
	// public double lastVisitedImZ;

	public Element(double originReT, double originImX) {
		this.originReT = originReT;
		this.originImX = originImX;
	}

	public void resetAsNew() {
		super.state = State.ActiveNew;

		this.lastVisitedReT = 0;
		this.lastVisitedImX = 0;

		super.lastIteration = 0;
		super.value = null;
	}

	// TODO this should been used
	// public void setLastVisited(double reT, double imX, double imY, double imZ) {
	public void setLastVisited(double reT, double imX) {
		this.lastVisitedReT = reT;
		this.lastVisitedImX = imX;
		// this.lastVisitedImY = imY;
		// this.lastVisitedImZ = imZ;
	}

	@Override
	public int compareTo(Element other) {
		return Integer.compare(this.getValue(), other.getValue());
	}

	@Override
	public String toString() {
		return this.originReT + Time.I + this.originImX;
		// return this.origin.reT + Time.I + this.origin.imX + Time.I + this.origin.imY + Time.I + this.origin.imZ;
	}
}
