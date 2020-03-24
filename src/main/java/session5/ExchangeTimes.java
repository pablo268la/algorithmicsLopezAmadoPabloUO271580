package session5;
public class ExchangeTimes {

	public static void main(String[] args) {
		int[] coins = new int[] { 0, 1, 2, 3, 5, 7, 11, 13, 17, 19, 23 };
		long t1, t2;
		for (int n = 10000; n < Integer.MAX_VALUE; n *= 2) {
			Exchange exchange = new Exchange(coins, n);
			t1 = System.currentTimeMillis();
			int bestCoinsNumber = exchange.getNumCoins(n);
			t2 = System.currentTimeMillis();
			System.out.println(String.format("Times taken: n = %d - time = %d - number of coins = %d", n, t2 - t1,
					bestCoinsNumber));
		}
	}

}
