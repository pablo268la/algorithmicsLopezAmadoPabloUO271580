package session7.boggle;

import java.util.ArrayList;
import java.util.List;

import session7.utils.BranchAndBound;
import session7.utils.Node;

public class BoggleBranchAndBound extends BranchAndBound {

	public BoggleBranchAndBound(BoggleNode board) {
		rootNode = board;
	}

	@Override
	public void branchAndBound(Node rootNode) {
		pruneLimit = 0;// rootNode.initialValuePruneLimit();

		List<Node> firstNodes = ((BoggleNode) rootNode).AddAll();
		for (int i = 0; i < firstNodes.size(); i++)
			ds.insert(firstNodes.get(i));

		while (!ds.empty() && ds.estimateBest() <= pruneLimit) {
			Node node = ds.extractBestNode();

			ArrayList<Node> children = node.expand();

			for (Node child : children) {
				int cost = child.getHeuristicValue();
				if (child.isSolution()) {
					if (cost >= pruneLimit) {
						pruneLimit = cost;
						ds.insert(child);
					}
					int reps = ((BoggleNode) child).getRepetitions();
					if (reps == 1 || reps == ((BoggleNode) child).getConcatenated())
						bestNode = child;
				} else if (cost >= pruneLimit && cost != Integer.MAX_VALUE) {
					ds.insert(child);
					pruneLimit = cost;
				}
			}
		} // while
	}
}
