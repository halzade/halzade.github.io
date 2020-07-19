package fine.fractals.data;

import fine.fractals.data.objects.Data;

import java.util.LinkedList;

public class Key {

	public static LinkedList<String> keys = new LinkedList<>();

	public static final String A_ITERATION_MAX = "A_ITERATION_MAX";
	public static final String A_ITERATION_MIN = "A_ITERATION_MIN";

	public static final String T_ROTATION = "T_ROTATION";

	public static final String C_RES = "C_RES";
	public static final String C_STEP = "C_STEP";
	public static final String C_CURRENT_MAX = "C_CURRENT_MAX";
	public static final String C_CURRENT_MIN = "C_CURRENT_MIN";

	public static final String M_DIVERGED = "M_DIVERGED";
	public static final String M_SCR_MAX = "M_SCR_MAX";
	public static final String M_SCR_MIN = "M_SCR_MIN";
	public static final String M_HIGH_VALUE = "M_HIGH_VALUE";

	public static final String B_TOTAL_VALUE = "B_TOTAL_VALUE";
	public static final String B_ELEMENTS_SIZE = "B_ELEMENTS_SIZE";
	public static final String B_ELEMENTS_SMALL = "B_ELEMENTS_SMALL";
	public static final String B_SCR_MAX = "B_SCR_MAX";
	public static final String B_SCR_MIN = "B_SCR_MIN";

	static {
		/* Order for painting data on screen */
		keys.add(A_ITERATION_MAX);
		keys.add(A_ITERATION_MIN);
		keys.add(T_ROTATION);
		keys.add("");
		keys.add(C_RES);
		keys.add(C_STEP);
		keys.add(C_CURRENT_MAX);
		keys.add(C_CURRENT_MIN);
		keys.add("");
		keys.add(M_DIVERGED);
		keys.add(M_SCR_MAX);
		keys.add(M_SCR_MIN);
		keys.add(M_HIGH_VALUE);
		keys.add("");

		keys.add(B_TOTAL_VALUE);
		keys.add(B_ELEMENTS_SIZE);
		keys.add(B_ELEMENTS_SMALL);
		keys.add(B_SCR_MAX);
		keys.add(B_SCR_MIN);
		Data.set("", "");
	}

}
