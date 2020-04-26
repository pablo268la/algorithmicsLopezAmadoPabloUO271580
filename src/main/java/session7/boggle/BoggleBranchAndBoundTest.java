package session7.boggle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoggleBranchAndBoundTest {

	@Test
	public void testCase01() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";
		String tableFileName = "src/main/java/session7/boggle/table01.txt";
				
		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 1, 1);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();
		
		assertEquals(null, ((BoggleNode) game.getBestNode()));
	}
	
	@Test
	public void testCase02() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";
		String tableFileName = "src/main/java/session7/boggle/table02.txt";

		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 3, 3);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();

		assertEquals(9, ((BoggleNode) game.getBestNode()).aux().length());
		assertEquals("p - u - s - p - u - s - p - u - s - ", game.getBestNode().toString());
	}

	@Test
	public void testCase03() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";
		String tableFileName = "src/main/java/session7/boggle/table03.txt";

		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 3, 3);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();

		assertEquals(12, ((BoggleNode) game.getBestNode()).aux().length());
		assertEquals("d - u - b - s - d - u - b - s - d - u - b - s - ", game.getBestNode().toString());
	}
	
	@Test
	public void testCase04() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";
		String tableFileName = "src/main/java/session7/boggle/table04.txt";
				
		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 2, 2);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();
		
		assertEquals(8, ((BoggleNode) game.getBestNode()).aux().length());	}
	
	@Test
	public void testCase05() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";
		String tableFileName = "src/main/java/session7/boggle/table05.txt";
				
		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 3, 3);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();
		
		assertEquals(21, ((BoggleNode) game.getBestNode()).aux().length());
	}
	
	@Test
	public void testCase15() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";
		String tableFileName = "src/main/java/session7/boggle/table15.txt";
				
		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 10, 3);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();
		
		assertEquals(8, ((BoggleNode) game.getBestNode()).aux().length());
	}
	
	@Test
	public void testCase46() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";
		String tableFileName = "src/main/java/session7/boggle/table46.txt";
				
		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 2, 2);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();
		
		assertEquals(16, ((BoggleNode) game.getBestNode()).aux().length());
	}
	
	@Test
	public void testCase100() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary100.txt";
		String tableFileName = "src/main/java/session7/boggle/table100.txt";
		
		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 4, 3);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();
		
		assertEquals(0, ((BoggleNode) game.getBestNode()).aux().length());
	}
	
	@Test
	public void testCase205() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary100.txt";
		String tableFileName = "src/main/java/session7/boggle/table205.txt";
				
		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 4, 3);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();
		
		assertEquals(0, ((BoggleNode) game.getBestNode()).aux().length());
	}
	
	@Test
	public void testCase205_2() {
		String dictionaryFileName = "src/main/java/session7/boggle/dictionary80368.txt";
		String tableFileName = "src/main/java/session7/boggle/table205.txt";
				
		BoggleNode problem = new BoggleNode(dictionaryFileName, tableFileName, 2, 2);
		BoggleBranchAndBound game = new BoggleBranchAndBound(problem);

		problem.printTable();
		System.out.println();
		game.branchAndBound(problem);
		game.printSolutionTrace();
		
		assertEquals(0, ((BoggleNode) game.getBestNode()).aux().length());
	}

}
