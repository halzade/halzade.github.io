package fine.fractals.math.mandelbrot;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.Main;
import fine.fractals.color.things.ScreenColor;
import fine.fractals.data.objects.Bool;
import fine.fractals.engine.FractalMachine;
import fine.fractals.fractal.Fractal;
import fine.fractals.math.AreaDomain;
import fine.fractals.math.AreaImage;
import fine.fractals.math.common.Element;
import fine.fractals.math.common.HH;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class MandelbrotDomain {

	private Time time = new Time(MandelbrotDomain.class);

	Element[][] elementsScreen = new Element[Application.RESOLUTION_DOMAIN_T][Application.RESOLUTION_DOMAIN_X];
	ArrayList<Element> elementsToRemember = new ArrayList<>();

	private AreaDomain areaDomain;
	private AreaImage areaImage;

	int conflictsResolved;
	Element bestMatch;
	int bestMatchAtT;
	int bestMatchAtX;
	double dist;

	int partStartT = 0;
	int partRowT = 0;
	// boolean wrapDomain = false;


	boolean domainNotFinished = true;

	static boolean maskDone = true;

	public MandelbrotDomain(AreaDomain areaDomain, AreaImage areaImage) {
		this.areaDomain = areaDomain;
		this.areaImage = areaImage;
	}

	public final void domainScreenCreateInitialization() {
		for (int t = 0; t < Application.RESOLUTION_DOMAIN_T; t++) {
			for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
				Element element = new Element(areaDomain.screenToDomainReT(t), areaDomain.screenToDomainImX(x));

				if (Fractal.OPTIMIZE_SYMMETRY && element.originImX < 0) {
					continue;
				}

				/* OPTIMIZATION will have elements on screen (pink) but won't be fetched for calculation */

				elementsScreen[t][x] = element;
			}
		}
	}


	/*
	 * This is called After zoom in
	 */
