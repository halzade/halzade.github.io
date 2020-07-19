package fine.fractals.data;

import fine.fractals.Main;
import fine.fractals.math.common.HH;

public class Screen {

	private int[][] elementsStaticScreenRed = null;
	private int[][] elementsStaticScreenGreen = null;
	private int[][] elementsStaticScreenBlue = null;

	private int[] maxScrValue3;
	private int[] minScrValue3;

	public Screen() {
		this.elementsStaticScreenRed = new int[Main.RESOLUTION_IMAGE_T][Main.RESOLUTION_IMAGE_X];
		this.elementsStaticScreenGreen = new int[Main.RESOLUTION_IMAGE_T][Main.RESOLUTION_IMAGE_X];
		this.elementsStaticScreenBlue = new int[Main.RESOLUTION_IMAGE_T][Main.RESOLUTION_IMAGE_X];
	}

	public void initiate(int[][] r, int[][] g, int[][] b) {
		this.elementsStaticScreenRed = r;
		this.elementsStaticScreenGreen = g;
		this.elementsStaticScreenBlue = b;
	}


	public void updateMiniMax() {

		int[] minScrValue = new int[3];
		minScrValue[0] = Integer.MAX_VALUE;
		minScrValue[1] = Integer.MAX_VALUE;
		minScrValue[2] = Integer.MAX_VALUE;
		int[] maxScrValue = new int[3];

		for (int i = 0; i < Main.RESOLUTION_IMAGE_T; i++) {
			for (int j = 0; j < Main.RESOLUTION_IMAGE_X; j++) {
				int v1 = this.elementsStaticScreenRed[i][j];
				int v2 = this.elementsStaticScreenGreen[i][j];
				int v3 = this.elementsStaticScreenBlue[i][j];
				if (minScrValue[0] > v1) {
					minScrValue[0] = v1;
				}
				if (minScrValue[1] > v2) {
					minScrValue[1] = v2;
				}
				if (minScrValue[2] > v3) {
					minScrValue[2] = v3;
				}
				if (v1 > maxScrValue[0]) {
					maxScrValue[0] = v1;
				}
				if (v2 > maxScrValue[1]) {
					maxScrValue[1] = v2;
				}
				if (v3 > maxScrValue[2]) {
					maxScrValue[2] = v3;
				}
			}
		}

		this.maxScrValue3 = maxScrValue;
		this.minScrValue3 = minScrValue;
	}

	public void resetAsNew() {
		maxScrValue3 = null;
		minScrValue3 = null;
	}

	public int[] maxSrc3() {
		return this.maxScrValue3;
	}

	public int[] minSrc3() {
		return this.minScrValue3;
	}

	public void add(HH hh) {
		add(hh.calculation.pxT, hh.calculation.pxX, hh);
	}

	public void add(int pxT, int pxX, HH hh) {
		boolean ok = false;
		if (hh.calculation.r) {
			this.elementsStaticScreenRed[pxT][pxX] += 1;
			ok = true;
		}
		if (hh.calculation.g) {
			this.elementsStaticScreenGreen[pxT][pxX] += 1;
			ok = true;
		}
		if (hh.calculation.b) {
			this.elementsStaticScreenBlue[pxT][pxX] += 1;
			ok = true;
		}
		if (!ok) throw new RuntimeException("screen.add() should be ok");
	}

	public static int hhColorToInt(HH hh) {
		if (hh.calculation.r) {
			return 0;
		}
		if (hh.calculation.g) {
			return 1;
		}
		if (hh.calculation.b) {
			return 2;
		}
		throw new RuntimeException("screen.hhColorToInt() should be ok");
	}

	public static void intToHHColor(int color, HH hh) {
		if (color == 0) {
			hh.calculation.r = true;
			hh.calculation.g = false;
			hh.calculation.b = false;
			return;
		}
		if (color == 1) {
			hh.calculation.r = false;
			hh.calculation.g = true;
			hh.calculation.b = false;
			return;
		}
		if (color == 2) {
			hh.calculation.r = false;
			hh.calculation.g = false;
			hh.calculation.b = true;
			return;
		}
		throw new RuntimeException("screen.intToHHColor() should be ok");
	}

	public void clear() {
		for (int x = 0; x < Main.RESOLUTION_IMAGE_X; x++) {
			for (int t = 0; t < Main.RESOLUTION_IMAGE_T; t++) {
				elementsStaticScreenRed[t][x] = 0;
				elementsStaticScreenGreen[t][x] = 0;
				elementsStaticScreenBlue[t][x] = 0;
			}
		}
	}

	public int red(int t, int x) {
		return elementsStaticScreenRed[t][x];
	}

	public int green(int t, int x) {
		return elementsStaticScreenGreen[t][x];
	}

	public int blue(int t, int x) {
		return elementsStaticScreenBlue[t][x];
	}

	public int[][] getRedElements() {
		return elementsStaticScreenRed;
	}

	public int[][] getGreenElements() {
		return elementsStaticScreenGreen;
	}

	public int[][] getBlueElements() {
		return elementsStaticScreenBlue;
	}
}
