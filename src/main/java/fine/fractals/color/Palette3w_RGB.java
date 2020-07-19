package fine.fractals.color;

import fine.fractals.color.things.ColorTest;
import fine.fractals.color.things.Function;
import fine.fractals.color.things.Palette;

import java.awt.*;
import java.util.ArrayList;

public class Palette3w_RGB extends Palette {

	public Palette3w_RGB() {
		super("Palette3w_RGB");
		/* Color for Highest values is first */

		spectrumR = new ArrayList<>();
		spectrumG = new ArrayList<>();
		spectrumB = new ArrayList<>();

		final Color r = Color.RED;
		final Color g = Color.GREEN;
		final Color b = Color.BLUE;

		fromTo(spectrumR, black, r, Function.linear1);
		fromTo(spectrumG, black, g, Function.linear1);
		fromTo(spectrumB, black, b, Function.linear1);

		fromTo(spectrumR, r, black, Function.linear7);
		fromTo(spectrumG, g, black, Function.linear7);
		fromTo(spectrumB, b, black, Function.linear7);

		fromTo(spectrumR, black, r, Function.linear7);
		fromTo(spectrumG, black, g, Function.linear7);
		fromTo(spectrumB, black, b, Function.linear7);
	}

	public static void main(String[] args) {
		ColorTest.execute(new Palette3w_RGB());
	}
}
