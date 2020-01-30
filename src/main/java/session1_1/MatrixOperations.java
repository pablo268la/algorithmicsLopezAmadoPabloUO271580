package session1_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class MatrixOperations {

	private int[][] matrix;
	private int size;

	public static void main(String[] args) {
		MatrixOperations mo = new MatrixOperations("src/main/java/session1_1/matrix.txt");
		mo.write();
		mo.travelPath(3, 0);
		System.out.println();
		System.out.println("-----------");
		System.out.println();
		mo.write();
	}

	/**
	 * 
	 * Creates a n*n matrix
	 * 
	 * @param n
	 */
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

	/**
	 * Reads a file and creates a matrix specified inside.
	 * 
	 * @param fileName
	 */
	public MatrixOperations(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String size = br.readLine();
			this.size = Integer.parseInt(size);
			matrix = new int[this.size][this.size];
			String line;
			int i = 0;
			while ((line = br.readLine()) != null) {
				String parts[] = line.split("\t");
				for (int j = 0; j < this.size; j++) {
					matrix[i][j] = Integer.parseInt(parts[j]);
				}
				i++;
			}

		} catch (IOException e) {
			System.out.println("File error. That file does not exist");
		}
	}

	/**
	 * 
	 * @return the size of the matrix
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Prints the matrix
	 */
	public void write() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Sums the elements at the diagonal with O(2)
	 * 
	 * @return the sum of the diagonal
	 */
	public int sumDiagonal1() {
		int total = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) {
					total += matrix[i][j];
				}
			}
		}
		return total;
	}

	/**
	 * Sums the elements at the diagonal with O(1)
	 * 
	 * @return the sum of the diagonal
	 */
	public int sumDiagonal2() {
		int total = 0;
		for (int i = 0; i < size; i++) {
			total += matrix[i][i];
		}
		return total;
	}

	/**
	 * Travels through the matrix. Depending on the value, it goes up (1), right
	 * (2), down (3) or left (4)
	 * 
	 * @param i
	 *            x position
	 * @param j
	 *            y position
	 */
	public void travelPath(int i, int j) {
		if (i >= size || i < 0)
			return;
		if (j >= size || j < 0)
			return;
		int value = matrix[i][j];

		switch (value) {
		case 1:
			matrix[i][j] = -1;
			travelPath(--i, j);
			break;
		case 2:
			matrix[i][j] = -1;
			travelPath(i, ++j);
			break;
		case 3:
			matrix[i][j] = -1;
			travelPath(++i, j);
			break;
		case 4:
			matrix[i][j] = -1;
			travelPath(i, --j);
			break;
		default:
			break;
		}

	}

}