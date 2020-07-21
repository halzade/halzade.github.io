package fine.fractals.math;

import fine.fractals.fractal.Fractal;

import java.util.HashSet;

public class Mathematician {

	private static HashSet<Integer> PRIMES;
	private static HashSet<Integer> FIBONACCI;
	private static HashSet<Integer> PERFECT;
	private static HashSet<Integer> SQUARE;

	private static int INIT_MAX;

	private static boolean initiated = false;

	public Mathematician() {
	}

	public static void initialize() {

		INIT_MAX = Fractal.ITERATION_MAX;

		if (initiated) {
			throw new RuntimeException("Mathematician already initialized!");
		}

		synchronized (Mathematician.class) {
			if (!initiated) {
				initPrimes();
				initFibonaci();
				initSquares();
				// initPerfectNumbers();
				initiated = true;
			}
		}
	}

	private static void initPrimes() {
		PRIMES = new HashSet<>();
		for (int i = 0; i < INIT_MAX; i++) {
			if (isPrimeInit(i)) {
				PRIMES.add(i);
			}
		}
	}

	private static void initFibonaci() {

		FIBONACCI = new HashSet<>();

		int a = 0;
		int b = 1;
		int sum;

		while (b <= INIT_MAX) {
			sum = a + b;

			FIBONACCI.add(sum);

			a = b;
			b = sum;
		}
	}

	private static void initPerfectNumbers() {
		PERFECT = new HashSet<>();
		for (int i = 0; i <= INIT_MAX; i++) {
			if (isPerfectInit(i)) {
				PERFECT.add(i);
			}
		}
	}

	private static void initSquares() {
		SQUARE = new HashSet<>();
		int sq;
		for (int i = 0; i * i <= INIT_MAX; i++) {
			sq = i * i;
			SQUARE.add(sq);
		}
	}

	private static boolean isPrimeInit(int n) {
		if (n % 2 == 0) {
			return false;
		}
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	private static boolean isPerfectInit(int number) {
		int temp = 0;
		for (int i = 1; i <= number / 2; i++) {
			if (number % i == 0) {
				temp += i;
			}
		}
		return temp == number;
	}

	public boolean isPrime(Integer n) {
		return PRIMES.contains(n);
	}

	public boolean isFibonacci(Integer n) {
		return FIBONACCI.contains(n);
	}

	public boolean isPerfect(Integer n) {
		return PERFECT.contains(n);
	}

	public boolean isSquare(Integer n) {
		return SQUARE.contains(n);
	}

}
