package fine.fractals.data.objects;

import java.util.ArrayList;

public class Path {

	/* ArrayList is much faster then LinkedList*/
	private ArrayList<Double> pathReT;
	private ArrayList<Double> pathImX;

	private int length;


	public Path(FastList pathReT, FastList pathImX) {
		this.pathReT = pathReT.list();
		this.pathImX = pathImX.list();
		this.length = pathReT.size();
	}

	public int length() {
		return this.length;
	}

	public double getTAt(int i) {
		return pathReT.get(i);
	}

	public double getXAt(int i) {
		return pathImX.get(i);
	}

}
