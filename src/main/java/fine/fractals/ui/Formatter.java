package fine.fractals.ui;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Formatter {

	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu_MM_dd-HH-mm-ss").withZone(ZoneId.of("CET"));

	public static String roundString(double d) {
		long multiplier = 100000000000000L;
		return String.format("%1$,.14f", Math.floor(d * multiplier) / multiplier).replaceAll(",", ".");
	}

	public static String round4(double d) {
		long multiplier = 10_000L;
		return String.format("%1$,.4f", Math.floor(d * multiplier) / multiplier);
	}

	public static String round(double d) {
		return String.format("%1$,.0f", Math.floor(d));
	}

	public static String now() {
		return LocalDateTime.now().format(dtf);
	}

}
