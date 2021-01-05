package phase3;

import java.util.Stack;;

/** -------------------------------------------------------
 * A class that performs a Depth First Search on a ColEdge graph and checks if any loops occur in the graph.
 * If no loops are found it can be inferred that the graph is 2-Colourable
 * @author L Debnath
 * @since 5 Jan 21
 */
public class DepthFirstSearch 
{
	private final boolean DEBUG = false;
	
	private boolean[] visited;
	private boolean[][] adjacent;
	private boolean loop = false;
	
	/** -------------------------------------------------------
	 * Builds the adjacency matrix and conducts a recursive search of the graph.
	 * @param E - The set of edges of the graph
	 * @param n - The number of nodes within the graph
	 */
	public DepthFirstSearch(ColEdge[] E, int n)
	{
		visited = new boolean[n+1];			// n+1 due to discounting the 0 node
		adjacent = new boolean[n+1][n+1];  
		
		/* Create the Adjacency matrix */
		for(ColEdge e: E)
		{
			adjacent[e.v][e.u] = true;
			if(DEBUG) {System.out.println(e.v + " -> " + e.u);}
		}
		
		/* Recursive call that starts the search from node 1 */
		search(adjacent, 1);
		if(DEBUG) {System.out.println("Loop " + loop);}
	}
	
	/** -------------------------------------------------------
	 * A recursive search algorithm that explores down each branch until it finds a leaf node
	 * @param adjacent - The matrix of adjacent nodes
	 * @param n - The current node
	 */
	public void search( boolean[][] adjacent, int n)
	{
		/* Set the current node to visited */
		visited[n] = true;
		Stack<Integer> stack = new Stack<Integer>();
		
		/* Check each adjacent node to n */
		for(int m = 1; m < adjacent[n].length; m++)
		{
			/* If a visited adjacent node is found that is not the parent */
			if( adjacent[n][m] && visited[m] && (m != n))
				loop = true;
			/* If an unvisited adjacent node is found, add to the stack */
			if( adjacent[n][m] && !visited[m] )
				stack.push(m);
		}
		
		/* Work through the stack of adjacent nodes, searching down each one */
		while(!stack.isEmpty())
		{
			search(adjacent, stack.pop());
		}
	}
	
	/** -------------------------------------------------------
	 * Getter method to fetch loop
	 * @return
	 */
	public boolean containsLoop()
	{
		return loop;
	}
}
