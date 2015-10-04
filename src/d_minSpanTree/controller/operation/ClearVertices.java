package d_minSpanTree.controller.operation;

import java.util.ArrayList;

import d_minSpanTree.model.GraphModelInterface;
import d_minSpanTree.model.Vertex;

public class ClearVertices implements UndoableGraphOperation {
	private ArrayList<ArrayList<Vertex>> vertexStack = new ArrayList<ArrayList<Vertex>>();

	@Override
	public void execute(GraphModelInterface gmi) {
		vertexStack.add(gmi.getVertices());
		gmi.getVertices().clear();
	}

	@Override
	public String getName() {
		return "Clear Vertices";
	}

	@Override
	public void undo(GraphModelInterface gmi) {
		ArrayList<Vertex> vertices = vertexStack.get(vertexStack.size() - 1);

		gmi.getVertices().clear();
		gmi.getVertices().addAll(vertices);
		vertexStack.remove(vertexStack.size() - 1);
	}

}
