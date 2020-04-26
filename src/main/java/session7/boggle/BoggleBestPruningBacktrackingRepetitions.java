package session7.boggle;

import java.util.ArrayList;

import session6.boggle.Boggle;

public class BoggleBestPruningBacktrackingRepetitions extends Boggle {

	private static final int NEED_TO_CHECK_REPETITIONS = -2;
	private int repeatCell;
	private int concatenated;
	private int solutionPoints = 0;
	private int[][] repeatedVisits;
	private String wordClean;
	private String bestSolution = "";

	public BoggleBestPruningBacktrackingRepetitions(String dictionaryFileName, int numberOfTimesAWordCanBeConcanetaded,
			int numberOfTimesACellCanBeRepeated, String tableFileName) {
		super(dictionaryFileName, tableFileName);
		this.concatenated = numberOfTimesAWordCanBeConcanetaded;
		this.repeatCell = numberOfTimesACellCanBeRepeated;
		this.repeatedVisits = new int[size][size];
		solution = new char[10*concatenated];
	}

	public BoggleBestPruningBacktrackingRepetitions(String dictionaryFileName, int numberOfTimesAWordCanBeConcanetaded,
			int numberOfTimesACellCanBeRepeated, int size) {
		super(dictionaryFileName, size);
		this.concatenated = numberOfTimesAWordCanBeConcanetaded;
		this.repeatCell = numberOfTimesACellCanBeRepeated;
		this.repeatedVisits = new int[size][size];
		solution = new char[10*concatenated];
	}
	
	public void setTable(Character[][] aux) {
		letters = aux;
	}

	@Override
	public long getTotalPoints() {
		return solutionPoints;
	}

	public String getBestSolution() {
		return bestSolution;
	}

	@Override
	protected void backtracking(int level, int i, int j) {
		if (level >= solution.length)
			return;
		if (i < 0 || i >= size || j < 0 || j >= size)
			throw new IndexOutOfBoundsException();

		visited[i][j] = true;
		repeatedVisits[i][j]++;
		solution[level] = letters[i][j];

		if (!checkIfSolution(level)) {
			visited[i][j] = false;
			repeatedVisits[i][j]--;
			solution[level] = '\u0000';
			return;
		}

		for (int auxI = (i - 1); auxI < (i + 2) && auxI < size; auxI++)
			for (int auxJ = (j - 1); auxJ < (j + 2) && auxJ < size; auxJ++)
				if (!(auxI < 0 || auxJ < 0) && repeatedVisits[auxI][auxJ] < repeatCell) {
					if (auxI == i && auxJ == j)
						continue;

					backtracking(level + 1, auxI, auxJ);
				}

		visited[i][j] = false;
		repeatedVisits[i][j]--;
		solution[level] = '\u0000';

	}

	@Override
	protected boolean checkIfSolution(int level) {
		String string = new String(solution).trim();

		if (string.length() == 1) {
			if (oneLetter.contains(string)) {
				solutions.add(string);
				wordClean = string;
				calculatePoints(string, NEED_TO_CHECK_REPETITIONS);
				oneLetter.remove(string);
				return true;
			}
		}
		if (string.length() == 2) {
			if (twoLetter.contains(string)) {
				solutions.add(string);
				wordClean = string;
				calculatePoints(string, NEED_TO_CHECK_REPETITIONS);
				twoLetter.remove(string);
				return true;
			}
		}

		if (string.length() > 2)// not one character string
			if (dict.containsKey(string.substring(0, 3))) { // dict contains start of string [ dictionary -> all bef rea
															// ...]
				if (dict.get(string.substring(0, 3)).contains(string)) { // start of string array contains the string [
																			// rea -> ready reaction ...]
					wordClean = string;
					solutions.add(string);
					calculatePoints(string, NEED_TO_CHECK_REPETITIONS);
					dict.get(string.substring(0, 3)).remove(string);

				} else { // if not contains the full word
					ArrayList<String> a = dict.get(string.substring(0, 3));
					for (String s : a) {
						if (s.contains(string))
							return true;
					}

					int rep = getRepetitions(string);
					if (rep == 0)
						return false;
					else if (rep == -1) {
						return true;
					} else {
						calculatePoints(string, rep);
						return true;
					}
				}
			} else {
				int rep = getRepetitions(string);
				if (rep == -1) {
					return true;
				} else if (rep == concatenated) {
					calculatePoints(string, rep);
					return true;
				}
				return false;
			}
		return true;
	}

	private int getRepetitions(String string) {
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

	private void calculatePoints(String string, int k) {
		if (k == NEED_TO_CHECK_REPETITIONS) // get the repetitions if not know them
			k = getRepetitions(string);
		if (k == -1) // word that can be concatenated but still not complete
			return;
		if (k == 1) { // normal word
			totalPoints += (string.length() * k + Math.pow(string.length(), k));
			if ((string.length() * k + Math.pow(string.length(), k)) > solutionPoints) {
				solutionPoints = (int) (string.length() * k + Math.pow(string.length(), k));
				bestSolution = string;
			}
			return;
		}
		if (k > 1 && !solutions.contains(string)) { // concatenated word
			solutions.add(string);
			solutions.remove(wordClean);
			totalPoints += (wordClean.length() * k + Math.pow(wordClean.length(), k));
			totalPoints -= (wordClean.length() * 1 + Math.pow(wordClean.length(), 1));
			if ((wordClean.length() * k + Math.pow(wordClean.length(), k)) > solutionPoints) {
				solutionPoints = (int) (wordClean.length() * k + Math.pow(wordClean.length(), k));
				bestSolution = string;
			}
			return;
		}
	}
}
