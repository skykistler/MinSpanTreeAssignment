package d_minSpanTree.controller.operation.algorithm;

import java.util.ArrayList;

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
			// find all bad triangles (point is inside circumcircle of triangle)
			// add all edges to polygon that aren't shared by other bad
			// triangles
			// remove all bad triangles from triangulation
			// add polygon triangles to triangulation
		}

		// remove triangles that contain super-triangle vertex

		for (int i = 0; i < triangulation.size(); i++) {
			Vertex v0 = triangulation.get(i).getVertex(0);
			Vertex v1 = triangulation.get(i).getVertex(0);
			Edge e = new Edge(v0, v1);
			double dx = v1.getX() - v0.getX();
			double dy = v1.getY() - v0.getY();
			e.setWeight(dx * dx + dy * dy);
			gmi.getEdges().add(e);
		}

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
		public Vertex[] vertices = new Vertex[3];

		public void setVertex(int i, Vertex v) {
			vertices[i] = v;
		}

		public Vertex getVertex(int i) {
			return vertices[i];
		}
	}
	
	private boolean pointIsInCircumcircle(Triangle t, Vertex v) {
        double[][] m = new double[4][];
        for (int i = 0; i < 3; i++) {
            Vertex point = t.getVertex(i);
            double x = point.getX();
            double y = point.getY();
            double[] row = new double[] { x, y, (x*x)+(y*y), 1 };
            m[i] = row;
        }
        double x = v.getX();
        double y = v.getY();
        double[] row = new double[] { x, y, (x*x)+(y*y), 1 };
        m[3] = row;
        
        double determinant =
                ( m[0][0] * ( m[1][1] * ( ( m[2][2] * m[3][3] ) - ( m[2][3] * m[3][2] ) ) 
                           -  m[1][2] * ( ( m[2][1] * m[3][3] ) - ( m[2][3] * m[3][1] ) )
                           +  m[1][3] * ( ( m[2][1] * m[3][2] ) - ( m[2][2] * m[3][1] ) ))
                - m[0][1] * ( m[1][0] * ( ( m[2][2] * m[3][3] ) - ( m[2][3] * m[3][2] ) ) 
                           -  m[1][2] * ( ( m[2][0] * m[3][3] ) - ( m[2][3] * m[3][0] ) )
                           +  m[1][3] * ( ( m[2][0] * m[3][2] ) - ( m[2][2] * m[3][0] ) ))
                + m[0][2] * ( m[1][0] * ( ( m[2][1] * m[3][3] ) - ( m[2][3] * m[3][1] ) ) 
                           -  m[1][1] * ( ( m[2][0] * m[3][3] ) - ( m[2][3] * m[3][0] ) )
                           +  m[1][3] * ( ( m[2][0] * m[3][1] ) - ( m[2][1] * m[3][0] ) ))
                - m[0][3] * ( m[1][0] * ( ( m[2][1] * m[3][2] ) - ( m[2][2] * m[3][1] ) ) 
                           -  m[1][1] * ( ( m[2][0] * m[3][2] ) - ( m[2][2] * m[3][0] ) )
                           +  m[1][2] * ( ( m[2][0] * m[3][1] ) - ( m[2][1] * m[3][0] ) )));
        
        if (determinant > 0) return true;
        else return false;
    }
	
}
