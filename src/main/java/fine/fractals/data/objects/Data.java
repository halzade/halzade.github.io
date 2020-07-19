package fine.fractals.data.objects;

import fine.fractals.data.Key;

import java.util.HashMap;

public class Data {

	private static final HashMap<String, String> map = new HashMap<>();
	private static final HashMap<String, String> mapArchived = new HashMap<>();

	private Data() {
	}

	public static void set(String key, Integer value) {
		map.put(key, String.valueOf(value));
	}

	public static void set(String key, Boolean value) {
		map.put(key, String.valueOf(value));
	}

	public static void set(String key, String value) {
		map.put(key, value);
	}

	public static void set(String key, Double value) {
		map.put(key, String.valueOf(value));
	}

	public static String get(String key) {
		return format(map.get(key));
	}

	public static String getArchived(String key) {
		return format(mapArchived.get(key));
	}

	public static void archive() {
		mapArchived.clear();
		for (String key : Key.keys) {
			mapArchived.put(key, map.get(key));
		}
		map.clear();
		map.put("", "");
		mapArchived.put("", "");
	}

	private static String format(String s) {
		if (s != null) {
			return s;
		}
		return "-";
	}

	/**
	 * Parenthesis
	 */
	public static String par(int s) {
		return "(" + s + ")";
	}

	public static String spar(int s) {
		return " " + Data.par(s);
	}
}
