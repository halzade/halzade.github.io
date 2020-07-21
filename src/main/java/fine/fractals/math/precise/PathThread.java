package fine.fractals.math.precise;

import fine.fractals.Main;
import fine.fractals.Time;
import fine.fractals.data.FractalFiles;
import fine.fractals.data.objects.FastList;
import fine.fractals.fractal.Fractal;
import fine.fractals.math.AreaImage;
import fine.fractals.math.Design;
import fine.fractals.math.common.Element;
import fine.fractals.math.common.HH;

public class PathThread implements Runnable {

	private static Time time = new Time(PathThread.class);

	private int myId;
	private Element el;
	private AreaImage areaImage;
	private Design design;

	private HH hh = new HH();

	public PathThread(int myId, Element el, AreaImage areaImage, Design design) {
		this.myId = myId;
		this.el = el;
		this.areaImage = areaImage;
		this.design = design;
	}

	public void run() {
//			/* Tested that it is faster if both are ArrayLists (The difference could be in addEscapePathInside) */
			FastList originPathReT = new FastList();
			FastList originPathImX = new FastList();

			int iterator = el.getLastIteration();
			int orbitHit = 0;

			hh.calculation.reT = el.originReT;
			hh.calculation.imX = el.originImX;

			while (hh.quadrance() < Fractal.CALCULATION_BOUNDARY && iterator < Fractal.ITERATION_MAX) {

				/*
				 * Calculation happens only here
				 */
				Main.FRACTAL.math(hh, el.originReT, el.originImX);

				/** There are Three possible contains: Domain zoomed, Image, Domain full */
				if (areaImage.contains(hh)) {
					/* Calculation did not diverge */
					if (Fractal.ONLY_LONG_ORBITS) {
						/* for Tiara like fractals */

						/* TODO This is totally unreliable as contains may be on completely different indexes. ?
						// But results seem to be same as not optimized, but twice as fast computed. */
						if (originPathReT.contains(hh.calculation.reT) && originPathImX.contains(hh.calculation.imX)) {
							orbitHit++;

							// TODO stop calculation?

						} else {
							originPathReT.add(hh.calculation.reT);
							originPathImX.add(hh.calculation.imX);
						}
					} else {
						/* for Tiara like fractals */
						originPathReT.add(hh.calculation.reT);
						originPathImX.add(hh.calculation.imX);
					}
				}
				iterator++;

				if (Fractal.ONLY_LONG_ORBITS) {
					/* for Tiara like fractals */
					if (iterator < Fractal.ITERATION_MIN && orbitHit > 2) {
						iterator = Fractal.ITERATION_MAX;
						break;
					}
					if (orbitHit > Fractal.ITERATION_MIN / 2) {
						iterator = Fractal.ITERATION_MAX;
						break;
					}
				}
			}

			boolean pathTest;
			if (Fractal.ONLY_LONG_ORBITS) {
				/** Tiara like fractals **/
				pathTest = iterator == Fractal.ITERATION_MAX;
			} else {
				/** Mandelbrot like fractals **/
				pathTest = iterator < Fractal.ITERATION_MAX;
			}


			if (pathTest) {
				/* Element diverged */
				el.setValues(iterator);
				/* Don't set last iteration; I will need test if it was 0 bellow. It is set in last else */
				/* This state may latter change to hibernatedFinishedInside */
				el.setHibernatedFinished();
				/* Divergent paths for Design */

				/** PATH size may DIFFER based on contains */
				if (el.getLastIteration() == 0 && originPathReT.size() > Fractal.ITERATION_MIN) {
					// if (el.getLastIteration() == 0) {

					/* This isn't continuation of unfinished iteration from previous calculation */

					el.setHibernatedFinishedInside();

					design.addEscapePathToSpectraNow(hh, originPathReT, originPathImX);

					FractalFiles.addNowDomainEl(el.originReT, el.originImX);
				}
			}

			//countPart++;
			//synchronized (this) {
			//	FractalMachine.calculateProgressPercentage(countPart, countPartAll, partRowT, Application.RESOLUTION_DOMAIN_T, first);
			//}
	}

}
