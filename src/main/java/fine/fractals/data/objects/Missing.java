package fine.fractals.data.objects;

import fine.fractals.math.common.Element;

public class Missing {

	public Element element;
	public int iterateTo;
	public FastList originPathRe;
	public FastList originPathIm;

	public Missing(Element element, int iterateTo, FastList originPathRe, FastList originPathIm) {
		this.element = element;
		this.iterateTo = iterateTo;
		this.originPathRe = originPathRe;
		this.originPathIm = originPathIm;
	}
}
