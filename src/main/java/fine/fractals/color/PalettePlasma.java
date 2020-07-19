package fine.fractals.color;

import fine.fractals.color.things.ColorTest;
import fine.fractals.color.things.Palette;

import java.awt.*;
import java.util.LinkedList;

public class PalettePlasma extends Palette {

	@Deprecated
	public PalettePlasma() {
		super("PalettePlasma");

		final LinkedList<Color> colors = new LinkedList<>();
		colors.add(new Color(239, 250, 26)); //	Yellow
		colors.add(new Color(247, 211, 30)); //	Broom
		colors.add(new Color(251, 160, 38)); //	Lightning yellow
		colors.add(new Color(231, 101, 65)); //	Coral
		colors.add(new Color(213, 72, 81)); //	Punch
		colors.add(new Color(177, 34, 109)); //	Hot pink
		colors.add(new Color(153, 8, 128)); //	Orchid
		colors.add(new Color(131, 1, 142)); //	Dark magenta
		colors.add(new Color(90, 0, 150)); //		Indigo
		colors.add(new Color(62, 0, 145)); //		Navy
		colors.add(new Color(37, 1, 133)); //		Navy
		colors.add(new Color(7, 1, 116)); //		Navy

		// super.spectrumColorsToLinear(colors);
	}

	public static void main(String[] args) {
		ColorTest.execute(new PalettePlasma());
	}
}