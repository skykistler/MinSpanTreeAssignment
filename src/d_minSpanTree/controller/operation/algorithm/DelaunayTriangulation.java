package d_minSpanTree.controller.operation.algorithm;

import java.util.ArrayList;

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
		super_triangle.setPoint(0, min_x, min_y);
		super_triangle.setPoint(1, super_tri_width, min_y);
		super_triangle.setPoint(2, min_x, super_tri_height);

		ArrayList<Triangle> triangulation = new ArrayList<Triangle>();
		triangulation.add(super_triangle);

		for (Vertex v : gmi.getVertices()) {
			// find all bad triangles (point is inside circumcircle of triangle)
			// add all edges to polygon that aren't shared by other bad
			// triangles
			// remove all bad triangles from triangulation
			// add polygon triangles to triangulation
		}

		// remove triangles that contain super-triangle vertex

		// for (int i0 = 0; i0 < gmi.getVertices().size(); i0++) {
		// for (int i1 = i0 + 1; i1 < gmi.getVertices().size(); i1++) {
		// Vertex v0 = gmi.getVertices().get(i0);
		// Vertex v1 = gmi.getVertices().get(i1);
		// Edge e = new Edge(v0, v1);
		// double dx = v1.getX() - v0.getX();
		// double dy = v1.getY() - v0.getY();
		// e.setWeight(dx * dx + dy * dy);
		// gmi.getEdges().add(e);
		// }
		// }

		long endTime = System.nanoTime(); // Finish the total timing
		float timeElapsed = (endTime - startTime) / 1000000.0f; // milliseconds
		System.out.println("Edge creation O(f(nE,nV)???):" + timeElapsed);
	}

	public String getName() {
		return "Delaunay Triangulation";
	}

	public boolean canLiveUpdate() {
		return true;
	}

	class Triangle {
		public double[][] points = new double[3][2];

		public void setPoint(int i, double x, double y) {
			points[i] = new double[] { x, y };
		}

		public double[] getPoint(int i) {
			return points[i];
		}
	}

}
