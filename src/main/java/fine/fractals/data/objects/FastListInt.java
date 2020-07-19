package fine.fractals.data.objects;

import java.util.*;

public class FastListInt implements Iterable<Integer>, Iterator<Integer> {

	/* ArrayList is much faster then LinkedList */
	private ArrayList<Integer> list = new ArrayList<>();
	private Set<Integer> set = new HashSet<>();

	private int count = 0;

	public FastListInt() {
	}

	public int size() {
		return list.size();
	}

	public boolean contains(Integer i) {
		return set.contains(i);
	}

	public void add(Integer i) {
		set.add(i);
		list.add(i);
	}

	public Integer get(Integer i) {
		return list.get(i);
	}

	@Override
	public Iterator<Integer> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		return count < list.size();
	}

	@Override
	public Integer next() {
		if (count == list.size()) {
			throw new NoSuchElementException();
		}
		count++;
		return list.get(count - 1);
	}

	public ArrayList<Integer> list() {
		return list;
	}
}
