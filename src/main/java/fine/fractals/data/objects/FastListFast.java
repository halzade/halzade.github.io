package fine.fractals.data.objects;

import java.util.*;

public class FastListFast implements Iterable<Double>, Iterator<Double> {

	/* ArrayList is much faster then LinkedList */
	private ArrayList<Double> list = new ArrayList<>();
	private Set<Double> set = new HashSet<>();

	private int count = 0;

	public FastListFast() {

	}

	public int size() {
		return list.size();
	}

	public boolean contains(Double d) {
		return set.contains(d);
	}

	public void add(Double d) {
		set.add(d);
		list.add(d);
	}

	public Double get(Integer i) {
		return list.get(i);
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
