package fine.fractals;

import fine.fractals.data.Key;
import fine.fractals.data.objects.Data;
import fine.fractals.engine.FractalEngine;
import fine.fractals.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ApplicationWindow extends UIWindow {

	public final JFrame frame;
	private int lineHeight;
	private String name;

	public static boolean repaintDone = false;
	public static boolean showInfo = true;

	private BufferedImage mandelbrotMask = new BufferedImage(this.resolutionT, this.resolutionX, BufferedImage.TYPE_INT_RGB);

	public ApplicationWindow(OneTarget target, UIMouseListener uiMouseListener,
							 UIMouseWheelListener uiMouseWheelListener,
							 UIKeyDispatcher uiKeyDispatcher,
							 int resolutionT, int resolutionX,
							 final String name
	) {
		super(target, name, resolutionT, resolutionX);
		this.name = name;

        /*  Initialize UI */
		this.frame = new JFrame(name);
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.getContentPane().add(this);
		this.frame.pack();
		this.frame.setLocationByPlatform(true);
		this.frame.setVisible(true);

        /* Initialize UI Actions */
		JLayeredPane layeredPane = this.frame.getRootPane().getLayeredPane();
		layeredPane.addMouseListener(uiMouseListener);
		super.hideDefaultCursor(frame);

		this.motionAdapter = new UIMouseMotionAdapter(this.target, this);
		layeredPane.addMouseMotionListener(this.motionAdapter);
		layeredPane.addMouseWheelListener(uiMouseWheelListener);

		this.frame.addKeyListener(new UIKeyAdapter());
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(uiKeyDispatcher);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		// g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));

		String n = FractalEngine.calculationProgress;
		if (n == null || "".equals(n)) {
			n = "00";
		}
		this.frame.setTitle(n + " - " + this.name);

		Color textColor;
		/* Paint mandelbrot Mask */
		Application.ME.getEngine().createMandelbrotMask(mandelbrotMask);
		/* image full size */
		// g2d.drawImage(mandelbrotMask, 0, 0, mandelbrotMask.getWidth(), mandelbrotMask.getHeight(), null);
		/* image size fit to window size */
		g2d.drawImage(mandelbrotMask, 0, 0, getWidth(), getHeight(), null);

		textColor = Color.BLACK;

		super.drawMouseCursor(g2d);

		if (showInfo) {

			g2d.setColor(textColor);
			this.lineHeight = g2d.getFontMetrics().getHeight();
			int line = 0;
		
			/* Calculation Text */
			if (FractalEngine.calculationInProgress) {
				g2d.drawString(FractalEngine.calculationText + " " + FractalEngine.calculationProgress, col(0), row(line));
			}
			line++;
		
			/* Data */
			for (String key : Key.keys) {
				g2d.drawString(key + ": ", col(0), row(line));
				g2d.drawString(Data.get(key), col(1), row(line));
				g2d.drawString(Data.getArchived(key), col(2), row(line));
				line++;
			}
			line++;
		
			/* Target coordinates PX */
			g2d.drawString("Target px: ", col(0), row(line));
			g2d.drawString(target.getScreenFromCornerT() + ", " + target.getScreenFromCornerX(), col(1), row(line));
			g2d.drawString(target.getScreenFromCenterT() + ", " + target.getScreenFromCenterX(), col(2), row(line));
			line++;
		
			/* Target coordinates domain */
			g2d.drawString("Target: ", col(0), row(line));
			g2d.drawString(target.getTextReT(), col(1), row(line));
			g2d.drawString(target.getTextImX(), col(2), row(line));
			line++;
		
			/* Area size domain */
			g2d.drawString("Domain h/w: ", col(0), row(line));
			g2d.drawString(Application.ME.areaDomain.sizeImXString(), col(1), row(line));
			g2d.drawString(Application.ME.areaDomain.sizeReTString(), col(2), row(line));
			line++;

			/* Area size image */
			g2d.drawString("Domain h/w: ", col(0), row(line));
			g2d.drawString(Application.ME.areaImage.sizeImXString(), col(1), row(line));
			g2d.drawString(Application.ME.areaImage.sizeReTString(), col(2), row(line));
			line++;
		
			/* Mandelbrot Element value */
			g2d.drawString("El. value: ", col(0), row(line));
			g2d.drawString(this.target.getMandelbrotValue(), col(1), row(line));
			g2d.drawString(this.target.getMandelbrotState(), col(2), row(line));
		}
		// if (design.addAllEscapePathsToSpectraFinished) {
		// 	showSpectra(g2d);
		// }

		ApplicationWindow.repaintDone = true;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(resolutionT, resolutionX);
	}

	private int col(int col) {
		return 20 + col * 180;
	}

	private int row(int line) {
		return 20 + line * lineHeight;
	}

}
