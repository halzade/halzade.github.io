package fine.fractals.color;

import fine.fractals.color.things.ColorTest;
import fine.fractals.color.things.Function;
import fine.fractals.color.things.Palette;

import java.util.ArrayList;

public class PaletteBW3 extends Palette {

	public PaletteBW3() {
		super("PaletteBW3");

		spectrumR = new ArrayList<>();
		spectrumG = new ArrayList<>();
		spectrumB = new ArrayList<>();

		fromTo(spectrumR, black, white, Function.circleUp);
		fromTo(spectrumG, black, white, Function.circleUp);
		fromTo(spectrumB, black, white, Function.circleUp);

	}

	public static void main(String[] args) {
		ColorTest.execute(new PaletteBW3());
	}

}
