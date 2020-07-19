package fine.fractals.color.things;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class ColorTest extends JComponent {

	private static JFrame frame;
	private static Palette palette;
	private boolean painted = false;
	private static Integer resolutionR = null;
	private static Integer resolutionG = null;
	private static Integer resolutionB = null;
	private static Integer res = null;
	private BufferedImage testImage = null;

	private ColorTest() {
	}

	private BufferedImage applyTestColorPalette() {

		BufferedImage testImage = new BufferedImage(res, 300, BufferedImage.TYPE_INT_RGB);
		int imageSize = testImage.getWidth();

		for (int y = 0; y < 100; y++) {
			for (int x = 0; x < resolutionR; x++) {
				testImage.setRGB(x, y, palette.getSpectrumValueR(x).getRGB());
			}
		}

		for (int y = 100; y < 200; y++) {
			for (int x = 0; x < resolutionG; x++) {
				testImage.setRGB(x, y, palette.getSpectrumValueG(x).getRGB());
			}
		}

		for (int y = 200; y < 300; y++) {
			for (int x = 0; x < resolutionB; x++) {
				testImage.setRGB(x, y, palette.getSpectrumValueB(x).getRGB());
			}
		}
		return testImage;
	}

	public static void execute(Palette palette) {
		resolutionR = palette.colorResolutionR();
		resolutionG = palette.colorResolutionG();
		resolutionB = palette.colorResolutionB();

		res = palette.max(resolutionR, resolutionG, resolutionB);

		ColorTest.palette = palette;
		ColorTest app = new ColorTest();

		frame = new JFrame("ColorTest: " + Palette.getName());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(app);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frame.dispose();
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		if (!painted) {
			painted = true;
			testImage = applyTestColorPalette();
		}
		g2d.drawImage(testImage, 0, 0, res, 300, null);
		g2d.dispose();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(res, 300);
	}

}
