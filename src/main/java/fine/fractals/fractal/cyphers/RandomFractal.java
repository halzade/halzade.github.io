package fine.fractals.fractal.cyphers;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public class RandomFractal {

	public static void main(String[] args) {
		final int width = 10;
		final int height = 10;
		final int total = width * height;
		int index = -1;
		Random random = new Random();

		while (++index < total) {

			byte[] secret = new byte[64];
			random.nextBytes(secret);

			ByteBuffer wrapped = ByteBuffer.wrap(secret);
			wrapped.order(ByteOrder.LITTLE_ENDIAN);
			long number = wrapped.getLong();

			System.out.println(number);


			long out = number % total;
			if (out < 0) {
				out = out + total;
			}
			System.out.println(out);
			long x = out % width;
			long y = (long) ((double) out / (double) height);
			System.out.println(x + " | " + y);
		}
	}
}
