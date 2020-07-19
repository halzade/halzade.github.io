package fine.fractals.data;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.Main;
import fine.fractals.fractal.Fractal;
import fine.fractals.math.Design;
import fine.fractals.math.common.Element;

import java.io.*;
import java.util.ArrayList;

/**
 * Use only primitive types in files.
 */
public class FractalFiles {

	private static Time time = new Time(FractalFiles.class);

	private final static String u = "_";
	private final static String x = "x";

	// private static LinkedList<Double> thisDomainReT;
	// private static LinkedList<Double> thisDomainImX;

	// private static boolean newDomain = false;
	private static boolean newDesign = false;

	private static String fileDirectory() {
		return Main.FILE_PATH
				// Folder Name
				+ Fractal.NAME + "_"
				+ Application.VERSION + "_"
				+ Main.COLORING_METHOD + "/";
	}

	/**
	 * This stores data for Image. Not only for domain.
	 */
	private static String fileName() {
		return fileDirectory()
				// File Name
				// Resolution Domain
				+ "rD" + Application.RESOLUTION_DOMAIN_T + x
				+ Application.RESOLUTION_DOMAIN_X + u
				// Resolution Image
				+ "rI" + Main.RESOLUTION_IMAGE_T + x
				+ Main.RESOLUTION_IMAGE_X + u
				// Iteration Min and Max
				+ "it" + Fractal.ITERATION_MIN + "-"
				+ Fractal.ITERATION_MAX + u
				// Area Domain Size
				+ "aD" + Application.ME.areaDomain.sizeTString4() + x
				+ Application.ME.areaDomain.sizeImXString4() + u
				// Center of Area Domain
				+ "cD" + Application.ME.areaDomain.centerReT + x
				+ Application.ME.areaDomain.centerImX + u
				// Area Image Size
				+ "aI" + Application.ME.areaImage.sizeTString4() + x
				+ Application.ME.areaImage.sizeImXString4() + u
				// Center of Area Image
				+ "cI" + Application.ME.areaImage.centerReT + x
				+ Application.ME.areaImage.centerImX + u
				// Resolution multiplier
				+ "m" + Main.RESOLUTION_MULTIPLIER;
	}

	private static String fileNameDomain() {
		String name = fileName() + ".domain";
		time.red("DOMAIN FILE NAME: " + name);
		return name;
	}

	private static String fileNameDesign() {
		String name = fileName() + ".design";
		time.red("DOMAIN FILE NAME: " + name);
		return name;
	}

	public static void start() {
		if (Main.DO_FILES) {
			time.now("start");
			// newDomain = !existsDomain();
			newDesign = !existsDesign();
			// if (newDomain || newDesign) {
			if (newDesign) {
				createIfThereIsNoDirectory();
				// thisDomainReT = new LinkedList<>();
				// thisDomainImX = new LinkedList<>();
			} else {
				time.now("nothing");
			}
		}
	}

	public static void addNowDomainEl(double originReT, double originImX) {
		if (Main.DO_FILES) {
			//if (newDomain) {
			//	thisDomainReT.add(originReT);
			//	thisDomainImX.add(originImX);
			//}
		}
	}

	synchronized public static void finish() {
		if (Main.DO_FILES) {
			time.now("finish");
			try {
				// if (newDomain) {
				// 	ObjectOutputStream oosDomain = new ObjectOutputStream(new FileOutputStream(fileNameDomain()));
				// 	int size = thisDomainReT.size();
				//
				// 	double[] allReT = new double[size];
				// 	double[] allImX = new double[size];
				//
				// 	for (int i = 0; i < size; i++) {
				// 		allReT[i] = thisDomainReT.get(i);
				// 		allImX[i] = thisDomainImX.get(i);
				// 	}
				// 	oosDomain.writeObject(allReT);
				// 	oosDomain.writeObject(allImX);
				//
				// 	oosDomain.close();
				// }
				if (newDesign) {
					ObjectOutputStream oosDesign = new ObjectOutputStream(new FileOutputStream(fileNameDesign()));

					oosDesign.writeObject(Design.ME.screen.getRedElements());
					oosDesign.writeObject(Design.ME.screen.getGreenElements());
					oosDesign.writeObject(Design.ME.screen.getBlueElements());

					oosDesign.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @deprecated use initDesign() instead
	 */
	public static ArrayList<Element> getDomain() {
		if (!Main.DO_FILES) {
			throw new RuntimeException("FractalFiles are disabled");
		}
		time.now("getDomain");
		try {
			ObjectInputStream oisDomain = new ObjectInputStream(new FileInputStream(fileNameDomain()));

			double[] allReT = (double[]) oisDomain.readObject();
			double[] allImX = (double[]) oisDomain.readObject();
			int size = allReT.length;

			ArrayList<Element> domain = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				domain.add(new Element(allReT[i], allImX[i]));
			}

			oisDomain.close();

			return domain;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void initDesign() {
		if (!Main.DO_FILES) {
			throw new RuntimeException("FractalFiles are disabled");
		}
		time.now("getDomain");
		try {
			ObjectInputStream oisDesign = new ObjectInputStream(new FileInputStream(fileNameDesign()));
			int[][] redElements = (int[][]) oisDesign.readObject();
			int[][] greenElements = (int[][]) oisDesign.readObject();
			int[][] blueElements = (int[][]) oisDesign.readObject();
			Design.ME.screen.initiate(redElements, greenElements, blueElements);

			oisDesign.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean existsDomain() {
		File f = new File(fileNameDomain());
		return f.exists() && !f.isDirectory();
	}

	public static boolean existsDesign() {
		File f = new File(fileNameDesign());
		return f.exists() && !f.isDirectory();
	}

	private static void createIfThereIsNoDirectory() {
		File dir = new File(fileDirectory());
		if (!dir.exists()) {
			time.red("Create new Directory: " + dir.getName());
			dir.mkdir();
		}
	}

}
