package d_minSpanTree.controller.operation;

import java.util.ArrayList;

import d_minSpanTree.model.Edge;
import d_minSpanTree.model.GraphModelInterface;

public class ClearEdges implements UndoableGraphOperation {

	private ArrayList<ArrayList<Edge>> edgeStack = new ArrayList<ArrayList<Edge>>();
	private ArrayList<ArrayList<Edge>> displayStack = new ArrayList<ArrayList<Edge>>();

	@Override
	public void execute(GraphModelInterface gmi) {
		edgeStack.add(gmi.getEdges());
		displayStack.add(gmi.getDisplayEdges());

		gmi.getEdges().clear();
		gmi.getDisplayEdges().clear();
	}

	@Override
	public String getName() {
		return "Clear Edges";
	}

	@Override
	public void undo(GraphModelInterface gmi) {
		ArrayList<Edge> lastEdges = edgeStack.get(edgeStack.size() - 1);
		edgeStack.remove(edgeStack.size() - 1);

		gmi.getEdges().clear();
		gmi.getEdges().addAll(lastEdges);

		ArrayList<Edge> lastDispEdges = displayStack.get(displayStack.size() - 1);
		displayStack.remove(displayStack.size() - 1);

		gmi.getDisplayEdges().clear();
		gmi.getDisplayEdges().addAll(lastDispEdges);

	}

}
