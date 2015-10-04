package d_minSpanTree.controller.operation;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import d_minSpanTree.model.GraphModelInterface;
import d_minSpanTree.model.Vertex;
import d_minSpanTree.view.GraphViewer;

public class AddNRandomVertices implements UndoableGraphOperation {
	boolean reset;
	int nRandomVertices;
	Random rand = new Random(); // One can seed with a parameter variable here
	static int seed = (int) System.nanoTime(); // 1 for cool stuff
	ArrayList<Vertex> added = new ArrayList<Vertex>();

	public AddNRandomVertices(boolean reset, int nRandVert, Stack<UndoableGraphOperation> commandStack) {
		this.reset = reset;
		nRandomVertices = nRandVert;
		rand.setSeed(seed++);
	}

	public void execute(GraphModelInterface gmi) {
		// Start from no graph (Without this, it should be just an append of new
		// vertices.)
		if (reset) {
			gmi.getVertices().clear();
			gmi.getEdges().clear();
		}

		for (int i = 0; i < nRandomVertices; i++) {
			double xPos = rand.nextDouble() * (GraphViewer.windowWidth - 8) + 8;
			double yPos = rand.nextDouble() * (GraphViewer.windowHeight - 64) + 8;
			Vertex v = new Vertex("p" + gmi.getVertices().size(), xPos, yPos);

			// Vertex creation O(n)
			added.add(v);
			gmi.getVertices().add(v);
		}

		gmi.runAlgorithms();
	}

	public String getName() {
		return "Random 100";
	}

	public void undo(GraphModelInterface gmi) {
		for (Vertex v : added) {
			gmi.getVertices().remove(v);
		}
	}

}
