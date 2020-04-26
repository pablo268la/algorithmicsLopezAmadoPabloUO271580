package session7.boggle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import session7.utils.Node;

public class BoggleNode extends Node {

	protected int[][] repited;
	protected Character[][] letters;
	protected int size;
	protected int totalPoints;
	private int x;
	private int y;
	private int concatenated;
	private int repitedCell;
	private String wordClean = "";
	private boolean first = false; // for at first iteration be able to take the (0,0)

	protected char[] solution;
	protected List<String> solutions = new ArrayList<String>();

	protected HashMap<String, ArrayList<String>> dict = new HashMap<>();
	protected ArrayList<String> oneLetter = new ArrayList<>();
	protected ArrayList<String> twoLetter = new ArrayList<>();

	protected final static int MAX_LENGTH_WORD = 10;

	protected List<Character> lettersList = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

	public List<Node> AddAll() {
		List<Node> aux = new ArrayList<>();
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				BoggleNode n = new BoggleNode(solution, size, i, j, letters, repited, oneLetter, twoLetter, dict, parentID,
						depth, concatenated, repitedCell, wordClean, true);
				n.setHeuristic(0);
				n.visit(i, j);
				aux.add(n);
			}
		return aux;

	}

	public String aux() {
		return String.valueOf(solution).trim();
	}

	public void setTable(Character[][] aux) {
		letters = aux;
	}

	public BoggleNode(String dictionaryFileName, String tableFileName, int concatenated, int repitedCell) {
		readDictionaryFromFile(dictionaryFileName);
		readTableFromFile(tableFileName);
		solution = new char[MAX_LENGTH_WORD * concatenated];
		repited = new int[size][size];
		this.concatenated = concatenated;
		this.repitedCell = repitedCell;
		this.first = true;
	}

	public BoggleNode(String dictionaryFileName, int tableSize, int concatenated, int repitedCell) {
		readDictionaryFromFile(dictionaryFileName);
		this.size = tableSize;
		this.letters = new Character[tableSize][tableSize];
		this.repited = new int[tableSize][tableSize];
		this.solution = new char[MAX_LENGTH_WORD * concatenated];
		this.concatenated = concatenated;
		this.repitedCell = repitedCell;
		this.first = true;
		fillRandomLetters();
	}

	private void fillRandomLetters() {
		for (int i = 0; i < letters.length; i++) {
			for (int j = 0; j < letters[0].length; j++) {
				Collections.shuffle(lettersList);
				letters[i][j] = lettersList.get(0);
			}
		}
	}

	public BoggleNode(char[] sol, int size, int i, int j, Character[][] letters, int[][] repited, ArrayList<String> oneLetter,
			ArrayList<String> twoLetter, HashMap<String, ArrayList<String>> dict, UUID parentID, int depthA,
			int concatenated, int repitedCell, String wordClean, boolean first) {

		this.solution = sol.clone();
		this.x = i;
		this.y = j;
		this.size = size;
		this.letters = letters;
		this.repited = copyRepited(repited);
		this.oneLetter = oneLetter;
		this.twoLetter = twoLetter;
		this.dict = dict;
		this.parentID = parentID;
		this.depth = depthA;
		if (!first)
			this.solution[depth] = letters[x][y];
		this.concatenated = concatenated;
		this.repitedCell = repitedCell;
		this.wordClean = wordClean;
		calculateHeuristicValue();
		this.repited[x][y]++;
		this.first = false;

	}

	private int[][] copyRepited(int[][] visit) {
		int[][] aux = new int[visit.length][visit.length];

		for (int i = 0; i < visit.length; i++)
			for (int j = 0; j < visit.length; j++)
				aux[i][j] = visit[i][j];

		return aux;
	}

	@Override
	public void calculateHeuristicValue() {
		int counter = 0;
		if (!prune())
			heuristicValue = Integer.MAX_VALUE;
		else {
			for (int i = 0; i < solution.length; i++)
				if (solution[i] != '\u0000')
					counter++;
				else
					break;
			heuristicValue = counter;
		}
	}

	private boolean prune() {
		String string = new String(solution).trim();

		int reps = getRepetitions(String.valueOf(solution));
		if (reps == 1 || reps == -1 || reps == concatenated)
			return true;

		if (x < 0 || x >= size || y < 0 || y >= size)
			return false;
		if (string.length() == 1) {
			if (oneLetter.contains(string)) {
				return true;
			}
		}
		if (string.length() == 2) {
			if (twoLetter.contains(string)) {
				return true;
			}
		}
		if (string.length() > 2)
			if (dict.containsKey(string.substring(0, 3))) {
				if (dict.get(string.substring(0, 3)).contains(string)) {
					return true;
				} else if (getRepetitions(string) == -1) {
					return true;
				} else {
					ArrayList<String> a = dict.get(string.substring(0, 3));
					for (String s : a)
						if (s.contains(string))
							return true;

					return false;
				}
			} else {
				return false;
			}
		return true;
	}

	private void visit(int i, int j) {
		this.solution[this.depth] = letters[i][j];
	}

	private int getRepetitions(String string) {
		string = string.trim();
		if (string.equals(wordClean))
			return 1;
		if (wordClean != null && string.startsWith(wordClean)) {
			String[] aux = string.split("(?<=\\G.{" + wordClean.length() + "})");
			for (String a : aux) {
				if (!wordClean.startsWith(a))
					return 0;
			}
			if (string.length() == wordClean.length() * concatenated)
				return concatenated;
			else
				return -1;
		}
		return 0;
	}

	@Override
	public ArrayList<Node> expand() {
		if (depth+1 >= solution.length)
			return new ArrayList<>();

		ArrayList<Node> result = new ArrayList<>();
		BoggleNode temp;

		for (int i = x - 1; i < x + 2; i++)
			for (int j = y - 1; j < y + 2; j++) {
				if (!(i < 0 || i >= size || j < 0 || j >= size) && repited[i][j] < repitedCell) {
					if (this.x != i || this.y != j || first) {
						temp = new BoggleNode(solution, size, i, j, letters, repited, oneLetter, twoLetter, dict, ID,
								depth + 1, concatenated, repitedCell, wordClean, false);
						result.add(temp);
					}
				}
			}

		return result;
	}

	@Override
	public boolean isSolution() {
		if (getHeuristicValue() == Integer.MAX_VALUE)
			return false;
		String string = new String(solution).trim();

		if (getRepetitions(string) == concatenated)
			return true;

		if (string.length() == 1) {
			if (oneLetter.contains(string)) {
				wordClean = string;
				solutions.add(string);
				oneLetter.remove(string);
				return true;
			}
		}
		if (string.length() == 2) {
			if (twoLetter.contains(string)) {
				wordClean = string;
				solutions.add(string);
				twoLetter.remove(string);
				return true;
			}
		}
		if (string.length() > 2)
			if (dict.containsKey(string.substring(0, 3))) {
				if (dict.get(string.substring(0, 3)).contains(string)) {
					wordClean = string;
					solutions.add(string);
					dict.get(string.substring(0, 3)).remove(string);
					return true;
				}
			}
		return false;
	}

	public void printTable() {
		for (int i = 0; i < letters[0].length; i++) {
			for (int j = 0; j < letters[0].length; j++) {
				System.out.print(letters[i][j] + " ");
			}
			System.out.println();
		}
	}

	@Override
	public String toString() {
		String aux = "";
		for (char c : solution)
			if (c != '\u0000')
				aux += (String.valueOf(c) + " - ");
		return aux;
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

	public int getRepetitions() {
		return getRepetitions(String.valueOf(solution));
	}

	private void setHeuristic(int a) {
		heuristicValue = a;
	}

	public int getConcatenated() {
		return concatenated;
	}
}
