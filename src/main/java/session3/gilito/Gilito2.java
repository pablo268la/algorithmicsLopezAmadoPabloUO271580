package session3.gilito;

public class Gilito2 {

	private int[] coins; // weight in grams of the n coins
	private long watts; // average energy consumed (watts)
	public static int REAL_WEIGHT = 1000;
	public static int FAKE_WEIGHT = 999;

	public Gilito2(int n) {
		this.coins = new int[n];
	}

	public void setCoinWeight(int position, int value) {
		this.coins[position] = value;
	}

	public long getUsedWatts() {
		return this.watts;
	}

	public void resetUsedWatts() {
		this.watts = 0;
	}

	/**
	 * Weigh the coins of the left plate between the leftMin and leftMax positions
	 * Weigh the coins of the right plate between the rightMin and righMax positions
	 * 
	 * @return 1 if left side is less heavy, 2 if right side is less heavy and 3 if
	 *         they weigh the same
	 */
	public int balance(int leftMin, int leftMax, int rightMin, int rightMax) {
		watts++; // 1 watts used

		int leftWeight = 0; // weight of left plate
		for (int i = leftMin; i <= leftMax; i++)
			leftWeight += this.coins[i];

		int rightWeight = 0; // weight of right plate
		for (int i = rightMin; i <= rightMax; i++)
			rightWeight += this.coins[i];

		if (leftWeight < rightWeight)
			return 1;
		if (leftWeight > rightWeight)
			return 2;
		return 3;
	}

	/**
	 * Current algorithm, which we are asked to improve
	 * 
	 * @return position of the fake coin Best case: the fake coin is one of the two
	 *         first coins (1 watt used) Worst case: the fake coin is the last one
	 *         (n/2 watts used approximately) Average case: average of all the
	 *         positions (n/4 watts used approximately) This algorithm is linear
	 *         (O(n)) in the worst and average cases
	 */
	public int calculate() {
		return calculate3(0, this.coins.length - 1);

	}

	private int calculate(int i, int j) {
		int medio = (i + j) / 2;
		if (i < j) {
			// If odd size, check the last
			if ((i + j) % 2 == 0) {
				int aux = balance(j - 1, j - 1, j, j);
				if (aux == 1) {
					return j - 1;
				} else if (aux == 2) {
					return j;
				} else {
					j--;
				}

			}
			int bal = balance(i, medio, medio + 1, j);

			if (bal == 1) {
				return calculate(i, medio);
			} else if (bal == 2) {
				return calculate(medio + 1, j);
			}

		}
		return i;
	}

	private int calculate3(int left, int right) {
		int gap = right - left;
		int med1 = left + gap / 3;
		int med2 = left + 2 * gap / 3;

		if (left < right) {
			int aux = balance(left, med1, med1 + 1, med2);
			if (aux == 1) {
				return calculate3(left, med1);
			} else if (aux == 2) {
				return calculate3(med1 + 1, med2);
			} else {
				return calculate3(med2 + 1, right);
			}
		}

		return left;
	}

	public static void main(String arg[]) {
		int n = Integer.parseInt(arg[0]); // number of coins (size of the problem)
		Gilito2 gilito = new Gilito2(n);

		// let's simulate the n possible cases - false currency in each position
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				gilito.setCoinWeight(j, REAL_WEIGHT); // authentic coin weight
			gilito.setCoinWeight(i, FAKE_WEIGHT); // fake coin weight
			gilito.calculate();
		}
		System.out.println("COINS=" + n + " ***AVERAGE ENERGY=" + gilito.getUsedWatts() / n + " watts");
	}
}
