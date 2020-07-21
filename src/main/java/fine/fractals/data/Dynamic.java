package fine.fractals.data;

import fine.fractals.Time;
import fine.fractals.data.objects.FastList;
import fine.fractals.data.objects.Missing;
import fine.fractals.fractal.Fractal;
import fine.fractals.math.AreaImage;
import fine.fractals.math.common.Element;
import fine.fractals.math.common.HH;

import java.util.*;

public class Dynamic {

	private static Time time = new Time(Dynamic.class);

	/**
	 * re; im; color
	 */
	private final LinkedHashMap<Double, LinkedHashMap<Double, Integer>> dynamicRowRe = new LinkedHashMap<>();

	private AreaImage areaImage;
	private Screen screen;

	public Dynamic(Screen screen) {
		this.screen = screen;
	}

	public void setAreaImage(AreaImage areaImage) {
		this.areaImage = areaImage;
	}

	private int co = 0;

	/* All elements on escape path are already inside displayed area */
	// TODO
	public synchronized void addEscapePathInside(FastList originPathRe, FastList originPathIm) {

		LinkedHashMap<Double, Integer> dynamicRowIm;
		double imX;

		HH hh = new HH();
		int size = originPathRe.size();

		int i = 0;
		for (Double reT : originPathRe) {
			dynamicRowIm = this.dynamicRowRe.get(reT);
			imX = originPathIm.get(i);


			Fractal.ME.colorsFor(hh, i + 1, size);
			Integer pathElementColor = Screen.hhColorToInt(hh);


			if (dynamicRowIm != null) {
				if (!dynamicRowIm.containsKey(imX)) {
					/* Almost all rowIm have length one */
					/* Remove different paths hit same element */
					dynamicRowIm.put(imX, pathElementColor);
				} else {
					co++;
				}
			} else {
				dynamicRowIm = new LinkedHashMap<>();
				dynamicRowIm.put(imX, pathElementColor);
				this.dynamicRowRe.put(reT, dynamicRowIm);
			}
			i++;
		}
		// time.red(co);
	}

	public void addIfMissing(LinkedList<Missing> missingList) {
		double x;
		double xNew;
		double y;
		double originRe = 0;
		double originIm = 0;
		int iterator;
		Element element;

		ArrayList<Double> missingOriginPathRe = new ArrayList<>();
		ArrayList<Double> missingOriginPathIm = new ArrayList<>();

		HH hh = new HH();

		int missingNo = 1;
		int addedMissingElementsTotal = 0;
		int wastedMissingElementsTotal = 0;

		for (Missing missing : missingList) {
			int addedMissingElements = 0;
			int wastedMissingElements = 0;
			element = missing.element;
			x = 0;
			y = 0;
			/* Some hits inside may have been found already by iterating from IterationLast to current IterationMax */
			originRe = element.originReT;
			originIm = element.originImX;
			iterator = 0;

			/* Original */
			while (iterator < missing.iterateTo) {

				// TODO
				xNew = (x * x) - (y * y) + originRe;
				hh.calculation.imX = 2 * x * y + originIm;
				hh.calculation.reT = xNew;

				if (areaImage.contains(hh)) {
					missingOriginPathRe.add(x);
					missingOriginPathIm.add(y);
				}
				iterator++;
			}

			if (missing.originPathRe.size() + missingOriginPathRe.size() > Fractal.ITERATION_MAX) {
				// Add Missing  path to Fractal

				element.setHibernatedFinishedInside();
				missing.originPathRe.list().addAll(missingOriginPathRe);
				missing.originPathIm.list().addAll(missingOriginPathIm);

				/* Add missing to Fractal here */
				// TODO
				// time.now("* add path inside START");
				// this.addEscapePathInside(missing.originPathRe, missing.originPathIm);
				// time.now("* add path inside END");

				addedMissingElements += missing.originPathRe.size();
				addedMissingElementsTotal += addedMissingElements;
			} else {
				// Don't add this path
				wastedMissingElements += iterator;
				wastedMissingElementsTotal += wastedMissingElements;
			}
			missingOriginPathRe.clear();
			missingOriginPathIm.clear();
			missingNo++;
		}
	}

	public void domainToScreenGrid() {

		int removed = 0;

		HH hh = new HH();
		double im;

		Set<Double> rowRe = this.dynamicRowRe.keySet();
		for (Double re : rowRe) {
			LinkedHashMap<Double, Integer> dynamicRowIm = this.dynamicRowRe.get(re);

			if (dynamicRowIm != null) {

				Iterator<Double> imIterator = dynamicRowIm.keySet().iterator();

				while (imIterator.hasNext()) {
					im = imIterator.next();

					int color = dynamicRowIm.get(im);
					Screen.intToHHColor(color, hh);

					this.areaImage.domainToScreenCarry(hh, re, im);

					if (hh.calculation.pxT != HH.NOT && hh.calculation.pxX != HH.NOT) {
						/* Multiple elements hit same pixel */

						this.screen.add(hh);

					} else {
						/* remove im on rowRe */
						removed++;
						imIterator.remove();
					}
				}
			} else {
				rowRe.remove(re);
			}
		}
		time.now("* Removed: " + removed);

		/* Elements im on rowRw were remove already while iterating with Iterator.remove */

		/* Remove full rowRe, if it moved outside of area with all its row Ims */
		final double le = areaImage.left();
		final double ri = areaImage.right();

		this.dynamicRowRe.entrySet().removeIf(entry -> entry.getKey() < le || entry.getKey() > ri);
		/* Remove rowsRe where all Ims were removed */
		this.dynamicRowRe.entrySet().removeIf(entry -> entry.getValue().size() == 0);
	}

	public void clearScreenValues() {
		time.red("CLEAR SCREEN VALUES");
		this.screen.clear();
	}

}
