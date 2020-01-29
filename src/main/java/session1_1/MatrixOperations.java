package session1_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MatrixOperations {

	private int[][] matrix;
	private int size;

	public MatrixOperations(int n) {
		size = n;
		matrix = new int[n][n];
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = r.nextInt(100);
			}
		}
	}

	public MatrixOperations(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String size = br.readLine();
			int n = Integer.parseInt(size);
			matrix = new int[n][n];
			String line;
			for (int i = 0; i < n; i++) {
				while ((line = br.readLine()) != null) {
					String parts[] = line.split("\t");
					for (int j = 0; j < n; j++) {
						matrix[i][j] = Integer.parseInt(parts[j]);
					}
				}
			}

		} catch (IOException e) {

		}
	}

	public int getSize() {
		return size;
	}

	public void write() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.println(matrix[i][j]);
			}
		}
	}

	public void sumDiagonal1() {
		int total = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) {
					total += matrix[i][j];
				}
			}
		}
	}

	public void sumDiagonal2() {
		int total = 0;
		for (int i = 0; i < size; i++) {
			total += matrix[i][i];
		}
	}
}
