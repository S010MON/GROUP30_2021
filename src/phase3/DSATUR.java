package phase3;


/**
 * An implementation of the DSatur algorithm an optimisation of the Greedy Algorithm, it will colour the most frequent vertex first.
 * Subsequently the next vertex is chosen by the most saturated (i.e. the vertex with most adjacent coloured vertices). 
 * If none exists the algorithm will revert back to the most frequent.
 *
 * @author L.Debnath
 * @version 1.0
 */

import java.util.Arrays;

public class DSATUR extends GraphColouringAlgorithm
{

	public DSATUR() {
		bound = Bound.UPPER;
	}
	
	/** -------------------------------------------------------
	 * Computes the UPPER BOUND of a graph
	 * @param e	Array of edges.
	 * @param m	Number of vertices.
	 * @param n Number of edges.
     * @param fileName Number of edges.
	 * @return UPPER BOUND
	 */
	public int solve(ColEdge[] e, int m, int n, String inputfile)
		{
			e = ColEdge.copyEdges(e);
			// Start the clock!
			// Colour the most frequent vertex
			colour(e, mostFreq(e, n), 0);
			int chromeNumb = 0;
			while(!complete(e))
			{
				int col = 0;
				int lastColoured = 0;
				boolean legal = false;
				int mostSat = mostSaturated(e, n);
				if(mostSat == lastColoured)
					mostSat = mostFreq(e, n);
				while(!legal)
				{
					// Find the next most saturated and colour it on a copy of the graph
					ColEdge[] eCopy = Arrays.copyOf(e, e.length);
					colour(eCopy, mostSat, col);
					
					// Check legality
					if(legal(eCopy))
						legal = true;		
					else
						col++;
				}
				// Commit colouring of copy to 'e' and record the last colour used
				colour(e, mostSat, col);
				lastColoured = col;
				if(col > chromeNumb)
					chromeNumb = col;
				
			}
			Colour.set(bound, chromeNumb + 1);
			return chromeNumb + 1;
		}
		
		/** -------------------------------------------------------
		 * A method that colours the vertices within the ColEdge objects 
		 * @param e 		- the array of edges
		 * @param vertex	- the vertex to be coloured
		 * @param colour	- the colour to be added
		 * @return ColEdge	- the amended array of edges
		 */
		private static ColEdge[] colour(ColEdge[] e, int vertex, int colour)
		{
			for(ColEdge edge: e)
			{
				if(edge.v == vertex)
					edge.colV = colour;
				if(edge.u == vertex)
					edge.colU = colour;
			}	
			return e;
		}
		
		/** -------------------------------------------------------
		 * A method that counts the saturation (number of adjacent vertices that have been coloured) 
		 * and returns the highest saturated.
		 * @param e  		- the edges for the graph
		 * @param vertices 	- the number of vertices
		 * @return int[] 	- the number of edges to each vertex
		 */	
		private static int mostSaturated(ColEdge[] e, int vertices)
		{
			int[] sat = new int[vertices+1];
			for(int i = 0; i < e.length; i++)
			{
				if(e[i].colU > -1 && e[i].colV == -1)
					sat[e[i].v]++;
				if(e[i].colV > -1 && e[i].colU == -1)
					sat[e[i].u]++;
			}
			
			int mostSat = 0;			
			for(int i = 0; i < sat.length; i++)
			{
				if(sat[i] > sat[mostSat])
					mostSat = i;
			}

			return mostSat;
		}
	
		/** -------------------------------------------------------
		 * A method that counts the frequency of all of the vertices and returns the count of edges to each vertex
		 * @param e  		- the edges for the graph
		 * @param vertices 	- the number of vertices
		 * @return int[] 	- the number of edges to each vertex
		 */
		private static int mostFreq(ColEdge[] e, int vertices)
		{
			int[] freq = new int[vertices+1];
			
			// Count how many edges each vertex has coming to it
			for(int i = 0; i < e.length; i++)
			{
				if(e[i].colV == -1)
					freq[e[i].v]++;
				if(e[i].colU == -1)
					freq[e[i].u]++;
			}		
			
			int mostFreq = 0;						
			for(int i = 0; i < freq.length; i++)
			{
				if(freq[i] > freq[mostFreq])
					mostFreq = i;
			}	
			return mostFreq;
		}
		
		/** -------------------------------------------------------
		 * Checks each edge for legality using a method within the ColEdge object
		 * @param e - ColEdge - Array of edges in the graph
		 * @return  - Boolean - {@code true} if the entire graph is legal 
		 */
		private static boolean legal(ColEdge[] e)
		{
			boolean legal = true;
			for(int i = 0; i < e.length; i++)
			{
				if(!e[i].legal())
					legal = false;
			}
			return legal;
		}
		
		/** -------------------------------------------------------
		 * Checks each edge values for colU and colV are not '-1'.  If the entire graph is coloured
		 * returns {@code true}
		 * @param e - ColEdge - Array of edges in the graph
		 * @return  - Boolean - If the entire graph has been coloured
		 */
		private static boolean complete(ColEdge[] e)
		{
			boolean complete = true;
			for(int i = 0; i < e.length; i++)
			{
				if(e[i].colV == -1)
					complete = false;
				if(e[i].colU == -1)
					complete = false;
			}
			return complete;
		}
}

