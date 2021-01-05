package phase3;


/**
 * A class that holds all of the information required for a graph
 * @author L.Debnath
 * @since 16 Nov 20
 * @version 3.0
 */

public class ColEdge 
{

	// Vertices U and V that the edge connects
	public int u;
	public int v;
	// Colours assigned to V and U, starts with -1 being blank
	public int colU = -1;
	public int colV = -1;

	/** -------------------------------------------------------
	 * 	Checks that V and U are not connected (-1 && -1 case ignored)
	 * @return boolean true if legal
	 */
	public boolean legal() 
	{
		boolean legal = true;
		if((colV != -1  || colU != -1) && colV == colU)
		{
			legal = false;
		}
		return legal;
	}

	public static boolean exists(ColEdge[] e, int from, int to)
	{
		boolean exists = false;
		for (ColEdge edge : e) {
			if (edge != null && edge.u == from && edge.v == to) exists = true;
		}
		return exists;
	}
	
	/**
	 * Make a deep copy of a ColEdge array
	 * 
	 * @param e - An array of edges
	 * @return - A deep copy of the array
	 */
	public static ColEdge[] copyEdges(ColEdge[] e)
	{
		ColEdge[] eCopy = new ColEdge[e.length];
		for(int i = 0; i < e.length; i++)
		{
			eCopy[i] = new ColEdge();
			eCopy[i].u = e[i].u;
			eCopy[i].v = e[i].v;
			eCopy[i].colU = e[i].colU;
			eCopy[i].colV = e[i].colV;
		}
		return eCopy;
	}
}
