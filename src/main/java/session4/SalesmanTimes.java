package session4;

public class SalesmanTimes {
	public static void main(String[] args) {
		int ntimes = 100;
		long t1, t2, t3, t4;
		for (int n = 10; n < Integer.MAX_VALUE; n *= 2) {
			Salesman s = new Salesman(n, n * 2);
			t1 = System.currentTimeMillis();
			for (int i = 0; i < ntimes; i++)
				s.greedy1(0);
			t2 = System.currentTimeMillis();
			t3 = System.currentTimeMillis();
			for (int i = 0; i < ntimes; i++)
				s.greedy2();
			t4 = System.currentTimeMillis();
			System.out.println(String.format("TIME MEASURES -- n = %d -- ntimes = %d -- GREEDY1 = %d -- GREEDY2 = %d",
					n, ntimes, t2 - t1, t4 - t3));
		}
	}
}
