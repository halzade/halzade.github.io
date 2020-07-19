//package com.pieceofinfinity.fractal.engine;
//
//import Time;
//import Q;
//import fine.fractals.math.quick.C;
//import org.apache.commons.math3.dfp.Dfp;
//
//public class TestKIM {
//
//	public TestKIM() {
//
//		// Xn+1 = Xn^2 + X0
//		// Yn+1 = Yn^2 + Y0
//		// Dn   = Yn - Xn
//		// D0   = Y0- X0
//
//		// (1) Dn+1 = 2Xn* Dn + Dn^2 + D0
//		//     D1   = 2X0* D0 + D0^2 + D0
//		//     D2   = 2X1* D1 + D1^2 + D0
//		//     D3   = 2X2* D2 + D2^2 + D0
//		//     D4   = 2X3* D3 + D3^2 + D0
//
//		// X (2) Dn   = An * D0  + Bn * D0^2 + Cn * D0^3+ o(D0^4)
//		// X (3) An+1 = 2Xn * An + 1
//		// X (4) Bn+1 = 2Xn * Bn + An^2
//		// X (5) Cn+1 = 2Xn * Cn + 2An * Bn
//	}
//
//	/* Test 11 iteration */
//	/* private static final double originRe = -0.29333333333334;
//	private static final double originIm = 0.67999999999999;
//	private static final double D = 0.000001;
//	private static final int INIT_LENGTH = 11;
//	*/
//
//	private static final Dfp originRe = Q.dfp(-0.74988961259278);
//	private static final Dfp originIm = Q.dfp(0.01274681889462);
//	private static final Dfp D = Q.dfp(0.000000000003);
//	private static final int INIT_LENGTH = 7888 + 1;
//
//
//	private static final Dfp originReNew = originRe.add(D);
//	private static final Dfp originImNew = originIm.add(D);
//
//
//	@SuppressWarnings("SuspiciousNameCombination")
//	public static void main(String... args) {
//
//		/* -------- Calculate Known Element ------------------------------------------------------------------------ */
//		Dfp x = originRe;
//		Dfp y = originIm;
//		int iterator = 0;
//		Dfp x2;
//		Dfp y2;
//
//		x2 = Q.sq(x);
//		y2 = Q.sq(y);
//		Dfp[] pathRe = new Dfp[INIT_LENGTH];
//		Dfp[] pathIm = new Dfp[INIT_LENGTH];
//
//		while (x2.add(y2).lessThan(Q.FOUR)) {
//			y = Q.sq(x.add(y)).subtract(x2).subtract(y2);
//			y = y.add(originIm);
//			x = x2.subtract(y2).add(originRe);
//			x2 = Q.sq(x);
//			y2 = Q.sq(y);
//
//			Time.m(iterator + " " + x + " " + y);
//			pathRe[iterator] = x;
//			pathIm[iterator] = y;
//
//			iterator++;
//		}
//
//		/* do it one more time */
//		y = Q.sq(x.add(y)).subtract(x2).subtract(y2);
//		y = y.add(originIm);
//		x = x2.subtract(y2).add(originRe);
//		Time.m(iterator + " " + x + " " + y);
//		pathRe[iterator] = x;
//		pathIm[iterator] = y;
//
//
//
//
//
//
//		/* -------- Calculate New Element for verification --------------------------------------------------------- */
//		Time.m("- 0 -----------------------------");
//
//		x = originReNew;
//		y = originImNew;
//		int iteratorNew = 0;
//		x2 = Q.sq(x);
//		y2 = Q.sq(y);
//		Dfp[] pathReNew = new Dfp[INIT_LENGTH];
//		Dfp[] pathImNew = new Dfp[INIT_LENGTH];
//
//		while (x2.add(y2).lessThan(Q.FOUR)) {
//			y = Q.sq(x.add(y)).subtract(x2).subtract(y2);
//			y = y.add(originIm);
//			x = x2.subtract(y2).add(originRe);
//			x2 = Q.sq(x);
//			y2 = Q.sq(y);
//
//			Time.m(iteratorNew + " " + x + " " + y);
//			pathReNew[iteratorNew] = x;
//			pathImNew[iteratorNew] = y;
//
//			iteratorNew++;
//		}
//
//		/* -------- Calculate New Element (Differences) with New method -------------------------------------------- */
//		Time.m("- 1 -----------------------------");
//		// D0 = Y0- X0
//		double d0re = originReNew.subtract(originRe).toDouble();
//		double d0im = originImNew.subtract(originIm).toDouble();
//		double[] dRe = new double[INIT_LENGTH];
//		double[] dIm = new double[INIT_LENGTH];
//		dRe[0] = d0re;
//		dIm[0] = d0im;
//		double dnRe;
//		double dnIm;
//		for (int i = 0; i < 10; i++) {
//			// D1 = 2X0* D0 + D0^2 + D0
//			// D2 = 2X1* D1 + D1^2 + D0
//			// D3 = 2X2* D2 + D2^2 + D0
//			// D4 = 2X3* D3 + D3^2 + D0
//			dnRe = dRe[i];
//			dnIm = dIm[i];
//			dRe[i + 1] = 2 * pathRe[i].toDouble() * dnRe + dnRe * dnRe + d0re;
//			dIm[i + 1] = 2 * pathIm[i].toDouble() * dnIm + dnIm * dnIm + d0im;
//		}
//
//		/* -------- Verify calculation ----------------------------------------------------------------------------- */
//		double ynRe;
//		double ynIm;
//		int counter = 0;
//		for (int i = 0; i < iteratorNew; i++) {
//			// Yn =  Xn + Dn
//			ynRe = pathRe[i].toDouble() + dRe[i];
//			ynIm = pathIm[i].toDouble() + dIm[i];
//			Time.m(i + " " + ynRe + " \reT" + C.roundString(ynRe - pathReNew[i].toDouble()));
//			Time.m(i + " " + ynIm + " \reT" + C.roundString(ynIm - pathImNew[i].toDouble()));
//			counter++;
//		}
//
//
//		Time.m("(Iterator:    " + iterator + ")");
//		Time.m(" iteratorNew: " + iteratorNew);
//		Time.m(" Counter:     " + counter);
//	}
//
//}