//	public void domainForThisZoom() {
//
//		if (Venture.RESOLUTION_MULTIPLIER > 1) {
//			throw new RuntimeException("Domain zoom not ready for zoom with resolution multiplier");
//		}
//		if (Fractal.OPTIMIZE_SYMMETRY) {
//			throw new RuntimeException("Can't zoom with optimize symmetry");
//		}
//
//
//		if (areaDomain.sizeImX < 0.000000000000005) {
//			time.total("STOP CALCULATION domain: " + areaDomain.sizeImX);
//			time.total("STOP CALCULATION image: " + areaImage.sizeImX);
//			Application.REPEAT = false;
//		}
//
//		/**
//		 * Scan area domain elements (old positions from previous calculation) to be -
//		 * moved to new positions (remembered) or skipped the calculation for them.
//		 */
//		int value;
//		int valuesRemembered = 0;
//		int newHibernatedBlack = 0;
//		long allValues = 0;
//		Element element;
//		for (int t = 0; t < Application.RESOLUTION_DOMAIN_T; t++) {
//			for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
//
//				element = elementsScreen[t][x];
//				if (areaDomain.contains(element.originReT, element.originImX)) {
//					/* Move elements to new coordinates */
//					if (element.isHibernatedBlack_Neighbour()) {
//						element.setHibernatedBlack();
//					} else if (!element.isHibernatedBlack()
//							&& !element.isHibernatedBlack_Neighbour()
//							&& !element.isFixed()
//							&& FractalMachine.isVeryDeepBlack(t, x, elementsScreen)) {
//						element.setHibernatedBlack();
//						newHibernatedBlack++;
//					} else {
//						if (element.isActiveNew()) {
//							element.setActiveMoved();
//						}
//					}
//					elementsToRemember.add(element);
//					value = element.getValue();
//					if (value != 0) {
//						allValues += value;
//						valuesRemembered++;
//					}
//				}
//				/* clear color set for debug */
//				element.setColor(null);
//			}
//		}
//
//		time.now("newHibernatedBlack " + newHibernatedBlack);
//		time.now("valuesRemembered " + valuesRemembered);
//		time.now("elementsToRemember " + elementsToRemember.size());
//
//		if (valuesRemembered != 0) {
//			time.now("Average value:  " + (allValues / valuesRemembered));
//		} else {
//			time.red("Average value: valuesRemembered is ZERO");
//		}
//		time.now("New black:      " + newHibernatedBlack);
//
//		/**
//		 * Delete all elements assigned to screen coordinates.
//		 * Some are remembered and will be moved.
//		 */
//		for (int t = 0; t < Application.RESOLUTION_DOMAIN_T; t++) {
//			for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
//				elementsScreen[t][x] = null;
//			}
//		}
//
//		/**
//		 * Add remembered elements to their new position (after zoom) for new calculation
//		 */
//		int conflictsFound = 0;
//		int newPositionT;
//		int newPositionX;
//		Element done;
//
//		int addedToNewPositions = 0;
//
//		HH hh = new HH();
//
//		LinkedList<Element>[][] conflicts = new LinkedList[Application.RESOLUTION_DOMAIN_T][Application.RESOLUTION_DOMAIN_X];
//		for (Element el : elementsToRemember) {
//			/* Get screen position if inside Area domain */
//			if (areaDomain.domainToScreenCarry(hh, el.originReT, el.originImX)) {
//
//				newPositionT = hh.calculation.pxT;
//				newPositionX = hh.calculation.pxX;
//
//				if (newPositionT != HH.NOT
//						&& newPositionX != HH.NOT) {
//					done = elementsScreen[newPositionT][newPositionX];
//					if (done != null) {
//						/* Conflict */
//						if (conflicts[newPositionT][newPositionX] == null) {
//							conflicts[newPositionT][newPositionX] = new LinkedList<>();
//						}
//						conflictsFound++;
//						// el.setColor(Color.BLACK);
//						conflicts[newPositionT][newPositionX].add(el);
//					} else {
//						/* OK; no conflict */
//						elementsScreen[newPositionT][newPositionX] = el;
//						addedToNewPositions++;
//					}
//				}
//			}
//		}
//
//		time.now("AddedToNewPositions " + addedToNewPositions);
//
//		time.now("FIX CONFLICTS " + conflictsFound);
//		conflictsResolved = 0;
//		LinkedList conflictsOnPixel;
//		/** Resolve found conflicts */
//		/** More Elements hit same pixel after zoom */
//		for (int t = 0; t < Application.RESOLUTION_DOMAIN_T; t++) {
//			for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
//				conflictsOnPixel = conflicts[t][x];
//				if (conflictsOnPixel != null) {
//
//		 			/* Add the initial conflict */
//					element = elementsScreen[t][x];
//					//noinspection unchecked
//					conflictsOnPixel.add(element);
//
//		 			/* Find best match for the pixel with conflicts */
//					//noinspection unchecked
//					elementsScreen[t][x] = bestMatch(hh, t, x, conflictsOnPixel);
//
//		 			/* Find best match for pixels around */
//					if (!conflictsOnPixel.isEmpty()) {
//						//noinspection unchecked
//						dropBestMatchToEmptyNeighbour(hh, t, x, conflictsOnPixel);
//					}
//					if (!conflictsOnPixel.isEmpty()) {
//						//noinspection unchecked
//						dropBestMatchToEmptyNeighbour(hh, t, x, conflictsOnPixel);
//					}
//					if (!conflictsOnPixel.isEmpty()) {
//						//noinspection unchecked
//						dropBestMatchToEmptyNeighbour(hh, t, x, conflictsOnPixel);
//					}
//					conflicts[t][x] = null;
//				}
//			}
//		}
//
//		time.now("CONFLICTS FOUND / RESOLVED: " + conflictsFound + " / " + conflictsResolved);
//
//	   /*
//		 * Create new elements on positions where nothing was moved to
//	    */
//		int createdNewElements = 0;
//		Element newElement;
//		for (int t = 0; t < Application.RESOLUTION_DOMAIN_T; t++) {
//			for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
//				if (elementsScreen[t][x] == null) {
//					newElement = new Element(areaDomain.screenToDomainReT(t), areaDomain.screenToDomainImX(x));
//					elementsScreen[t][x] = newElement;
//					createdNewElements++;
//				}
//			}
//		}
//		time.now("createdNewElements " + createdNewElements);
//
//		/**
//		 * Calculation for some positions should be skipped as they are to far away form any divergent position. (They are deep black)
//		 * Skipp also calculation for their neighbours. (Black neighbour)
//		 */
//		final int r = 3;
//		final int rr = r * r;
//		int markedAsDeepBlack = 0;
//		for (int i = 0; i < Application.RESOLUTION_DOMAIN_T; i++) {
//			for (int j = 0; j < Application.RESOLUTION_DOMAIN_X; j++) {
//				element = elementsScreen[i][j];
//				if (element.isHibernatedBlack()) {
//					/* Set all element's in neighborhood as deep black */
//					for (int t = -r; t < r; t++) {
//						for (int x = -r; x < r; x++) {
//							if ((t * t) + (x * x) < rr) {
//								int t1 = i + t;
//								int x1 = j + x;
//								// y = 0;
//								// z = 0;
//								FractalMachine.setAsDeepBlack(t1, x1, elementsScreen);
//								markedAsDeepBlack++;
//							}
//						}
//					}
//				}
//			}
//		}
//
//		time.now("markedAsDeepBlack:   " + markedAsDeepBlack);
//		elementsToRemember.clear();
//	}

	synchronized ArrayList<Element> fetchDomainPart() {
		// if (Venture.DO_FILES && FractalFiles.existsDomain()) {
		// 	time.now(" DOMAIN from File");
		// 	// return FractalFiles.getDomain();
		// 	throw new RuntimeException("load Design instead");
		// } else {
		time.now("DOMAIN created new");

		Element elementZero;
		ArrayList<Element> domainPart = new ArrayList<>();

		/**
		 * Add active elements to be filtered by GPU.
		 * Only interesting will be put to cd.domainGPUFiltered ... Some element diverged in their neighbourhood
		 * Center element was already calculated without wrapping
		 */
		Element[] wrapping = null;

		final boolean wrapDomain = Main.RESOLUTION_MULTIPLIER > 1;
		time.now("wrapDomain " + wrapDomain);

		if (wrapDomain) {
			wrapping = new Element[Main.RESOLUTION_MULTIPLIER * Main.RESOLUTION_MULTIPLIER];
		}

		final int MAX = 4_000_000;
		long elementCountPart = 0;

		long wrappedCount = 0;
		long notWrappedCount = 0;

		time.now("partStartT: " + partStartT);
		partRowT = 0;
		int t;

		for (t = partStartT; t < Application.RESOLUTION_DOMAIN_T; t++) {
			for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
				/* Go through domain and queue elements to be filtered by GPU */
				elementZero = elementsScreen[t][x];

				boolean isActive = elementZero != null && elementZero.isActiveAny();
				boolean optimizationPassed = elementZero != null && Fractal.ME.optimize(elementZero.originReT, elementZero.originImX);

				if (!optimizationPassed && isActive) {
					elementZero.setColor(ScreenColor._OPTIMIZATION);
					continue;
				}
				if (!wrapDomain) {
					/* First calculation */
					if (isActive && optimizationPassed) {
						elementCountPart++;
						domainPart.add(elementZero);
					}
				} else {
					/* Wrapping of first calculation*/
					boolean shouldBeWrapped = elementZero != null;// && FractalMachine.someNeighboursFinishedInside(t, x, elementsScreen);
					if (shouldBeWrapped) {
						wrappedCount++;
						elementZero.setMarked();
						areaDomain.wrap(elementZero, wrapping);
						// elementZero.setColor(FColor.MARK2);
						for (Element el : wrapping) {
							elementCountPart++;
							domainPart.add(el);
						}
					} else {
						notWrappedCount++;
					}
				}
			}
			partRowT++;
			if (elementCountPart >= MAX) {
				time.red("TO MANY");
				/* Domain has to many elements. Do calculation for this part*/
				partStartT = t;

				/* Continue with this part of domain */

				time.now("DOMAIN PART LIMIT: " + elementCountPart);
				time.now("partStartT: " + partStartT);

				/* stop creating domain and calculate fractal with this part */
				break;
			}
		}
		if (wrapDomain) {
			/* domain optimization fixes */
			int reWrapped = 0;
			for (int tt = 0; tt < Application.RESOLUTION_DOMAIN_T; tt++) {
				for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_X; xx++) {
					/* Go through domain and queue elements to be filtered by GPU */
					elementZero = elementsScreen[tt][xx];
					if (elementZero != null && elementZero.isActiveRecalculate()) {
						// don't set any state, will be set again properly by calculation
						elementZero.setMarked();
						areaDomain.wrap(elementZero, wrapping);
						// elementZero.setColor(FColor.MARK2);
						for (Element el : wrapping) {
							reWrapped++;
							domainPart.add(el);
						}
					}
				}
			}
			time.red(">reWrapped " + reWrapped);
		}

		time.now("notWrappedCount " + notWrappedCount);
		time.now("wrappedCount " + wrappedCount);

		// time.now(". GPU go");
		// GPU.filter(domainToFilter, cd);
		// time.now(". GPU go done");

		if (Main.RESOLUTION_MULTIPLIER == 1) {
			/* finished and there was NO wrapping */
			time.red("Domain EMPTY: No multiplier");
			domainNotFinished = false;
		} else if (wrapDomain && t == Application.RESOLUTION_DOMAIN_T) {
			/* finished and there was wrapping */
			time.red("Domain EMPTY: Multiplication finished");
			domainNotFinished = false;
		} else {
			// time.red("-");
			// time.red("Venture.RESOLUTION_MULTIPLIER " + Venture.RESOLUTION_MULTIPLIER);
			// time.red("wrapDomain " + wrapDomain);
			// time.red("t " + t);
			// time.red("Application.RESOLUTION_DOMAIN_T " + Application.RESOLUTION_DOMAIN_T);
			// time.red("-");
			time.red("Domain still not empty");
		}

		// TODO ? wrapDomain = true;
		time.now("WRAP DOMAIN " + wrapDomain);
		return domainPart;
		//}
	}

	private void dropBestMatchToEmptyNeighbour(HH hh, int t, int x, LinkedList<Element> conflictsOnPixel) {
		bestMatch = null;

		dist = 0;
		double distMin = 42;
		for (Element element : conflictsOnPixel) {
			/* up */
			distMin = tryBestMatch(hh, t - 1, x + 1, element, distMin);
			distMin = tryBestMatch(hh, t, x + 1, element, distMin);
			distMin = tryBestMatch(hh, t + 1, x + 1, element, distMin);
			/* left */
			distMin = tryBestMatch(hh, t - 1, x, element, distMin);
			/* center is already filled by bestMatch */
			/* right */
			distMin = tryBestMatch(hh, t + 1, x, element, distMin);
			/* bottom */
			distMin = tryBestMatch(hh, t - 1, x - 1, element, distMin);
			distMin = tryBestMatch(hh, t, x - 1, element, distMin);
			distMin = tryBestMatch(hh, t + 1, x - 1, element, distMin);
		}
		if (bestMatch != null) {
			conflictsOnPixel.remove(bestMatch);
			elementsScreen[bestMatchAtT][bestMatchAtX] = bestMatch;
			conflictsResolved++;
			// } else {
			// elements[xx][yy].setColor(Color.RED);
		}
	}

	double tryBestMatch(HH hh, int t, int x, Element element, double distMin) {
		if (emptyAt(t, x)) {
			areaDomain.screenToDomainCarry(hh, t, x);
			dist = dist(hh.calculation.reT, hh.calculation.imX, element.originReT, element.originImX);
			if (dist < distMin) {
				distMin = dist;
				bestMatch = element;
				bestMatchAtT = t;
				bestMatchAtX = x;
			}
		}
		return distMin;
	}

	private boolean emptyAt(int t, int x) {
		return valueAt(t, x) != null;
	}

	public Integer valueAt(int t, int x) {
		if (t < 0 || x < 0 || t >= Application.RESOLUTION_DOMAIN_T || x >= Application.RESOLUTION_DOMAIN_X) {
			return null;
		}
		Element el = elementsScreen[t][x];
		if (el != null) {
			return el.getValue();
		}
		return null;
	}


	private Element bestMatch(HH hh, int t, int x, LinkedList<Element> conflictsOnPixel) {
		double distMin = 42;
		Element ret = null;
		for (Element el : conflictsOnPixel) {
			areaDomain.screenToDomainCarry(hh, t, x);
			dist = dist(hh.calculation.reT, hh.calculation.imX, el.originReT, el.originImX);
			if (dist < distMin) {
				distMin = dist;
				ret = el;
			}
		}
		conflictsOnPixel.remove(ret);
		return ret;
	}

	/**
	 * This is not distance. It is quadrance
	 */
	double dist(double hhReT, double hhImX, double hReT, double hImX) {
		return (hhReT - hReT) * (hhReT - hReT) + (hhImX - hImX) * (hhImX - hImX);
	}


	// TODO improve for multiple points per pixel
	public void createMask(BufferedImage maskImage) {
		if (maskDone) {
			maskDone = false;
			Element element;
			Color color;

			for (int t = 0; t < Application.RESOLUTION_DOMAIN_T; t++) {
				for (int x = 0; x < Application.RESOLUTION_DOMAIN_X; x++) {
					element = elementsScreen[t][x];
					if (element != null && element.getColor() != null) {
						color = element.getColor();
					} else {
						if (element != null) {
							switch (element.getState()) {
								case ActiveMoved:
									color = ScreenColor._MOVED;
									break;
								case ActiveNew:
									color = ScreenColor._ACTIVE_NEW;
									break;
								case HibernatedBlack:
									color = ScreenColor._HIBERNATED_BLACK;
									break;
								case HibernatedBlackNeighbour:
									color = ScreenColor._HIBERNATED_BLACK_NEIGHBOR;
									break;
								case HibernatedFinished:
									color = ScreenColor._FINISHED_OUTSIDE;
									break;
								case HibernatedFinishedInside:
									color = ScreenColor._FINISHED_INSIDE;
									break;
								case ActiveFixed:
									color = ScreenColor._ACTIVE_FIXED;
									break;
								default:
									color = ScreenColor._ERROR;
									break;
							}
						} else {
							color = ScreenColor._NULL;
						}
					}
					maskImage.setRGB(t, x, color.getRGB());
				}
			}
			maskDone = true;
		} else {
			time.now("mask don't");
		}
	}
	
	/*-----------------------------------------------------------------------------*/


	/*
	 * This is called already after zoom
	 */
	public void domainForThisZoom() {

		
		
        /*
		 * Scan area elements (old positions from previous calculation) to be -
         * moved to new positions (remembered) or skipped calculation for them.
         */
		int value;
		int valuesRemembered = 0;
		int newHibernatedBlack = 0;
		long allValues = 0;
		Element element;
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				element = elementsScreen[xx][yy];
				if (areaDomain.contains(element.originReT, element.originImX)) {
					/* Move elements to new coordinates */
					if (element.isHibernatedBlack_Neighbour()) {
						element.setHibernatedBlack();
					} else if (!element.isHibernatedBlack()
							&& !element.isHibernatedBlack_Neighbour()
							&& !element.isFixed()
							&& FractalMachine.isVeryDeepBlack(xx, yy, elementsScreen)) {
						element.setHibernatedBlack();
						newHibernatedBlack++;
					} else {
						if (element.isActiveNew()) {
							element.setActiveMoved();
						}
					}
					elementsToRemember.add(element);
					value = element.getValue();
					if (value != 0) {
						allValues += value;
						valuesRemembered++;
					}
				}
				/* clear color set for debug */
				element.setColor(null);
			}
		}


        /*
		 * Delete all elements assigned to screen coordinates.
         * Some are remembered and will be moved.
         */
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				elementsScreen[xx][yy] = null;
			}
		}

		/*
		 * Add remembered elements to their new position for new calculation
         */
		int conflictsFound = 0;
		Integer newPositionT;
		Integer newPositionX;
		Element done;

		int addedToNewPositions = 0;

		HH hh = new HH();

		LinkedList<Element>[][] conflicts = new LinkedList[Application.RESOLUTION_DOMAIN_T][Application.RESOLUTION_DOMAIN_X];
		for (Element el : elementsToRemember) {


			areaDomain.domainToScreenCarry(hh, el.originReT, el.originImX);

			newPositionT = hh.calculation.pxT;
			newPositionX = hh.calculation.pxX;

			if (newPositionX != HH.NOT && newPositionT != HH.NOT) {
				done = elementsScreen[newPositionT][newPositionX];
				if (done != null) {
					/* Conflict */
					if (conflicts[newPositionT][newPositionX] == null) {
						conflicts[newPositionT][newPositionX] = new LinkedList<>();
					}
					conflictsFound++;
					// el.setColor(Color.BLACK);
					conflicts[newPositionT][newPositionX].add(el);
				} else {
					/* OK; no conflict */
					elementsScreen[newPositionT][newPositionX] = el;
					addedToNewPositions++;
				}
			}
		}
		conflictsResolved = 0;
		LinkedList conflictsOnPixel;
		/* Resolve found conflicts */
		/* More Elements hit same pixel after zoom */
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				conflictsOnPixel = conflicts[xx][yy];
				if (conflictsOnPixel != null) {
					
					/* Add the initial conflict */
					element = elementsScreen[xx][yy];
					conflictsOnPixel.add(element);
					
					/* Find best match for the pixel with conflicts */
					elementsScreen[xx][yy] = bestMatch(hh, xx, yy, conflictsOnPixel);
					
					/* Find best match for pixels around */
					if (!conflictsOnPixel.isEmpty()) {
						dropBestMatchToEmptyNeighbour(hh, xx, yy, conflictsOnPixel);
					}
					if (!conflictsOnPixel.isEmpty()) {
						dropBestMatchToEmptyNeighbour(hh, xx, yy, conflictsOnPixel);
					}
					if (!conflictsOnPixel.isEmpty()) {
						dropBestMatchToEmptyNeighbour(hh, xx, yy, conflictsOnPixel);
					}
					conflicts[xx][yy] = null;
				}
			}
		}

        /*
		 * Create new elements on positions where nothing was moved to
         */
		int createdNewElements = 0;
		Element newElement;
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				if (elementsScreen[xx][yy] == null) {
					areaDomain.screenToDomainCarry(hh, xx, yy);
					newElement = new Element(hh.calculation.reT, hh.calculation.imX);
					// newElement.setColor(Color.CYAN);
					elementsScreen[xx][yy] = newElement;
					createdNewElements++;
				}
			}
		}
		
        /*
		 * Calculation for some positions should be skipped as they are to far away form any divergent position. (They are deep black)
         * Skipp also calculation for their neighbours. (Black neighbour)
         * Try to guess value of new elements if all values around them are the very similar.
         */
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				element = elementsScreen[xx][yy];
				if (element.isHibernatedBlack()) {
					final int r = 3;
					for (int x = -r; x < r; x++) {
						for (int y = -r; y < r; y++) {
							/* Set black neighbours in circle around deep black position */
							if ((x * x) + (y * y) < (r * r)) {
								FractalMachine.setAsDeepBlack(xx + x, yy - y, elementsScreen);
							}
						}
					}
				}
			}
		}

		// Time.now(".Guessed count:   " + guessedCount);
		elementsToRemember.clear();
	}

	public void resetOptimizationSoft() {
		Element element;
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				element = elementsScreen[xx][yy];
				if (element.isHibernatedBlack() || element.isHibernatedBlack_Neighbour()) {
					element.resetForOptimization();
				}
			}
		}
	}

	public void resetOptimizationHard() {

		Element element;
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				element = elementsScreen[xx][yy];
				if (!element.isActiveMoved() && !element.isHibernatedFinished() && !element.isHibernatedFinishedInside()) {
					element.resetAsNew();
				}
			}
		}
	}

	public boolean isOptimizationBreakAndFix() {
		/* Last tested pixel is Hibernated as Converged (Calculation finished) */
		Bool lastIsWhite = new Bool();
		/* Last tested pixel is Hibernated as Skipped for calculation (Deep black) */
		Bool lastIsBlack = new Bool();
		ArrayList<Integer> failedNumbersRe = new ArrayList<>();
		ArrayList<Integer> failedNumbersIm = new ArrayList<>();
		/* Test lines left and right */
		for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
			for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
				FractalMachine.testOptimizationBreakElement(xx, yy, elementsScreen[xx][yy], failedNumbersRe, failedNumbersIm, lastIsWhite, lastIsBlack);
			}
			lastIsBlack.setFalse();
			lastIsWhite.setFalse();
		}
		/* Test lines up and down */
		for (int xx = 0; xx < Application.RESOLUTION_DOMAIN_T; xx++) {
			for (int yy = 0; yy < Application.RESOLUTION_DOMAIN_X; yy++) {
				FractalMachine.testOptimizationBreakElement(xx, yy, elementsScreen[xx][yy], failedNumbersRe, failedNumbersIm, lastIsWhite, lastIsBlack);
			}
			lastIsBlack.setFalse();
			lastIsWhite.setFalse();
		}
		/* Fix failed positions */
		/* In worst case failed positions contains same position twice */
		int size = failedNumbersRe.size();
		for (int i = 0; i < size; i++) {
			// Time.now("FIXING: " + position.x + ". " + position.y);
			final int r = Application.TEST_OPTIMIZATION_FIX_SIZE;
			for (int x = -r; x < r; x++) {
				for (int y = -r; y < r; y++) {
					if ((x * x) + (y * y) < (r * r)) {
						// These thing should be much optimized to not do same for points it was already done
						FractalMachine.setActiveMovedIfBlack(failedNumbersRe.get(i) + x, failedNumbersIm.get(i) + y, elementsScreen);
					}
				}
			}
		}
		return !failedNumbersRe.isEmpty();
	}

}
