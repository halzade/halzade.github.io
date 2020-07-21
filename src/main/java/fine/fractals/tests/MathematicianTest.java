package fine.fractals.tests;

import fine.fractals.fractal.longorbits.Tiara;
import fine.fractals.math.Mathematician;
import org.junit.Test;

public class MathematicianTest {

	@Test
	public void test() {

		new Tiara();

		final Mathematician mathematician = new Mathematician();

		assert mathematician.isFibonacci(1);
		assert mathematician.isFibonacci(2);
		assert mathematician.isFibonacci(3);
		assert mathematician.isFibonacci(5);
		assert mathematician.isFibonacci(8);
		assert mathematician.isFibonacci(13);

		assert !mathematician.isFibonacci(4);
		assert !mathematician.isFibonacci(6);
		assert !mathematician.isFibonacci(7);
		assert !mathematician.isFibonacci(9);
		assert !mathematician.isFibonacci(10);
		assert !mathematician.isFibonacci(11);
		assert !mathematician.isFibonacci(12);
		assert !mathematician.isFibonacci(14);


		assert mathematician.isSquare(4);
		assert mathematician.isSquare(9);
		assert mathematician.isSquare(100);

		assert !mathematician.isSquare(17);


		assert mathematician.isPrime(3);
		assert mathematician.isPrime(5);
		assert mathematician.isPrime(7);
		assert mathematician.isPrime(13);
		assert mathematician.isPrime(19);

		assert !mathematician.isPrime(4);
		assert !mathematician.isPrime(6);
		assert !mathematician.isPrime(8);
		assert !mathematician.isPrime(9);
		assert !mathematician.isPrime(15);
		assert !mathematician.isPrime(100);
		assert !mathematician.isPrime(1000);
		assert !mathematician.isPrime(10000);
		assert !mathematician.isPrime(100000);

	}
}
