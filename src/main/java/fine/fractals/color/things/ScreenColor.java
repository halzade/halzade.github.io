package fine.fractals.color.things;

import java.awt.*;

public class ScreenColor {

	public static Color _NULL = Color.CYAN;
	public static Color _MOVED = Color.YELLOW;
	public static Color _ACTIVE_NEW = Color.GREEN;
	public static Color _ACTIVE_FIXED = Color.BLUE;
	public static Color _HIBERNATED_BLACK = Color.BLACK;
	public static Color _HIBERNATED_BLACK_NEIGHBOR = Color.GRAY;
	public static Color _FINISHED_OUTSIDE = Color.WHITE;
	public static Color _FINISHED_INSIDE = Color.RED;
	public static Color _ERROR = Color.ORANGE;
	public static Color _OPTIMIZATION = Color.PINK;
	/**
	 * For optimization fix
	 */
	public static Color _RECALCULATE = new Color(155, 1, 198);
	/**
	 * To be calculated as wrapped
	 */
	public static Color _MARK = new Color(123, 0, 255);
	/**
	 * To be calculated as wrapped: Finished
	 */
	public static Color _MARK_FINISHED = new Color(70, 0, 145);

}
