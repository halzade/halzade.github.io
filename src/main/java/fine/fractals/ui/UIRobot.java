package fine.fractals.ui;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UIRobot {

	private UIRobot() {
	}

	public static void moveMouseBy(int x, int y) {
		try {
			Point pNow = MouseInfo.getPointerInfo().getLocation();
			Point pMoved = new Point(pNow.x + x, pNow.y + y);
			new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]).mouseMove(pMoved.x, pMoved.y);
		} catch (AWTException ex) {
			Logger.getLogger(UIRobot.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
