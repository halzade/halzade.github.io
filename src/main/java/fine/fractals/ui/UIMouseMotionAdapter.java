package fine.fractals.ui;

import fine.fractals.Application;
import fine.fractals.ApplicationWindow;
import fine.fractals.FractalWindow;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class UIMouseMotionAdapter extends MouseMotionAdapter {

	private final OneTarget oneTarget;
	private final UIWindow meWindow;
	private ApplicationWindow otherApplicationWindow = null;
	private FractalWindow otherFractalWindow = null;

	private int mousePositionT;
	private int mousePositionX;

	public UIMouseMotionAdapter(OneTarget oneTarget, UIWindow meWindow) {
		this.oneTarget = oneTarget;
		this.meWindow = meWindow;
	}

	public void setApplicationWindow(ApplicationWindow otherApplicationWindow) {
		this.otherApplicationWindow = otherApplicationWindow;
	}

	public void setDesignWindow(FractalWindow otherFractalWindow) {
		this.otherFractalWindow = otherFractalWindow;
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		mousePositionT = me.getX();
		mousePositionX = me.getY();
		// mousePosition.y = 0;
		// mousePosition.z = 0;
		this.oneTarget.update(mousePositionT, mousePositionX);
		this.meWindow.activate();
		if (this.otherApplicationWindow != null) {
			this.otherApplicationWindow.deactivate();
		}
		if (this.otherFractalWindow != null) {
			this.otherFractalWindow.deactivate();
		}
		Application.ME.repaint();
	}

}
