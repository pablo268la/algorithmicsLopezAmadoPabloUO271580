package session3.gilito;

import java.util.Random;

public class GilitoComparison {
	public static void main(String arg[]) {
		int nTimes = 10;
		long t1, t2, t3, t4;
		int position1 = 0;
		int position2 = 0;
		Random random = new Random();
		for (int n = 10; n < Integer.MAX_VALUE; n *= 2) {
			Gilito2 gilito2 = new Gilito2(n);
			Gilito1 gilito1 = new Gilito1(n);
			for (int i = 0; i < n; i++) {
				gilito2.setCoinWeight(i, Gilito2.REAL_WEIGHT);
				gilito1.setCoinWeight(i, Gilito2.REAL_WEIGHT);
			}
			// authentic coin weight
			gilito1.setCoinWeight(n - 1, Gilito2.FAKE_WEIGHT);
			gilito2.setCoinWeight(n - 1, Gilito2.FAKE_WEIGHT); // worst case (last coin is the
																// fake one)

			t1 = System.currentTimeMillis();
			for (int i = 0; i < nTimes; i++) {
				position1 = gilito1.calculate();
			}
			t2 = System.currentTimeMillis();

			t3 = System.currentTimeMillis();
			for (int i = 0; i < nTimes; i++) {
				position2 = gilito2.calculate();
			}
			t4 = System.currentTimeMillis();
			System.out.println(String.format(
					"NCOINS=%d NTIMES=%d - GILITO1: FAKE_POSITION=%d ENERGY_USED=%d TIME=%d -- GILITO2: FAKE_POSITION=%d ENERGY_USED=%d TIME=%d ",
					n, nTimes, position1, gilito1.getUsedWatts() / nTimes, t2 - t1, position2,
					gilito2.getUsedWatts() / nTimes, t4 - t3));
		}
	}
}
