package phase3;

import java.util.Stack;;

public class DepthFirstSearch 
{
	private final boolean DEBUG = true;
	private boolean[] visited;
	private boolean[][] adjacent;
	private boolean loop;
	
	public DepthFirstSearch(ColEdge[] E, int n)
	{
		visited = new boolean[n+1];
		adjacent = new boolean[n+1][n+1];  	// The matrix of all adjacent nodes
		
		
		for(ColEdge e: E)
		{
			adjacent[e.v][e.u] = true;		// Set adjacent nodes
			if(DEBUG) {System.out.println(e.v + " -> " + e.u);}
		}
		loop = false;
		
		search(adjacent, 1); 	// Recursive call starting from node 1
		
		System.out.println("Loop " + loop);
	}
	
	public void search( boolean[][] adjacent, int n)
	{
		visited[n] = true;
		Stack<Integer> stack = new Stack<Integer>();
		
		for(int m = 1; m < adjacent[n].length; m++)
		{
			if( adjacent[n][m] && visited[m] && (m != n))
				loop = true;
			if( adjacent[n][m] && !visited[m] )
				stack.push(m);
		}
		
		while(!stack.isEmpty())
		{
			search(adjacent, stack.pop());
		}
	}
	
	public boolean containsLoop()
	{
		return loop;
	}
}
