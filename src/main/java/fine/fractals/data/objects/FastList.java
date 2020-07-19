package fine.fractals.data.objects;

import fine.fractals.fractal.Fractal;

import java.util.*;

public class FastList implements Iterable<Double>, Iterator<Double> {

	/* ArrayList is simply faster */
	private ArrayList<Double> list = new ArrayList<>();
	private Set<Double> set = null;

	private int count = 0;

	public FastList() {
		if (Fractal.ONLY_LONG_ORBITS) {
			this.set = new HashSet<>();
		}
	}

	public int size() {
		return list.size();
	}

	public boolean contains(Double d) {
		return set.contains(d);
	}

	public void add(Double d) {
		if (Fractal.ONLY_LONG_ORBITS) {
			set.add(d);
		}
		list.add(d);
	}

	public Double get(Integer i) {
		return list.get(i);
	}

	public void remove(int i) {
		list.remove(i);
		// TODO set
		// TODO try with linkedList again
	}

	@Override
	public Iterator<Double> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return count < list.size();
	}

	@Override
	public Double next() {
		if (count == list.size()) {
			throw new NoSuchElementException();
		}
		count++;
		return list.get(count - 1);
	}

	public ArrayList<Double> list() {
		return list;
	}
}
