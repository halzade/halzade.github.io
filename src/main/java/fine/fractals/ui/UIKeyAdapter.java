package fine.fractals.ui;

import fine.fractals.Application;
import fine.fractals.ApplicationWindow;
import fine.fractals.Time;
import fine.fractals.Main;
import fine.fractals.color.PaletteBW3;
import fine.fractals.color.PalettePurpleOrange;
import fine.fractals.engine.CalculationThread;
import fine.fractals.engine.FractalEngine;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UIKeyAdapter extends KeyAdapter {

	private static Time time = new Time(UIKeyAdapter.class);

	private static int lastNumber = 0;
	public static Integer lastNumberKey = null;

	private boolean start = false;

	/* There should be only One UIKeyAdapter and UIKeyDispatcher */
	public UIKeyAdapter() {
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		Integer code = ke.getKeyCode();
		time.now(KeyEvent.getKeyText(code) + " | " + code + " | " + ke.getKeyChar());
		switch (code) {
			// case KeyEvent.VK_F:
			// 	/* Reset optimization and recalculate */
			// 	if (!FractalEngine.calculationInProgress) {
			// 		Application.colorPaletteMandelbrot.reset();
			// 		this.Application.ME.fixDomainOptimizationSoftCareful();
			// 		FractalEngine.setFixInProgress(true);
			// 		CalculationThread.calculate(0);
			// 	} else {
			// 		time.now("k no action f Careful");
			// 	}
			// 	break;

			// case KeyEvent.VK_Q:
			// 	/* recalculate all points */
			// 	if (!FractalEngine.calculationInProgress) {
			// 		this.Application.ME.fixDomainOptimizationHardCareful();
			// 		CalculationThread.calculate(0);
			// 	} else {
			// 		time.now("k no action q Careful");
			// 	}
			// 	break;

			case KeyEvent.VK_ADD:
				/* plus */
				if (!UIKeyDispatcher.isCapsLock()) {
					FractalEngine.ME.calculateFractalColoring();
				} else {
					time.now("no calculateFractalColoring() with caps-lock");
				}
				break;

			case KeyEvent.VK_SUBTRACT:
				/* minus */
				if (!UIKeyDispatcher.isCapsLock()) {
					FractalEngine.ME.calculateFractalColoring();
				} else {
					time.now("no calculateFractalColoring() with caps-lock");
				}
				break;

			case KeyEvent.VK_T:
				/* Switch Mouse display mode */
				Application.ME.toggleRectangle();
				break;

			case KeyEvent.VK_S:
				FractalEngine.save();
				break;

			case KeyEvent.VK_C:
				/* Copy cursor location to clipboard and log */
				String targetString
						= "public static double INIT_IMAGE_TARGET_reT = " + Application.ME.getTarget().getTextReT() + ";\n"
						+ "public static double TARGET_IM = " + Application.ME.getTarget().getTextImX() + ";\n"
						+ "public static double INITIAL_AREA_DOMAIN_SIZE = " + Application.ME.areaDomain.sizeImXString() + ";\n"
						+ "public static double INITIAL_AREA_IMAGE_SIZE = " + Application.ME.areaImage.sizeImXString() + ";";
				time.red(targetString);
				StringSelection stringSelection = new StringSelection(targetString);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
				break;

			case KeyEvent.VK_M:
				// Design.toggleDoModulo();
				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_NUMPAD0:
			case KeyEvent.VK_0:
				// Venture.colorPalette = new PaletteHue();
				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_NUMPAD1:

			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_2:
				Main.colorPalette = new PaletteBW3();
				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_NUMPAD3:
			case KeyEvent.VK_3:

			case KeyEvent.VK_NUMPAD4:
			case KeyEvent.VK_4:
				Main.colorPalette = new PalettePurpleOrange();
				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_NUMPAD5:
			case KeyEvent.VK_5:

				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_6:

				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_NUMPAD7:
			case KeyEvent.VK_7:

				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_8:

				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_NUMPAD9:
			case KeyEvent.VK_9:
				// Venture.colorPalette = new PaletteDoubleSafe();
				FractalEngine.ME.calculateFractalColoring();
				break;

			case KeyEvent.VK_MULTIPLY:
				break;

			case KeyEvent.VK_I:
				ApplicationWindow.showInfo = !ApplicationWindow.showInfo;
				break;

			case KeyEvent.VK_Z:
				break;

			case KeyEvent.VK_P:
				/* Pause or start calculation */
				synchronized (this) {
					if (start) {
						Application.REPEAT = true;
						CalculationThread.calculate(0);
					} else {
						Application.REPEAT = false;
					}
					start = !start;
				}
				break;

			case KeyEvent.VK_L:

				break;
			case KeyEvent.VK_R:

				break;
			case KeyEvent.VK_B:

				break;
		}

		if (!Application.REPEAT) {
			switch (code) {

				case KeyEvent.VK_N:
					break;

				case KeyEvent.VK_LEFT:
					Application.ME.getTarget().move(-1, 0);
					break;
				case KeyEvent.VK_UP:
					Application.ME.getTarget().move(0, -1);
					break;
				case KeyEvent.VK_RIGHT:
					Application.ME.getTarget().move(1, 0);
					break;
				case KeyEvent.VK_DOWN:
					Application.ME.getTarget().move(0, 1);
					break;

				case KeyEvent.VK_ESCAPE:
					/* EXIT */
					Application.ME.dispose();
					break;

				case KeyEvent.VK_SPACE:
					/* ZOOM in with no move */
					if (!FractalEngine.calculationInProgress) {
						Application.ME.zoomIn();
						CalculationThread.calculate(0);
					} else {
						time.now("no action space");
					}
					break;

				case KeyEvent.VK_BACK_SPACE:
					/* ZOOM out and recalculate */
					if (!FractalEngine.calculationInProgress) {
						Application.ME.zoomOut();
						time.now("Zoomed out, to recalculate Enter");
					} else {
						time.now("no action back space");
					}
					break;

				case KeyEvent.VK_ENTER:
					/* Recalculate with no Move or Zoom */
					if (!FractalEngine.calculationInProgress) {
						CalculationThread.calculate(0);
					} else {
						time.now("no action enter");
					}
					break;
			}
		}
	}

}
