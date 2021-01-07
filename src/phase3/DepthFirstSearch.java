package phase3;

import java.util.Stack;

/** -------------------------------------------------------
 * A class that performs a Depth First Search on a ColEdge graph and checks if any loops occur in the graph.
 * If no loops are found it can be inferred that the graph is 2-Colourable
 * @author L.Debnath
 * @since 5 Jan 21
 */
public class DepthFirstSearch 
{
	private final boolean DEBUG = false;
	
	private boolean[] visited;
	private boolean[][] adjacent;
	private boolean tree;
	private boolean connected;
	private int noOfSubgraphs;
	private ColEdge[] graph;
	
	public DepthFirstSearch()
	{
		tree = true;
		connected = true;
		noOfSubgraphs = 1;
	}
	
	/** -------------------------------------------------------
	 * Conducts a recursive search of the graph to discover loops and sub-graphs
	 * @param E - The set of edges of the graph
	 * @param n - The number of nodes within the graph
	 * @return int - The number of sub-graphs
	 */
	public int run(ColEdge[] E, int n)
	{
		graph = E;
		visited = new boolean[n+1];			// n+1 due to discounting the 0 node
		adjacent = createMatrix(E, n);
		
		/* Find the first node and call a recursive search from there */
		int start = E[0].v;
		search(adjacent, start, 0, 0);		// 0 denotes no parent nodes
		
		/* If there are nodes left in the graph -> subgraph */
		if(unvisitedNodes() > 0)
		{
			connected = false;
			ColEdge[] subGraph = new ColEdge[E.length];
			
			/* find all unconnected parts of the graph and create a new subGraph */
			int i = 0;
			for(ColEdge e: E)
			{
				if(!visited[e.v])
					subGraph[i++] = e;
			}
			subGraph = trim(subGraph);
			
			/* Create a new Depth First Search and recursively call it */
			DepthFirstSearch dfs = new DepthFirstSearch();
			noOfSubgraphs = noOfSubgraphs + run(subGraph, n, visited);
			/* Check loop latch in sub graphs */
			if(!dfs.isTree())
				tree = false;
		}
		return noOfSubgraphs;
	}
	
	
	/** -------------------------------------------------------
	 *  Conducts a recursive search of the graph to discover loops and sub-graphs
	 * @param E 		- The set of edges of the graph
	 * @param n 		- The number of nodes within the graph
	 * @param visited 	- The array of previously visited nodes
	 * @return int - The number of sub-graphs
	 */
	public int run(ColEdge[] E, int n, boolean[] visited)
	{
		this.visited = visited;
		adjacent = createMatrix(E, n);
		noOfSubgraphs = 1;
		
		/* Find the first node and call a recursive search from there */
		int start = E[0].v;
		search(adjacent, start, 0, 0);		// 0 denotes no parent nodes
		
		/* If there are nodes left in the graph -> subgraph */
		if(unvisitedNodes() > 0)
		{
			connected = false;
			ColEdge[] subGraph = new ColEdge[E.length];
			
			/* find all unconnected parts of the graph and create a new subGraph */
			int i = 0;
			for(ColEdge e: E)
			{
				if(!visited[e.v])
					subGraph[i++] = e;
			}
			subGraph = trim(subGraph);
			
			/* Create a new Depth First Search and recursively call it */
			DepthFirstSearch dfs = new DepthFirstSearch();
			noOfSubgraphs = noOfSubgraphs + run(subGraph, n, visited);
			/* Check loop latch in sub graphs */
			if(dfs.isTree())
				tree = true;
		}
		return noOfSubgraphs;
	}
	
	
	/** -------------------------------------------------------
	 * Builds the adjacency matrix
	 * @param E
	 * @param n
	 * @return boolean[][] matrix
	 */
	private boolean[][] createMatrix(ColEdge[] E, int n)
	{
		boolean[][] A = new boolean[n+1][n+1];  // n+1 due to the 0 node not being used
		for(ColEdge e: E)
		{
			A[e.v][e.u] = true;					// Set bi-directional adjacency
			A[e.u][e.v] = true;
			if(DEBUG) {System.out.println(e.v + " - " + e.u);}
		}
		return A;
	}
	
	
	/** -------------------------------------------------------
	 * A recursive search algorithm that explores down each branch until it finds a leaf node
	 * @param adjacent 		- The matrix of adjacent nodes
	 * @param n 			- The current node
	 * @param parent 		- The parent node of the current node	
	 */
	public void search( boolean[][] adjacent, int n, int parent, int parentCol)
	{
		/* Set the current node to visited */
		visited[n] = true;
		Stack<Integer> stack = new Stack<Integer>();
		
		/* Check each adjacent node to n */
		for(int m = 1; m < adjacent[n].length; m++)
		{
			/* If a visited adjacent node is found that is not the parent */
			if( adjacent[n][m] && visited[m] && (m != n) && (m != parent))
				tree = false;
			/* If an unvisited adjacent node is found, add to the stack */
			if( adjacent[n][m] && !visited[m] )
				stack.push(m);
		}
		
		/* Set the colour of the node in the graph */
		int myCol;
		if(parentCol == 1)
			myCol = 0;
		else
			myCol = 1;
		setColour(n, myCol);
		
		/* Work through the stack of adjacent nodes, searching down each one */
		while(!stack.isEmpty())
		{
			search(adjacent, stack.pop(), n, myCol);
		}
	}

	
	/** -------------------------------------------------------
	 * Returns the next unvisited node
	 * @return integer index
	 */
	private int unvisitedNodes()
	{
		int unvisitedNodes = 0;
		for(int i = 1; i < visited.length; i++)
		{
			if(!visited[i])
				unvisitedNodes++;
		}
		return unvisitedNodes;
	}
	
	/** -------------------------------------------------------
	 * Trim a ColEdge array down to only non null values
	 * @return ColEdge[]
	 */
	private ColEdge[] trim(ColEdge[] E)
	{
		int trimTo = 0;
		for(int i = 0; i < E.length; i++)
		{
			if(E[i] != null)
				trimTo++;
		}
		
		ColEdge[] trimmed = new ColEdge[trimTo];
		for(int i = 0; i < trimmed.length; i++)
		{
			trimmed[i] = E[i];
		}
		return trimmed;
	}
	
	
	private void setColour(int vertex, int colour)
	{
		for(ColEdge e: graph)
		{
			if(e.v == vertex)
				e.colV = colour;
			if(e.u == vertex)
				e.colU = colour;
		}
	}
	
	public boolean checkGraph()
	{
		boolean valid = true;
		for(ColEdge e: graph)
		{
			if(!e.legal())
				valid = false;
		}
		return valid;
	}
		
	/** -------------------------------------------------------
	 * Getters
	 */
	public boolean isTree()
	{
		return tree;
	}
	
	public boolean connected()
	{
		return connected;
	}
	

}
