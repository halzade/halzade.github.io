package fine.fractals.math;

import fine.fractals.Main;
import fine.fractals.Time;
import fine.fractals.data.Screen;
import org.junit.Assert;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class ColoredValueIntegral {

	private static Time time = new Time(ColoredValueIntegral.class);

	private final HashMap<Integer, Pivot> valueIntegralWithColorLimitsR;
	private final HashMap<Integer, Pivot> valueIntegralWithColorLimitsG;
	private final HashMap<Integer, Pivot> valueIntegralWithColorLimitsB;

	private LinkedList<Integer> designValuesR;
	private LinkedList<Integer> designValuesG;
	private LinkedList<Integer> designValuesB;

	private Integer currentlyColoredPivotValueR = null;
	private Integer currentlyColoredPivotValueG = null;
	private Integer currentlyColoredPivotValueB = null;

	private int leftR;
	private int leftG;
	private int leftB;
	private Integer fullColoringR;
	private Integer fullColoringG;
	private Integer fullColoringB;

	public void setFullColoringR(int fullColoringR) {
		this.fullColoringR = fullColoringR;
	}

	public void setFullColoringG(int fullColoringG) {
		this.fullColoringG = fullColoringG;
	}

	public void setFullColoringB(int fullColoringB) {
		this.fullColoringB = fullColoringB;
	}

	public void finalize(HashMap<Integer, Color> finalColorsR, HashMap<Integer, Color> finalColorsG, HashMap<Integer, Color> finalColorsB) {
		ArrayList<Integer> valuesR = new ArrayList(this.valueIntegralWithColorLimitsR.keySet());
		ArrayList<Integer> valuesG = new ArrayList(this.valueIntegralWithColorLimitsG.keySet());
		ArrayList<Integer> valuesB = new ArrayList(this.valueIntegralWithColorLimitsB.keySet());

		for (Integer val : valuesR) {
			Pivot p = this.valueIntegralWithColorLimitsR.get(val);

			double r = 0;
			double g = 0;
			double b = 0;
			double cAmount = 0;

			Color colorUsed;
			for (Coloring c : p.appliedColors) {
				colorUsed = Main.colorPalette.getSpectrumValueR(c.colorIndex);
				r += c.amount * colorUsed.getRed();
				g += c.amount * colorUsed.getGreen();
				b += c.amount * colorUsed.getBlue();
				cAmount += c.amount;
			}
			finalColorsR.put(val, new Color((int) (r / cAmount), (int) (g / cAmount), (int) (b / cAmount)));
		}
		for (Integer val : valuesG) {
			Pivot p = this.valueIntegralWithColorLimitsG.get(val);

			double r = 0;
			double g = 0;
			double b = 0;
			double cAmount = 0;

			Color colorUsed;
			for (Coloring c : p.appliedColors) {
				colorUsed = Main.colorPalette.getSpectrumValueG(c.colorIndex);
				r += c.amount * colorUsed.getRed();
				g += c.amount * colorUsed.getGreen();
				b += c.amount * colorUsed.getBlue();
				cAmount += c.amount;
			}
			finalColorsG.put(val, new Color((int) (r / cAmount), (int) (g / cAmount), (int) (b / cAmount)));
		}
		for (Integer val : valuesB) {
			Pivot p = this.valueIntegralWithColorLimitsB.get(val);

			double r = 0;
			double g = 0;
			double b = 0;
			double cAmount = 0;

			Color colorUsed;
			for (Coloring c : p.appliedColors) {
				colorUsed = Main.colorPalette.getSpectrumValueB(c.colorIndex);
				r += c.amount * colorUsed.getRed();
				g += c.amount * colorUsed.getGreen();
				b += c.amount * colorUsed.getBlue();
				cAmount += c.amount;
			}
			finalColorsB.put(val, new Color((int) (r / cAmount), (int) (g / cAmount), (int) (b / cAmount)));
		}
	}

	private class Coloring {
		public int amount;

		public int colorIndex;

		public Coloring(int amount, int colorIndex) {
			this.amount = amount;
			this.colorIndex = colorIndex;
		}

	}

	private class Pivot {

		public int designValue;
		public int valuePixelCount;
		public ArrayList<Coloring> appliedColors = new ArrayList<>();

		public int needsToBePainted;

		public Pivot(int designValue, int valueAmount) {
			this.designValue = designValue;
			this.valuePixelCount = valueAmount;
			this.needsToBePainted = this.valuePixelCount;
		}

		public void increase() {
			this.valuePixelCount++;
			this.needsToBePainted = this.valuePixelCount;
		}

		public void paintMe(int colorIndex, int paintAmount) {
			this.appliedColors.add(new Coloring(paintAmount, colorIndex));
			this.needsToBePainted = this.needsToBePainted - paintAmount;
		}
	}

	/*
	 * Count how many of which value is there
	 */
	public ColoredValueIntegral(Screen screen) {

		this.valueIntegralWithColorLimitsR = new HashMap<>();
		this.valueIntegralWithColorLimitsG = new HashMap<>();
		this.valueIntegralWithColorLimitsB = new HashMap<>();

		// Initialize
		for (int x = 0; x < Main.RESOLUTION_IMAGE_X; x++) {
			for (int t = 0; t < Main.RESOLUTION_IMAGE_T; t++) {
				int designValueR = screen.red(t, x);
				if (valueIntegralWithColorLimitsR.containsKey(designValueR)) {
					valueIntegralWithColorLimitsR.get(designValueR).increase();
				} else {
					valueIntegralWithColorLimitsR.put(designValueR, new Pivot(designValueR, 1));
				}

				int designValueG = screen.green(t, x);
				if (valueIntegralWithColorLimitsG.containsKey(designValueG)) {
					valueIntegralWithColorLimitsG.get(designValueG).increase();
				} else {
					valueIntegralWithColorLimitsG.put(designValueG, new Pivot(designValueG, 1));
				}

				int designValueB = screen.blue(t, x);
				if (valueIntegralWithColorLimitsB.containsKey(designValueB)) {
					valueIntegralWithColorLimitsB.get(designValueB).increase();
				} else {
					valueIntegralWithColorLimitsB.put(designValueB, new Pivot(designValueB, 1));
				}
			}
		}

		this.designValuesR = new LinkedList<>(this.valueIntegralWithColorLimitsR.keySet());
		this.designValuesG = new LinkedList<>(this.valueIntegralWithColorLimitsG.keySet());
		this.designValuesB = new LinkedList<>(this.valueIntegralWithColorLimitsB.keySet());
		Collections.sort(designValuesR);
		Collections.sort(designValuesG);
		Collections.sort(designValuesB);

		System.out.println("R - All values to paint: " + designValuesR.size());
		System.out.println("R - smallest: " + designValuesR.get(0));
		System.out.println("R - largest:  " + designValuesR.get(designValuesR.size() - 1));
		System.out.println("R - All colors: " + Main.colorPalette.colorResolutionR());

		System.out.println("G - All values to paint: " + designValuesG.size());
		System.out.println("G - smallest: " + designValuesG.get(0));
		System.out.println("G - largest:  " + designValuesG.get(designValuesG.size() - 1));
		System.out.println("G - All colors: " + Main.colorPalette.colorResolutionG());

		System.out.println("B - All values to paint: " + designValuesB.size());
		System.out.println("B - smallest: " + designValuesB.get(0));
		System.out.println("B - largest:  " + designValuesB.get(designValuesB.size() - 1));
		System.out.println("B - All colors: " + Main.colorPalette.colorResolutionB());
	}


	private Integer getNextValueToPaintR() {
		if (designValuesR.isEmpty()) {
			return null;
		}
		final Integer ret = designValuesR.get(0);
		designValuesR.remove(0);
		return ret;
	}

	private Integer getNextValueToPaintG() {
		if (designValuesG.isEmpty()) {
			return null;
		}
		final Integer ret = designValuesG.get(0);
		designValuesG.remove(0);
		return ret;
	}

	private Integer getNextValueToPaintB() {
		if (designValuesB.isEmpty()) {
			return null;
		}
		final Integer ret = designValuesB.get(0);
		designValuesB.remove(0);
		return ret;
	}

	int usedPaintTotalR = 0;
	int usedPaintTotalG = 0;
	int usedPaintTotalB = 0;


	public int applyPaintR(int colorIndex, int paintAmount) {
		if (currentlyColoredPivotValueR == null) {
			currentlyColoredPivotValueR = getNextValueToPaintR();
		}
		// Image pixel amount couldn't be divided precisely into the number of colors without reminder left */
		if (leftR > 0) {
			paintAmount += leftR;
			leftR = 0;
		}

		final Pivot pivot = this.valueIntegralWithColorLimitsR.get(currentlyColoredPivotValueR);

		if (pivot == null) {
			verify();
		}

		final int colorLeft;
		if (pivot.needsToBePainted < paintAmount) {
			/*
				Pivot coloring will be finished
				This color will be left to color next pivot
			 */

			int exactPaintAmount = pivot.needsToBePainted;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalR += exactPaintAmount;

			colorLeft = paintAmount - exactPaintAmount;

			// switch to next pivot
			currentlyColoredPivotValueR = getNextValueToPaintR();
		} else if (pivot.needsToBePainted > paintAmount) {
			/*
				Pivot will be colored only partially.
				No paint with this color will be left
			*/

			int exactPaintAmount = paintAmount;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalR += exactPaintAmount;

			colorLeft = 0;
		} else {
			/*
				Pivot will be colored exactly.
				No paint with this color will be left
			*/
			int exactPaintAmount = paintAmount;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalR += exactPaintAmount;

			colorLeft = 0;

			currentlyColoredPivotValueR = getNextValueToPaintR();
		}

		return colorLeft;
	}

	public int applyPaintG(int colorIndex, int paintAmount) {
		if (currentlyColoredPivotValueG == null) {
			currentlyColoredPivotValueG = getNextValueToPaintG();
		}
		// Image pixel amount couldn't be divided precisely into the number of colors without reminder left */
		if (leftG > 0) {
			paintAmount += leftG;
			leftG = 0;
		}

		final Pivot pivot = this.valueIntegralWithColorLimitsG.get(currentlyColoredPivotValueG);

		if (pivot == null) {
			verify();
		}

		final int colorLeft;
		if (pivot.needsToBePainted < paintAmount) {
			/*
				Pivot coloring will be finished
				This color will be left to color next pivot
			 */

			int exactPaintAmount = pivot.needsToBePainted;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalG += exactPaintAmount;

			colorLeft = paintAmount - exactPaintAmount;

			// switch to next pivot
			currentlyColoredPivotValueG = getNextValueToPaintG();
		} else if (pivot.needsToBePainted > paintAmount) {
			/*
				Pivot will be colored only partially.
				No paint with this color will be left
			*/

			int exactPaintAmount = paintAmount;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalG += exactPaintAmount;

			colorLeft = 0;
		} else {
			/*
				Pivot will be colored exactly.
				No paint with this color will be left
			*/
			int exactPaintAmount = paintAmount;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalG += exactPaintAmount;

			colorLeft = 0;

			currentlyColoredPivotValueG = getNextValueToPaintG();
		}

		return colorLeft;
	}

	public int applyPaintB(int colorIndex, int paintAmount) {
		if (currentlyColoredPivotValueB == null) {
			currentlyColoredPivotValueB = getNextValueToPaintB();
		}
		// Image pixel amount couldn't be divided precisely into the number of colors without reminder left */
		if (leftB > 0) {
			paintAmount += leftB;
			leftB = 0;
		}

		final Pivot pivot = this.valueIntegralWithColorLimitsB.get(currentlyColoredPivotValueB);

		if (pivot == null) {
			verify();
		}

		final int colorLeft;
		if (pivot.needsToBePainted < paintAmount) {
			/*
				Pivot coloring will be finished
				This color will be left to color next pivot
			 */

			int exactPaintAmount = pivot.needsToBePainted;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalB += exactPaintAmount;

			colorLeft = paintAmount - exactPaintAmount;

			// switch to next pivot
			currentlyColoredPivotValueB = getNextValueToPaintB();
		} else if (pivot.needsToBePainted > paintAmount) {
			/*
				Pivot will be colored only partially.
				No paint with this color will be left
			*/

			int exactPaintAmount = paintAmount;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalB += exactPaintAmount;

			colorLeft = 0;
		} else {
			/*
				Pivot will be colored exactly.
				No paint with this color will be left
			*/
			int exactPaintAmount = paintAmount;
			pivot.paintMe(colorIndex, exactPaintAmount);

			usedPaintTotalB += exactPaintAmount;

			colorLeft = 0;

			currentlyColoredPivotValueB = getNextValueToPaintB();
		}

		return colorLeft;
	}

	private void verify() {
		/* ========================================== */

		System.out.println("------------------------------------------");
		ArrayList<Integer> valuesR = new ArrayList(this.valueIntegralWithColorLimitsR.keySet());
		ArrayList<Integer> valuesG = new ArrayList(this.valueIntegralWithColorLimitsG.keySet());
		ArrayList<Integer> valuesB = new ArrayList(this.valueIntegralWithColorLimitsB.keySet());
		Collections.sort(valuesR);
		Collections.sort(valuesG);
		Collections.sort(valuesB);

		HashMap<Integer, Integer> paintUsedR = new HashMap<>();
		HashMap<Integer, Integer> paintUsedG = new HashMap<>();
		HashMap<Integer, Integer> paintUsedB = new HashMap<>();

		for (Integer val : valuesR) {
			Pivot p = this.valueIntegralWithColorLimitsR.get(val);
			int paintedBy = 0;
			for (Coloring c : p.appliedColors) {
				paintedBy += c.amount;
				if (paintUsedR.containsKey(c.colorIndex)) {
					int amt = paintUsedR.get(c.colorIndex);
					paintUsedR.put(c.colorIndex, c.amount + amt);
				} else {
					paintUsedR.put(c.colorIndex, c.amount);
				}
			}
			System.out.println(p.designValue + "(" + p.valuePixelCount + " = " + paintedBy + "), 0=" + p.needsToBePainted + ", " + p.appliedColors.size() + "{" + usedColors(p.appliedColors) + "}" + usedPaint(p.appliedColors));
			Assert.assertEquals(p.valuePixelCount, paintedBy);
			Assert.assertEquals(0, p.needsToBePainted);
		}
		for (int ind = 0; ind < paintUsedR.size(); ind++) {
			System.out.println("colorR: " + ind + ":" + paintUsedR.get(ind));
			Assert.assertEquals(paintUsedR.get(ind), fullColoringR);
		}
		Assert.assertEquals(paintUsedR.size(), Main.colorPalette.colorResolutionR());
		System.out.println("------------------------------------------");


		for (Integer val : valuesG) {
			Pivot p = this.valueIntegralWithColorLimitsG.get(val);
			int paintedBy = 0;
			for (Coloring c : p.appliedColors) {
				paintedBy += c.amount;
				if (paintUsedG.containsKey(c.colorIndex)) {
					int amt = paintUsedG.get(c.colorIndex);
					paintUsedG.put(c.colorIndex, c.amount + amt);
				} else {
					paintUsedG.put(c.colorIndex, c.amount);
				}
			}
			System.out.println(p.designValue + "(" + p.valuePixelCount + " = " + paintedBy + "), 0=" + p.needsToBePainted + ", " + p.appliedColors.size() + "{" + usedColors(p.appliedColors) + "}" + usedPaint(p.appliedColors));
			Assert.assertEquals(p.valuePixelCount, paintedBy);
			Assert.assertEquals(0, p.needsToBePainted);
		}
		for (int ind = 0; ind < paintUsedG.size(); ind++) {
			System.out.println("colorG: " + ind + ":" + paintUsedG.get(ind));
			Assert.assertEquals(paintUsedG.get(ind), fullColoringG);
		}
		Assert.assertEquals(paintUsedG.size(), Main.colorPalette.colorResolutionG());
		System.out.println("------------------------------------------");


		for (Integer val : valuesB) {
			Pivot p = this.valueIntegralWithColorLimitsB.get(val);
			int paintedBy = 0;
			for (Coloring c : p.appliedColors) {
				paintedBy += c.amount;
				if (paintUsedB.containsKey(c.colorIndex)) {
					int amt = paintUsedB.get(c.colorIndex);
					paintUsedB.put(c.colorIndex, c.amount + amt);
				} else {
					paintUsedB.put(c.colorIndex, c.amount);
				}
			}
			System.out.println(p.designValue + "(" + p.valuePixelCount + " = " + paintedBy + "), 0=" + p.needsToBePainted + ", " + p.appliedColors.size() + "{" + usedColors(p.appliedColors) + "}" + usedPaint(p.appliedColors));
			Assert.assertEquals(p.valuePixelCount, paintedBy);
			Assert.assertEquals(0, p.needsToBePainted);
		}
		for (int ind = 0; ind < paintUsedB.size(); ind++) {
			System.out.println("color: " + ind + ":" + paintUsedB.get(ind));
			Assert.assertEquals(paintUsedB.get(ind), fullColoringB);
		}
		Assert.assertEquals(paintUsedB.size(), Main.colorPalette.colorResolutionB());
		System.out.println("------------------------------------------");
	}

	private String usedColors(ArrayList<Coloring> appliedColors) {
		String ret = "";
		for (Coloring c : appliedColors) {
			if (!"".equals(ret)) {
				ret += ",";
			}
			ret += c.colorIndex;
		}
		return ret;
	}

	private String usedPaint(ArrayList<Coloring> appliedColors) {
		String ret = "(";
		for (Coloring c : appliedColors) {
			if (!"(".equals(ret)) {
				ret += "-";
			}
			ret += c.amount;
		}
		return ret + ")";
	}

	public void setLeftR(int leftR) {
		this.leftR = leftR;
	}

	public void setLeftG(int leftG) {
		this.leftG = leftG;
	}

	public void setLeftB(int leftB) {
		this.leftB = leftB;
	}
}
