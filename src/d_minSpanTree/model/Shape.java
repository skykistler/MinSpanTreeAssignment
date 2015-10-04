package d_minSpanTree.model;

import java.util.ArrayList;

public class Shape {
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	private double opacity, red, green, blue;

	public Shape(double opacity, double red, double green, double blue) {
		this.opacity = opacity;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void addVertex(Vertex v) {
		for (Vertex p : vertices) {
			if (p.getX() == v.getX() && p.getY() == v.getY())
				return;
		}

		vertices.add(v);
	}

	public void addEdge(Edge e) {
		addVertex(e.getStart());
		addVertex(e.getEnd());
	}

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double o) {
		opacity = o;
	}

	public double[] getColor() {
		return new double[] { red, green, blue };
	}

}
