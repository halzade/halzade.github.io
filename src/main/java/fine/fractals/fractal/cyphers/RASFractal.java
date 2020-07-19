package fine.fractals.fractal.cyphers;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class RASFractal {

	// private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	public static void main(String[] args) {
		final int width = 100;
		final int height = 100;
		final int total = width * height;
		final int length = 6;
		int index = -1;
		String sentence;

		RSA.init();


		while (index++ < total) {
			sentence = "";
			sentence += index;
			sentence = String.format("%0" + length + "d", Integer.parseInt(sentence));

			// System.out.println(sentence);

			byte[] secret = RSA.encrypt(sentence);
			// System.out.println(new String(out));


			ByteBuffer wrapped = ByteBuffer.wrap(secret);
			wrapped.order(ByteOrder.LITTLE_ENDIAN);
			long number = wrapped.getLong();

			// System.out.println(number);


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
