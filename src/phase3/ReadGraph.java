package phase3;
import java.io.*;
import java.util.*;

// -------------------------------------------------------

public class ReadGraph
{
	public static boolean DEBUG = false;
	public final static String COMMENT = "//";
	
	private int n = -1; // ! n is the number of vertices in the graph
	private int m = -1; // ! m is the number of edges in the graph
	ColEdge e[] = null; // ! e will contain the edges of the graph

	// -------------------------------------------------------

	public ColEdge[] read(String inputfile) 
	{
		boolean seen[] = null;

		try {
			FileReader fr = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(fr);

			String record = new String();


			while ((record = br.readLine()) != null) {
				if (record.startsWith("//"))
					continue;
				break; // Saw a line that did not start with a comment -- time to start reading the data in!
			}

			if (record.startsWith("VERTICES = ")) {
				n = Integer.parseInt(record.substring(11));
				if (DEBUG)
					System.out.println(COMMENT + " Number of vertices = " + n);
			} else {
				System.out.println("Error! Problem reading file " + inputfile);
				System.exit(0);
			}

			seen = new boolean[n + 1];
			record = br.readLine();

			if (record.startsWith("EDGES = ")) {
				m = Integer.parseInt(record.substring(8));
				if (DEBUG)
					System.out.println(COMMENT + " Expected number of edges = " + m);
			} else {
				System.out.println("Error! Problem reading file " + inputfile);
				System.exit(0);
			}

			e = new ColEdge[m];

			for (int d = 0; d < m; d++) {
				if (DEBUG)
					System.out.println(COMMENT + " Reading edge " + (d + 1));
				record = br.readLine(); // Pull a new string from the file
				String data[] = record.split(" "); // Split into array of strings at each " "
				if (data.length != 2) // If data[] has more than two points (bad read)
				{
					System.out.println("Error! Malformed edge line: " + record);
					System.exit(0);
				}
				e[d] = new ColEdge();

				e[d].u = Integer.parseInt(data[0]);
				e[d].v = Integer.parseInt(data[1]);

				seen[e[d].u] = true;
				seen[e[d].v] = true;

				if (DEBUG)
					System.out.println(COMMENT + " Edge: " + e[d].u + " " + e[d].v);
			}

			String surplus = br.readLine();
			if (surplus != null) {
				if (surplus.length() >= 2)
					if (DEBUG)
						System.out.println(
								COMMENT + " Warning: there appeared to be data in your file after the last edge: '"
										+ surplus + "'");
			}
		}

		catch (IOException ex) {
			// catch possible io errors from readLine()
			System.out.println("Error! Problem reading file " + inputfile);
			System.exit(0);
		}

		for (int x = 1; x <= n; x++) {
			if (seen[x] == false) {
				if (DEBUG)
					System.out.println(COMMENT + " Warning: vertex " + x
							+ " didn't appear in any edge : it will be considered a disconnected vertex on its own.");
			}
		}
		
		return e;
	}
	
	/**-------------------------------------------------------
	 * 
	 * @return
	 */
	public int getM()
	{
		return m;
	}
	
	/**-------------------------------------------------------
	 * 
	 * @return
	 */
	public int getN()
	{
		return n;
	}


	//-------------------------------------------------------
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
