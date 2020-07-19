package fine.fractals;

import fine.fractals.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FractalWindow extends UIWindow {

	public final JFrame frame;
	private final BufferedImage designImage;

	public FractalWindow(OneTarget target,
						 UIMouseListener uiMouseListener,
						 UIMouseWheelListener uiMouseWheelListener,
						 UIKeyDispatcher uiKeyDispatcher,
						 BufferedImage designImage,
						 int resolutionScrT,
						 int resolutionScrX,
						 final String name
	) {
		super(target, name, resolutionScrT, resolutionScrX);

		this.designImage = designImage;

        /*  Initialize UI */
		this.frame = new JFrame(name);
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.getContentPane().add(this);
		this.frame.pack();
		this.frame.setLocationByPlatform(true);
		this.frame.setVisible(true);

        /* Initialize UI Actions */
		JLayeredPane layeredPane = this.frame.getRootPane().getLayeredPane();
		super.hideDefaultCursor(frame);

		this.motionAdapter = new UIMouseMotionAdapter(this.target, this);
		layeredPane.addMouseMotionListener(this.motionAdapter);

		this.frame.addKeyListener(new UIKeyAdapter());

		layeredPane.addMouseListener(uiMouseListener);
		layeredPane.addMouseWheelListener(uiMouseWheelListener);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(uiKeyDispatcher);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		// FractalMachine.addText(designImage);

		/* image full size */
		// g2d.drawImage(designImage, 0, 0, designImage.getWidth(), designImage.getHeight(), null);
		/* image size fit to window size */
		g2d.drawImage(designImage, 0, 0, getWidth(), getHeight(), null);


		super.drawMouseCursor(g2d);
	}

	@Override
	public Dimension getPreferredSize() {
		if (Main.RESOLUTION_IMAGE_T > 1000) {
			return new Dimension(1000, 1000);
		}
		// return new Dimension(resolutionT, resolutionX);
		return new Dimension(Main.RESOLUTION_IMAGE_T, Main.RESOLUTION_IMAGE_X);
	}
}
