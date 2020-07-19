package fine.fractals.fractal.cyphers;

import javax.crypto.Cipher;
import java.security.*;

public class RSA {

	private static final String CYPHER = "RSA";

	private static final KeyPair KEY_PAIR = init();
	private static final PublicKey KEY_PUBLICK = KEY_PAIR.getPublic();
	private static final PrivateKey KEY_PRIVATE = KEY_PAIR.getPrivate();

	private static final String MESSAGE = "Who is John Galt?";

	public static void main(String[] args) throws Exception {

		// encrypt the message
		byte[] encrypted = encrypt(MESSAGE);
		System.out.println(new String(encrypted));

		// decrypt the message
		byte[] secret = decrypt(encrypted);
		System.out.println(new String(secret));
	}

	public static KeyPair init() {
		try {
			final int keySize = 512;
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CYPHER);
			keyPairGenerator.initialize(keySize);
			return keyPairGenerator.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static byte[] encrypt(String message) {
		try {
			Cipher cipher = Cipher.getInstance(CYPHER);
			cipher.init(Cipher.ENCRYPT_MODE, KEY_PRIVATE);
			return cipher.doFinal(message.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static byte[] decrypt(byte[] encrypted) {
		try {
			Cipher cipher = Cipher.getInstance(CYPHER);
			cipher.init(Cipher.DECRYPT_MODE, KEY_PUBLICK);
			return cipher.doFinal(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

}
