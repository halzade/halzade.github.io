package fine.fractals.data.objects;

import java.util.ArrayList;

public class DoubleList {

	private ArrayList<Double> a;
	private ArrayList<Double> b;

	public DoubleList(ArrayList<Double> a, ArrayList<Double> b) {
		this.a = a;
		this.b = b;
	}

	public ArrayList<Double> one() {
		return this.a;
	}

	public ArrayList<Double> two() {
		return this.b;
	}
}
