package phase3;

import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * A class that holds all of the information required for a graph
 * @author L.Debnath
 * @since 16 Nov 20
 * @version 2.0
 */

public class ColEdge 
{

	// Vertices U and V that the edge connects
	public int u;
	public int v;
	// Colours assigned to V and U, starts with -1 being blank
	public int colU = -1;
	public int colV = -1;
	// X and Y coordinates for the locations of vertices U and V
	public int xU = -1;
	public int yU = -1;
	public int xV = -1;
	public int yV = -1;

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

	public static double getMaxDistance(ColEdge[] G, double[][] V) {
		double maxDist = 0;
		for (ColEdge edge : G) {
			maxDist = Math.max(getDistance(V[edge.u][0],V[edge.u][1],V[edge.v][0],V[edge.v][1]),maxDist);
		}
		return maxDist;
	}

	public static double getAvgDistance(ColEdge[] G, double[][] V) {
		double dist = 0;
		for (ColEdge edge : G) {
			dist+= getDistance(V[edge.u][0],V[edge.u][1],V[edge.v][0],V[edge.v][1]);
		}
		return dist / G.length;
	}

	private static double getDistance(double x1, double y1, double x2, double y2) {
		return Point.distance(x1, y1, x2, y2);
	}

	public static int getIntersectCount(ColEdge[] G, double[][] V) {
		int intersects = 0;
		Line2D[] lines = new Line2D[G.length];
		int i = 0;
		for (ColEdge edge : G) {
			lines[i] = new Line2D.Double(V[edge.u][0],V[edge.u][1],V[edge.v][0],V[edge.v][1]);
			i++;
		}
		int k = 0;
		for (Line2D line : lines) {
			int j = 0;
			for (Line2D line2 : lines) {
				if (k > j && line.intersectsLine(line2)) intersects++;
				j++;
			}
			k++;
		}
		return intersects; // NOTE: Lines connected to the same node always intersect, however this is the case for all graphs, and can be ignored.
	}
}
