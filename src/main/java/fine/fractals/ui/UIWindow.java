package fine.fractals.ui;

import fine.fractals.Application;
import fine.fractals.ApplicationWindow;
import fine.fractals.FractalWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UIWindow extends JComponent {

	private boolean active = false;

	protected OneTarget target;
	protected UIMouseMotionAdapter motionAdapter;
	protected String name;

	protected final int resolutionT;
	protected final int resolutionX;

	public UIWindow(OneTarget target, String name, int resolutionT, int resolutionX) {
		this.target = target;
		this.name = name;

		this.resolutionT = resolutionT;
		this.resolutionX = resolutionX;
	}

	protected void hideDefaultCursor(JFrame frame) {
		// Transparent 16 imX 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
	}

	protected void drawMouseCursor(Graphics g) {
		int sizeT = (int) (resolutionT * Application.ZOOM);
		int sizeX = (int) (resolutionX * Application.ZOOM);
		int halfT = sizeT / 2;
		int halfX = sizeX / 2;
		if (active) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}

		if (Application.ME.getDrawRectangle()) {
			g.drawRect(target.getScreenFromCornerT() - halfX, target.getScreenFromCornerX() - halfX, sizeT, sizeX);
		}
		int line = 3;
		g.drawLine(target.getScreenFromCornerT() + line, target.getScreenFromCornerX(), target.getScreenFromCornerT() - line, target.getScreenFromCornerX());
		g.drawLine(target.getScreenFromCornerT(), target.getScreenFromCornerX() + line, target.getScreenFromCornerT(), target.getScreenFromCornerX() - line);
	}

	public void setApplicationWindow(ApplicationWindow otherApplicationWindow) {
		this.motionAdapter.setApplicationWindow(otherApplicationWindow);
	}

	public void setDesignWindow(FractalWindow otherFractalWindow) {
		this.motionAdapter.setDesignWindow(otherFractalWindow);
	}

	public void activate() {
		this.active = true;
	}

	public void deactivate() {
		this.active = false;
	}

}