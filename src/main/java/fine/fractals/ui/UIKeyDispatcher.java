package fine.fractals.ui;

import fine.fractals.Time;

import java.awt.*;
import java.awt.event.KeyEvent;

public class UIKeyDispatcher implements KeyEventDispatcher {

	private static boolean ctrlPressed = false;
	private static boolean altPressed = false;
	private static boolean capsLockDown = false;

	private static Time time = new Time(UIKeyDispatcher.class);

	public UIKeyDispatcher() {

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent ke) {

		Integer code = ke.getKeyCode();

		time.now(KeyEvent.getKeyText(code) + " | " + code + " | " + ke.getKeyChar());

		switch (ke.getID()) {
			case KeyEvent.KEY_PRESSED:
				if (code == KeyEvent.VK_CONTROL) {
					ctrlPressed = true;
				}
				if (code == KeyEvent.VK_ALT) {
					altPressed = true;
				}
				break;
			case KeyEvent.KEY_RELEASED:
				if (code == KeyEvent.VK_CONTROL) {
					ctrlPressed = false;
				}
				if (code == KeyEvent.VK_ALT) {
					altPressed = false;
				}
				break;
		}

		capsLockDown = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		System.out.println("capsLockDown " + capsLockDown);

		return false;
	}

	public static boolean isCtrl() {
		return UIKeyDispatcher.ctrlPressed;
	}

	public static boolean isAlt() {
		return UIKeyDispatcher.altPressed;
	}

	public static boolean isCapsLock() {
		return UIKeyDispatcher.capsLockDown;
	}

}
