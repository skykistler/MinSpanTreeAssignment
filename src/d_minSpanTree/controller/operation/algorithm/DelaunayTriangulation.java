package d_minSpanTree.controller.operation.algorithm;

import java.util.ArrayList;
import java.util.Arrays;

import d_minSpanTree.model.Edge;
import d_minSpanTree.model.GraphModelInterface;
import d_minSpanTree.model.Vertex;

public class DelaunayTriangulation implements GraphAlgorithm {

	public void execute(GraphModelInterface gmi) {
		gmi.getEdges().clear();

		long startTime = System.nanoTime(); // Start the total timing

		// TODO: Implement Bowyer-Watson
		// http://en.wikipedia.org/wiki/Bowyer%E2%80%93Watson_algorithm

		double min_x = Double.POSITIVE_INFINITY;
		double max_x = Double.NEGATIVE_INFINITY;
		double min_y = Double.POSITIVE_INFINITY;
		double max_y = Double.NEGATIVE_INFINITY;
		for (Vertex v : gmi.getVertices()) {
			if (v.getX() < min_x)
				min_x = v.getX();
			if (v.getX() > max_x)
				max_x = v.getX();
			if (v.getY() < min_y)
				min_y = v.getY();
			if (v.getY() > max_y)
				max_y = v.getY();
		}
		min_x -= 1;
		min_y -= 1;
		double super_tri_height = (max_y - min_y) * 3;
		double super_tri_width = (max_x - min_x) * 3;

		Triangle super_triangle = new Triangle();
		super_triangle.setVertex(0, new Vertex("super", min_x, min_y));
		super_triangle.setVertex(1, new Vertex("super", super_tri_width, min_y));
		super_triangle.setVertex(2, new Vertex("super", min_x, super_tri_height));

		ArrayList<Triangle> triangulation = new ArrayList<Triangle>();
		triangulation.add(super_triangle);

		for (Vertex v : gmi.getVertices()) {
			ArrayList<Triangle> bad_triangles = new ArrayList<Triangle>();
			ArrayList<Edge> polygon = new ArrayList<Edge>();

			for (Triangle t : triangulation)
				if (inCircumcircle(t, v)) {
					bad_triangles.add(t);

					ArrayList<Edge> shared = new ArrayList<Edge>();
					for (Edge e : polygon) {
						for (int i = 0; i < 3; i++)
							if (e.compare(t.edges[i])) {
								shared.add(e);
								shared.add(t.edges[i]);
							}
					}
					polygon.addAll(Arrays.asList(t.edges));
					polygon.removeAll(shared);
				}

			triangulation.removeAll(bad_triangles);

			for (Edge e : polygon) {
				Triangle new_triangle = new Triangle();
				new_triangle.setVertex(0, e.getStart());
				new_triangle.setVertex(1, e.getEnd());
				new_triangle.setVertex(2, v);
				new_triangle.makeEdges();
				if (new_triangle.clockwise())
					new_triangle.flipOrder();
				triangulation.add(new_triangle);
			}
		}

		ArrayList<Triangle> super_triangles = new ArrayList<Triangle>();
		for (Triangle t : triangulation)
			for (Vertex v : t.vertices)
				if (v.getName().equals("super"))
					super_triangles.add(t);
		triangulation.removeAll(super_triangles);

		for (int i = 0; i < triangulation.size(); i++) {
			for (Edge e : triangulation.get(i).edges) {
				double dx = e.getEnd().getX() - e.getStart().getX();
				double dy = e.getEnd().getY() - e.getStart().getY();
				e.setWeight(dx * dx + dy * dy);
				gmi.getEdges().add(e);
			}
		}

		long endTime = System.nanoTime(); // Finish the total timing
		float timeElapsed = (endTime - startTime) / 1000000.0f; // milliseconds
		System.out.println("Edge creation O(f(nE,nV)???):" + timeElapsed);
	}

	public boolean inCircumcircle(Triangle t, Vertex v) {
		return false;
	}

	public String getName() {
		return "Delaunay Triangulation";
	}

	public boolean canLiveUpdate() {
		return true;
	}

	class Triangle {
		public Vertex[] vertices = new Vertex[3];
		public Edge[] edges = new Edge[3];
		public boolean[] shared_edges = new boolean[3];

		public void setVertex(int i, Vertex v) {
			vertices[i] = v;
		}

		public Vertex getVertex(int i) {
			return vertices[i];
		}

		public void makeEdges() {
			edges[0] = new Edge(vertices[0], vertices[1]);
			edges[1] = new Edge(vertices[1], vertices[2]);
			edges[2] = new Edge(vertices[2], vertices[0]);
		}

		public void flipOrder() {
			Vertex v0 = vertices[0];
			Vertex v1 = vertices[1];
			vertices[1] = v0;
			vertices[0] = v1;
		}

		public boolean clockwise() {
			double i = (vertices[1].getX() - vertices[0].getX()) * (vertices[1].getY() + vertices[0].getY());
			i += (vertices[2].getX() - vertices[1].getX()) * (vertices[2].getY() + vertices[1].getY());
			i += (vertices[0].getX() - vertices[2].getX()) * (vertices[0].getY() + vertices[2].getY());
			return i > 0;
		}
	}

}
