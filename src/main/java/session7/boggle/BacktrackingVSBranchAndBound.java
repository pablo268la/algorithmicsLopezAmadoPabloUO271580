package session7.boggle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingVSBranchAndBound {

	protected static List<Character> lettersList = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	protected static Character[][] letters;

	public static void main(String[] args) {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";

		int numberOfTimesAWordCanBeConcanetaded = 1;
		int numberOfTimesACellCanBeRepeated = 1;
		long t1, t2, t3, t4;

		for (int n = 1; n < Integer.MAX_VALUE; n *= 2) {
			letters = new Character[n][n];

			for (int i = 0; i < letters.length; i++) {
				for (int j = 0; j < letters[0].length; j++) {
					Collections.shuffle(lettersList);
					letters[i][j] = lettersList.get(0);
				}
			}

			BoggleNode problem = new BoggleNode(dictionaryFileName, n, numberOfTimesAWordCanBeConcanetaded,
					numberOfTimesACellCanBeRepeated);
			BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

			BoggleBestPruningBacktrackingRepetitions problem2 = new BoggleBestPruningBacktrackingRepetitions(
					dictionaryFileName, numberOfTimesAWordCanBeConcanetaded, numberOfTimesACellCanBeRepeated, n);

			problem.setTable(letters);
			problem2.setTable(letters);

			t1 = System.currentTimeMillis();
			problem2.findSolutions();
			t2 = System.currentTimeMillis();
			if (!problem2.getBestSolution().equals(""))
				System.out.println(String.format("BACKTRACKING -- Size: %d - Elapsed time: %s ms - Best solution: %s",
						n, t2 - t1, problem2.getBestSolution()));
			else
				System.out.println(String.format(
						"BACKTRACKING -- Size: %d - Elapsed time: %s ms - Best solution: NO SOLUTION THIS TIME", n,
						t2 - t1));

			t3 = System.currentTimeMillis();
			game.branchAndBound(problem);
			t4 = System.currentTimeMillis();
			if (((BoggleNode) game.getBestNode()) != null)
				System.out
						.println(String.format("BRANCH AND BOUND -- Size: %d - Elapsed time: %s ms - Best solution: %s",
								n, t4 - t3, ((BoggleNode) game.getBestNode()).aux()));
			else
				System.out.println(String.format(
						"BRANCH AND BOUND -- Size: %d - Elapsed time: %s ms - Best solution: NO SOLUTION THIS TIME", n,
						t4 - t3));
			System.gc();
		}
	}

}
