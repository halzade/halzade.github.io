package fine.fractals.ui;

import fine.fractals.Time;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class UIMouseWheelListener implements MouseWheelListener {

	private static Time time = new Time(UIMouseWheelListener.class);

	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {

		if (UIKeyDispatcher.isCtrl()) {

		} else if (UIKeyDispatcher.isAlt()) {

		}

		if (mwe.getWheelRotation() < 0) {
			/* Mouse wheel moved UP */

		} else {
			/* Mouse wheel moved DOWN */

		}

	}

}
