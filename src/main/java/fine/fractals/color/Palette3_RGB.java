package fine.fractals.color;

import fine.fractals.color.things.ColorTest;
import fine.fractals.color.things.Function;
import fine.fractals.color.things.Palette;

import java.awt.*;
import java.util.ArrayList;

public class Palette3_RGB extends Palette {

	public Palette3_RGB() {
		super("Palette3_RGB");
		/* Color for Highest values is first */

		spectrumR = new ArrayList<>();
		spectrumG = new ArrayList<>();
		spectrumB = new ArrayList<>();

		final Color black = new Color(0, 0, 0);

		final Color r = Color.RED;
		final Color g = Color.GREEN;
		final Color b = Color.BLUE;

		fromTo(spectrumR, black, r, Function.circleUp);
		fromTo(spectrumG, black, g, Function.circleUp);
		fromTo(spectrumB, black, b, Function.circleUp);
	}

	public static void main(String[] args) {
		ColorTest.execute(new Palette3_RGB());
	}
}
