package session1_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vector4 {

	private static Logger log = LoggerFactory.getLogger(Vector4.class);

	public static void main(String[] args) {
		int s = 0;
		int nTimes = Integer.parseInt(args[0]);
		for (int n = 10; n < Integer.MAX_VALUE; n *= 3) {
			int[] v = new int[n];
			Vector1.fillIn(v);

			long t1, t2;
			t1 = System.currentTimeMillis();
			for (int rep = 1; rep < nTimes; rep++) {
				 Vector1.maximum(v, new int[2]);
			}
			t2 = System.currentTimeMillis();

			System.out.println(
					String.format("SIZE = %d ** TIME = %d ms ** SUM = %d ** TIMES = %d", n, t2 - t1, s, nTimes));
		}
	}

}
