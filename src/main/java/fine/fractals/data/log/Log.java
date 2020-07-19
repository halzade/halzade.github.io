package fine.fractals.data.log;

import fine.fractals.Application;
import fine.fractals.Main;
import fine.fractals.fractal.Fractal;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Log {

	private static Logger logger = Logger.getLogger("FractalLogger");

	static {
		try {
			FileHandler fileHandler = new FileHandler(Main.FILE_PATH + "/log/" + Fractal.NAME + "_" + Application.APP_NAME + ".log");
			fileHandler.setFormatter(new LogFormatter());
			logger.addHandler(fileHandler);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void l(String s) {
		logger.info(s);
	}

	public static void severe(Throwable t) {
		logger.severe("ERROR: " + t.getMessage());
		for (StackTraceElement ste : t.getStackTrace()) {
			logger.severe("ERROR: " + ste.getLineNumber() + " - " + ste.getClassName() + "." + ste.getMethodName());
		}
	}

}
