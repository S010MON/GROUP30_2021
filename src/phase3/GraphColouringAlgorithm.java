package phase3;

public abstract class GraphColouringAlgorithm {
    
	/**
	 * Computes the chromatic number of a graph
	 * @param e	Array of edges.
	 * @param m	Number of vertices.
	 * @param n Number of edges.
     * @param fileName Number of edges.
	 * @return The chromatic number
	 */
	public abstract int solve(ColEdge[] e, int m, int n, String fileName);

	protected Bound bound;
	
	public enum Bound {
		UPPER, LOWER, EXACT;
	}

	public Bound getBound() {
		return bound;
	}

}
