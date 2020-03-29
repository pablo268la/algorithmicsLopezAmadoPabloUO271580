package session6.boggle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Boggle {

	private Character[][] letters;
	private boolean[][] visited;
	private int size;
	private int totalPoints;

	private char[] solution;
	private List<String> solutions = new ArrayList<String>();

	private HashMap<String, ArrayList<String>> dict = new HashMap<>();
	private ArrayList<String> oneLetter = new ArrayList<>();
	private ArrayList<String> twoLetter = new ArrayList<>();

	private final static int MAX_LENGTH_WORD = 10;

	private List<Integer> points = Arrays.asList(0, 0, 1, 2, 5, 7, 9, 12, 15, 19, 24);
	private List<Character> lettersList = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

	public Boggle(String dictionaryFileName, String tableFileName) {
		readDictionaryFromFile(dictionaryFileName);
		readTableFromFile(tableFileName);
	}

	public Boggle(String dictionaryFileName, int tableSize) {
		readDictionaryFromFile(dictionaryFileName);
		letters = new Character[size][size];
		visited = new boolean[size][size];
		solution = new char[MAX_LENGTH_WORD > (size * size) ? (size * size) : MAX_LENGTH_WORD];
		fillRandomLetters();
	}

	private void fillRandomLetters() {
		for (int i = 0; i < letters[0].length; i++) {
			for (int j = 0; j < letters[0].length; j++) {
				Collections.shuffle(lettersList);
				letters[i][j] = lettersList.get(0);
			}
		}
	}

	public List<String> getSolutions() {
		return solutions;
	}

	public long getTotalPoints() {
		return totalPoints;
	}

	public void printTable() {
		for (int i = 0; i < letters[0].length; i++) {
			for (int j = 0; j < letters[0].length; j++) {
				System.out.print(letters[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printSolutions() {
		for (int i = 0; i < solutions.size(); i++)
			System.out.println(solutions.get(i));
	}

	public void findSolutions() {
		for (int i = 0; i < letters[0].length; i++)
			for (int j = 0; j < letters[0].length; j++) {
				backtracking(0, i, j);
				visited[i][j] = false;
				solution[0] = '\u0000';
			}
		Collections.sort(solutions);
	}

	private void backtracking(int level, int i, int j) {
		if (level >= solution.length)
			return;
		if (i < 0 || i >= size || j < 0 || j >= size)
			throw new IndexOutOfBoundsException();

		visited[i][j] = true;
		solution[level] = letters[i][j];

		if (!checkIfSolution(level)) {
			visited[i][j] = false;
			solution[level] = '\u0000';
			return;
		}

		for (int auxI = (i - 1); auxI < (i + 2) && auxI < size; auxI++)
			for (int auxJ = (j - 1); auxJ < (j + 2) && auxJ < size; auxJ++)
				if (!(auxI < 0 || auxJ < 0) && !visited[auxI][auxJ])
					backtracking(level + 1, auxI, auxJ);

		visited[i][j] = false;
		solution[level] = '\u0000';

	}

	private boolean checkIfSolution(int level) {
		String string = new String(solution).trim();

		
		if (string.length() == 1) {
			if (oneLetter.contains(string) && !solutions.contains(string)) {
				totalPoints += points.get(level + 1);
				solutions.add(string);
				return true;
			}
		}
		if (string.length() == 2) {
			if (twoLetter.contains(string) && !solutions.contains(string)) {
				totalPoints += points.get(level + 1);
				solutions.add(string);
				return true;
			}
		}
		if (string.length() > 2)// not one character string
			if (dict.containsKey(string.substring(0, 3))) { // dict contains start of string
				if (dict.get(string.substring(0, 3)).contains(string)) { // start of string contains
					if (!solutions.contains(string)) {
						totalPoints += points.get(level + 1);
						solutions.add(string);
					}
				} else {
					ArrayList<String> a = dict.get(string.substring(0, 3));
					for (String s : a) {
						if (s.contains(string))
							return true;
					}
					return false;
				}
			} else {
				return false;
			}
		return true;
	}

	private void readDictionaryFromFile(String fileName) {
		ArrayList<String> aux = new ArrayList<>();
		String c = "";
		int i = 0;
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(fileName));
			while (reader.ready()) {
				String line = reader.readLine().toLowerCase();
				if (line.length() == 1) {
					oneLetter.add(line);
				} else if (line.length() == 2) {
					twoLetter.add(line);
				} else {
					if (i == 0) {
						i++;
						c = line.substring(0, 3);
					}
					if (line.substring(0, 3).equals(c)) {
						aux.add(line);
					} else {
						dict.put(c, aux);
						c = line.substring(0, 3);
						aux = new ArrayList<>();
						aux.add(line);
					}
				}
			} // add last
			dict.put(c, aux);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	private void readTableFromFile(String tableFileName) {
		BufferedReader reader = null;
		int aux = -1;
		try {
			reader = new BufferedReader(new FileReader(tableFileName));
			while (reader.ready()) {
				if (aux == -1) {
					size = Integer.parseInt(reader.readLine());
					letters = new Character[size][size];
					visited = new boolean[size][size];
					// solution = new char[size];
					solution = new char[MAX_LENGTH_WORD > (size * size) ? (size * size) : MAX_LENGTH_WORD];
					aux++;
				} else {
					String line = reader.readLine();
					String[] temp = line.split(" ");
					for (int i = 0; i < temp.length; i++) {
						letters[aux][i] = temp[i].toLowerCase().toCharArray()[0];
					}
					aux++;
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
