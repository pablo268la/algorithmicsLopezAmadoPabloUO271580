package session4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Salesman {
	private int nNodes; // number of nodes of the graph
	private int[][] matrix; // graph adjacency matrix
	private int[] sol; // solution path from the source node to the source node again

	public static void main(String[] args) {
	}

	/**
	 * Constructor that loads the graph from a file
	 * 
	 * @param fileName
	 *            File name in which the data is contained
	 */
	public Salesman(String fileName) {
		matrix = createMatrixFromFile(fileName);
		sol = new int[nNodes + 1]; // the last movement to the source
	}

	/**
	 * Constructor that creates a random graph
	 * 
	 * @param nNodes
	 *            Number of nodes of the graph
	 * @param max
	 *            Maximum value for the random values (weights)
	 */
	public Salesman(int nNodes, int max) {
		this.nNodes = nNodes;
		matrix = createMatrix(nNodes, max);
		sol = new int[nNodes + 1]; // the last movement to the source
	}

	/**
	 * It generates a symmetrical triangular matrix with respect to the main
	 * diagonal.
	 * 
	 * @param size
	 *            Size of the matrix
	 * @param max
	 *            Maximum value for the random values (weights)
	 **/
	private int[][] createMatrix(int size, int max) {
		int[][] elements = new int[size][size];
		Random r = new Random();

		// we create a symmetric array for an adjacency matrix of an undirected graph
		for (int i = 0; i < size; i++)
			for (int j = i; j < size; j++)
				if (i == j)
					elements[i][j] = Integer.MAX_VALUE; // there is no path
				else {
					elements[i][j] = r.nextInt(max) + 1; // values between 1 and max
					elements[j][i] = elements[i][j];
				}
		return elements;
	}

	/**
	 * Load the integer array values from a file
	 * 
	 * @param fileName
	 *            File name in which the data is contained
	 **/
	private int[][] createMatrixFromFile(String fileName) {
		BufferedReader file = null;
		String line;
		int[][] elements = null;

		try {
			// we open the text file
			file = new BufferedReader(new FileReader(fileName));
			line = file.readLine();
			// the first line contains the number of elements
			nNodes = Integer.parseInt(line);
			// we create a matrix of the corresponding size
			elements = new int[nNodes][nNodes];
			for (int i = 0; i < nNodes; i++) {
				line = file.readLine();
				String values[] = line.split("\t");
				for (int j = 0; j < nNodes; j++) {
					elements[i][j] = Integer.parseInt(values[j]);
					if (elements[i][j] == 0)
						elements[i][j] = Integer.MAX_VALUE; // there is no path
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
		} catch (IOException e) {
			System.out.println("File reading error: " + fileName);
		}
		return elements;
	}

	/**
	 * Prints the matrix with the costs from the graph
	 */
	public void printMatrix() {
		for (int i = 0; i < nNodes; i++) {
			for (int j = 0; j < nNodes; j++) {
				if (matrix[i][j] == Integer.MAX_VALUE)
					System.out.print("INF\t");
				else
					System.out.print(matrix[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Prints the solution path
	 */
	public void printSolution() {
		for (int i = 0; i < sol.length; i++) {
			System.out.print(sol[i]);
			if (i < sol.length - 1)
				System.out.print(" -> ");
		}
		System.out.println("\n");
	}

	/**
	 * Calculate the Hamilton cycle. The heuristic is to get the lowest cost edge
	 * from current node to one of the no connected nodes
	 * 
	 * @param sourceNode
	 *            The starting node
	 * @return The cost of the cycle from the source to itself iterating through all
	 *         the other nodes once
	 */
	public int greedy1(int sourceNode) {
		int cost = 0;
		boolean[] bools = new boolean[nNodes];
		sol[0] = sourceNode;
		bools[sourceNode] = true;
		int tempMinCost = Integer.MAX_VALUE;
		int tempMinNode = 0;

		for (int i = 1; i < nNodes; i++) {

			tempMinCost = Integer.MAX_VALUE;
			tempMinNode = 0;
			for (int j = 0; j < nNodes; j++) {
				if (matrix[sourceNode][j] < tempMinCost && !bools[j]) {
					tempMinCost = matrix[sourceNode][j];
					tempMinNode = j;
				}
			}
			sol[i] = tempMinNode;
			cost += tempMinCost;
			sourceNode = tempMinNode;
			bools[tempMinNode] = true;
		}
		cost += matrix[tempMinNode][0];
		sol[sol.length - 1] = sol[0];
		return cost;

	}

	/**
	 * Calculate the Hamilton cycle. The heuristic is to get the lowest cost edge of
	 * the whole graph, checking that no node has more than two edges, and that no
	 * cycles are created until the end
	 * 
	 * @return The cost of the cycle from the source to itself iterating through all
	 *         the other nodes once
	 */
	public int greedy2() {
		int[] conexions = new int[nNodes];
		Component component = new Component(nNodes);
		List<Edge> costs = new ArrayList<Edge>();
		List<Edge> temSol = new ArrayList<Edge>();
		int cost = 0;

		addConexions(costs);
		Collections.sort(costs);
		sortFirst(costs);
		cost = addCosts(conexions, component, costs, temSol, cost);
		cost = addLastCost(conexions, temSol, cost);

		reconstructPath(temSol, conexions);
		return cost;
	}

	private void addConexions(List<Edge> costs) {
		int lastCost = -1;
		for (int i = 1; i < nNodes; i++)
			for (int j = 0; j < i; j++)
				if (i != j && matrix[i][j] != lastCost) {
					costs.add(new Edge(j, i, matrix[i][j]));
					lastCost = matrix[i][j];
				}
	}

	private int addLastCost(int[] conexions, List<Edge> temSol, int cost) {
		int t = 0;
		int aux = 0;
		for (int i = 0; i < conexions.length; i++) {
			if (conexions[i] == 1 && t == 0) {
				aux = i;
				t++;
			} else if (conexions[i] == 1 && t == 1) {
				conexions[i]++;
				conexions[aux]++;
				temSol.add(new Edge(aux, i, matrix[aux][i]));
				cost += matrix[aux][i];
			}
		}
		return cost;
	}

	private int addCosts(int[] conexions, Component component, List<Edge> costs, List<Edge> temSol, int cost) {
		for (int i = 0; i < costs.size(); i++) {
			int target = costs.get(i).targetNode;
			int source = costs.get(i).sourceNode;
			if (conexions[source] < 2 && conexions[target] < 2
					&& component.getComponent(source) != component.getComponent(target)) {
				conexions[source]++;
				conexions[target]++;
				temSol.add(costs.get(i));
				cost += costs.get(i).cost;
				component.mergeComponents(source, target);
			}
		}
		return cost;
	}

	private void sortFirst(List<Edge> costs) {
		int i = 0;
		int minNode = Integer.MAX_VALUE;
		int last = 0;
		int n = 0;
		for (; i < costs.size() && n < 2; i++) {
			if (costs.get(i).sourceNode == 0) {
				if (costs.get(i).targetNode < minNode) {
					minNode = costs.get(i).targetNode;
					last = i;
				}
				n++;
			}
		}
		int j = last;
		while (j != 0) {
			Edge aux = costs.get(j - 1);
			costs.set(j - 1, costs.get(j));
			costs.set(j, aux);
			j--;
		}

	}

	private void reconstructPath(List<Edge> temSol, int[] conexions) {
		sortFirst(temSol);
		int source = temSol.get(0).sourceNode;
		int target = temSol.get(0).targetNode;
		for (int i = 0; i < temSol.size(); i++) {
			if (conexions[source] == 3) {
				sol[i] = target;
				conexions[target]++;
			} else {
				sol[i] = source;
				conexions[source]++;
			}
			boolean foundNext = false;
			for (int j = 0; j < temSol.size(); j++) {
				if (temSol.get(j).sourceNode == target && conexions[target] == 2
						&& temSol.get(j).targetNode != source) {
					source = target;
					target = temSol.get(j).targetNode;
					foundNext = true;
					break;
				}
			}
			if (!foundNext) {
				for (int j = 0; j < temSol.size(); j++) {
					if (temSol.get(j).targetNode == target && temSol.get(j).sourceNode != source) {
						source = target;
						target = temSol.get(j).sourceNode;
						foundNext = true;
						break;
					}
				}
			}
		}
		sol[sol.length - 1] = sol[0];
	}

	/**
	 * Returns the array with the solution calculated with greedy1 or greedy2
	 * 
	 * @return Array with the solution path
	 */
	public int[] getSol() {
		return sol;
	}

}
