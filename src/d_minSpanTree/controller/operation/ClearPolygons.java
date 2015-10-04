package d_minSpanTree.controller.operation;

import java.util.ArrayList;

import d_minSpanTree.model.GraphModelInterface;
import d_minSpanTree.model.Shape;

public class ClearPolygons implements UndoableGraphOperation {

	private ArrayList<ArrayList<Shape>> polygonStack = new ArrayList<ArrayList<Shape>>();

	@Override
	public void execute(GraphModelInterface gmi) {
		polygonStack.add(gmi.getPolygons());
		gmi.getPolygons().clear();
	}

	@Override
	public String getName() {
		return "Clear Polygons";
	}

	@Override
	public void undo(GraphModelInterface gmi) {
		ArrayList<Shape> polygons = polygonStack.get(polygonStack.size() - 1);
		polygonStack.remove(polygonStack.size() - 1);

		gmi.getPolygons().clear();
		gmi.getPolygons().addAll(polygons);
	}

}
