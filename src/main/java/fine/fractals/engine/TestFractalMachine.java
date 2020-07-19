package fine.fractals.engine;

import org.junit.Test;

public class TestFractalMachine {


	@Test
	public void testInitRingSequence() {
		final int[] RING_SEQUENCE_XX = new int[24];
		final int[] RING_SEQUENCE_YY = new int[24];
		FractalMachine.initRingSequence(RING_SEQUENCE_XX, RING_SEQUENCE_YY);
		for (int i = 0; i < 24; i++) {
			int re = RING_SEQUENCE_XX[i];
			int im = RING_SEQUENCE_YY[i];
			for (int j = 0; j < 24; j++) {
				if (i != j) {
					if (re == RING_SEQUENCE_XX[j] && im == RING_SEQUENCE_YY[j]) {
						throw new RuntimeException("Fail " + i + " " + j);
					}
				}
			}
		}
	}
}