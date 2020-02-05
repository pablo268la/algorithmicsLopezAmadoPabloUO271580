package session1_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vector3 {

	private static Logger log = LoggerFactory.getLogger(Vector3.class);

	public static void main(String[] args) {
		int s;
		for (int n = 10; n < Integer.MAX_VALUE; n *= 2) {
			int[] v = new int[n];
			Vector1.fillIn(v);

			long t1, t2;
			t1 = System.currentTimeMillis();
			s = Vector1.sum(v);
			t2 = System.currentTimeMillis();

			System.out.println(String.format("SIZE = %d ** TIME = %d ms ** SUM = %d", n, t2 - t1, s));
		}
	}

}
