package fine.fractals.data.comparator;

import fine.fractals.math.common.Element;

import java.util.Comparator;


public class ElementComparator implements Comparator<Element> {

	@Override
	public int compare(Element e1, Element e2) {
		return e1.getLastIteration() - e2.getLastIteration();
	}

}
