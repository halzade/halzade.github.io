package fine.fractals.data.log;


import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class LogFormatter extends SimpleFormatter {

	public LogFormatter() {
	}

	/**
	 * Remove time and class info from the line.
	 */
	@Override
	public String format(LogRecord record) {
		return record.getMessage() + "\n";
	}
}