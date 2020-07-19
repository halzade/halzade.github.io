package fine.fractals.color;

import fine.fractals.color.things.ColorTest;
import fine.fractals.color.things.Palette;

import java.awt.*;

public class PalettePurpleOrange extends Palette {

	@Deprecated
	public PalettePurpleOrange() {
		super("PalettePurpleOrange");

		Color color1 = new Color(53, 3, 112);
		Color color1_5 = new Color(35, 0, 79);
		Color color2 = new Color(109, 18, 83);
		Color color3 = new Color(176, 58, 52);
		Color color4 = new Color(238, 153, 0);
		Color color5 = new Color(255, 240, 195);

		// super.fromToLinear(color1, color1_5, 3);
		// super.fromToLinear(color1_5, color2, 2);
		// super.fromToLinear(color2, color3, 1);
		// super.fromToLinear(color3, color4, 0.5);
		// super.fromToLinear(color4, color5, 0.2);
		// super.fromToLinear(color5, white, 0.1);
	}

	public static void main(String[] args) {
		ColorTest.execute(new PalettePurpleOrange());
	}
}
