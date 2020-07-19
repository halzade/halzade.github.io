package fine.fractals.data.comparator;

import fine.fractals.data.objects.Path;

import java.util.ArrayList;
import java.util.Comparator;

public class PathComparator implements Comparator<Path> {

	@Override
	public int compare(Path p1, Path p2) {
		if (p1.length() == p2.length()) {
			return 0;
		}
		return p1.length() > p2.length() ? 1 : -1;
	}

	public static long collect(ArrayList<Path> paths) {
		long ret = 0;
		for (Path path : paths) {
			ret += path.length();
		}
		return ret;
	}
}
