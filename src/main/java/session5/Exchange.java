package session5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Exchange {
	public static void main(String arg[]) {
	}

	private int numberOfCoins;
	private int[] bestCoins;
	private int[][] totalCoins;
	private boolean[][] inCoins;
	private int[] coins;
	private int amount;

	public Exchange(int[] coins, int amount) {
		this.coins = coins;
		this.amount = amount;	
		bestCoins = new int[coins.length];
		numberOfCoins = coins.length-1;
	}

	public Exchange(String fileName) {
		BufferedReader file = null;
		String line;

		try {
			// we open the text file
			file = new BufferedReader(new FileReader(fileName));
			line = file.readLine();
			numberOfCoins = Integer.parseInt(line);

			line = file.readLine();
			String[] aux = line.split("\t");
			coins = new int[numberOfCoins];
			bestCoins = new int[numberOfCoins];

			for (int i = 0; i < aux.length; i++) {
				coins[i] = Integer.parseInt(aux[i]);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
		} catch (IOException e) {
			System.out.println("File reading error: " + fileName);
		}
	}

	public int getNumCoins(int amount) {
		this.amount = amount;
		totalCoins = new int[numberOfCoins + 1][amount + 1];
		inCoins = new boolean[numberOfCoins + 1][amount + 1];
		putInfinites(amount);

		for (int i = 1; i < numberOfCoins + 1; i++) {
			for (int j = 1; j < amount + 1; j++) {
				if (coins[i - 1] == 1) {
					totalCoins[i][j] = j;
					inCoins[i][j] = true;
				} else {

					int prevRow = totalCoins[i - 1][j];
					int prevValue;
					if (j - coins[i - 1] >= 0)
						prevValue = totalCoins[i][j - coins[i - 1]] + 1;
					else
						prevValue = Integer.MAX_VALUE;
					if (prevRow >= prevValue && prevValue <= amount) {
						totalCoins[i][j] = prevValue;
						inCoins[i][j] = true;
					} else {
						totalCoins[i][j] = prevRow;
					}
				}
			}
		}

		return totalCoins[numberOfCoins][amount];
	}

	private void putInfinites(int amount) {

		for (int j = 0; j < amount + 1; j++) {
			totalCoins[0][j] = Integer.MAX_VALUE;

		}
	}

	public int[] getBestCoins() {
		int aux = amount;
		int coinSelected = coins[numberOfCoins - 1];
		int posCoin = numberOfCoins;
		while (aux != 0) {
			if (inCoins[posCoin][aux]) {
				aux = aux - coinSelected;
				bestCoins[posCoin - 1]++;
			} else {
				posCoin--;
				coinSelected = coins[posCoin - 1];
			}

		}
		return bestCoins;
	}

}