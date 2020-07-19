package fine.fractals.ui;

import fine.fractals.Application;
import fine.fractals.Time;
import fine.fractals.engine.CalculationThread;
import fine.fractals.engine.FractalEngine;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UIMouseListener implements MouseListener {

	private static Time time = new Time(UIMouseListener.class);

	public UIMouseListener() {
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		if (!Application.REPEAT) {
			if (SwingUtilities.isRightMouseButton(me)) {
				time.now("Right click");
				if (!FractalEngine.calculationInProgress) {
					Application.ME.areaDomain.moveToCoordinates(Application.ME.getTarget());
					Application.ME.areaImage.moveToCoordinates(Application.ME.getTarget());
					Application.ME.zoomIn();
					CalculationThread.calculate(0);
				}
			} else {
				time.now("Left click, FIX, then use Enter");
				Application.ME.getEngine().fixDomainOnClick(Application.ME.getTarget());
			}
		} else {
			time.now("click skipped");
		}
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
