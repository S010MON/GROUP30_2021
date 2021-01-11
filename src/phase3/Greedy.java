package phase3;

import java.util.Arrays;

public class Greedy extends GraphColouringAlgorithm
{

	public Greedy() {
		bound = Bound.UPPER;
	}

	private final boolean DEBUG = false;

	/**
	 * Computes the UPPER BOUND of a graph
	 * @param e	Array of edges.
	 * @param m	Number of vertices.
	 * @param n Number of edges.
     * @param fileName Number of edges.
	 * @return UPPER BOUND
	 */
	public int solve(ColEdge[] e, int m, int n, String fileName){
		e = ColEdge.copyEdges(e);
		System.out.println("Running Greedy");
		long start = System.nanoTime();
		int vertex = 0;
		int XG = 0;
		while(!complete(e)){ //call the method: complete

			int col = 0;
			boolean legal = false;
			while(!legal){ //call the method: legal

				ColEdge[] eCopy = Arrays.copyOf(e, e.length); //prepare another the same array
				addColour(eCopy, vertex, col); //give color to every vertex
				if(available(eCopy)){
					legal = true;
				}else{
					col++;
				}
			}

			addColour(e, vertex++, col);
			if(col > XG) // as there are n vertices, there might be numerous color, give the amount of color to XG
				XG = col;
		}
		System.out.println("Chromatic Number = " + (XG + 1));
		double time = (System.nanoTime()-start)/1000000.0;
		System.out.println("The time needed to perform this analysis was: " + time + " ms.\n");
		//Logger.logResults("Greedy", fileName , XG + 1, time);
		Colour.set(bound, XG + 1);
		return XG + 1;
	}


	private ColEdge[] addColour(ColEdge[] e, int vertex, int colour) //give every vertex a color
	{
		for(ColEdge edge: e)
		{
			if(edge.v == vertex)
				edge.colV = colour;
			if(edge.u == vertex)
				edge.colU = colour;
		}
		if(DEBUG) {System.out.println("Coloured V" + vertex + " colour " + colour);}
		return e;
	}


	private boolean available(ColEdge[] e) //check if the colors of the two adjacent vertices are the same
	{
		boolean out = true;
		for(int i = 0; i < e.length; i++)
		{
			if(!e[i].legal())
				out = false;
		}
		if(DEBUG) {System.out.println("Legal: " + out);}
		return out;
	}


	private boolean complete(ColEdge[] e) //as the beginning of all the program to decide when to finish it
	{
		boolean complete = true;
		for(int i = 0; i < e.length; i++)
		{
			if(e[i].colV == -1)
				complete = false;
			if(e[i].colU == -1)
				complete = false;
		}
		if(DEBUG) {System.out.println("complete: " + complete);}
		return complete;
	}
}

