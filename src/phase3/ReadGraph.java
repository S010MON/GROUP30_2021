package phase3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// -------------------------------------------------------

public class ReadGraph
{
	public final static String COMMENT = "//";
	
	private int n = -1; // ! n is the number of vertices in the graph
	private int m = -1; // ! m is the number of edges in the graph
	ColEdge e[] = null; // ! e will contain the edges of the graph

	// -------------------------------------------------------

	public ColEdge[] read(String inputfile) 
	{
		boolean seen[] = null;

		try {

			File file = new File(inputfile);
			if (!file.exists()) inputfile = "src/graphs/phase3_2020_graph" + inputfile + ".txt";

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
				if (Colour.DEBUG)
					System.out.println(COMMENT + " Number of vertices = " + n);
			} else {
				br.close();
				throw new RuntimeException("Unable to read vertex count from " + inputfile);
			}

			seen = new boolean[n + 1];
			record = br.readLine();

			if (record.startsWith("EDGES = ")) {
				m = Integer.parseInt(record.substring(8));
				if (Colour.DEBUG)
					System.out.println(COMMENT + " Expected number of edges = " + m);
			} else {
				br.close();
				throw new RuntimeException("Unable to read edge count from " + inputfile);
			}

			e = new ColEdge[m];

			for (int d = 0; d < m; d++) {
				if (Colour.DEBUG)
					System.out.println(COMMENT + " Reading edge " + (d + 1));
				record = br.readLine(); // Pull a new string from the file
				String data[] = record.split(" "); // Split into array of strings at each " "
				if (data.length != 2) // If data[] has more than two points (bad read)
				{
					br.close();
					throw new RuntimeException("Unable to read edges from " + inputfile);
				}
				e[d] = new ColEdge();

				e[d].u = Integer.parseInt(data[0]);
				e[d].v = Integer.parseInt(data[1]);

				seen[e[d].u] = true;
				seen[e[d].v] = true;

				if (Colour.DEBUG)
					System.out.println(COMMENT + " Edge: " + e[d].u + " " + e[d].v);
			}

			String surplus = br.readLine();
			if (surplus != null) {
				if (surplus.length() >= 2)
					if (Colour.DEBUG)
						System.out.println(
								COMMENT + " Warning: there appeared to be data in your file after the last edge: '"
										+ surplus + "'");
			}
			br.close();
		}

		catch (IOException ex) {
			// catch possible io errors from readLine()
			System.out.println("Error! Problem reading file " + inputfile);
			throw new RuntimeException("Unable to read " + inputfile);
		}

		for (int x = 1; x <= n; x++) {
			if (seen[x] == false) {
				if (Colour.DEBUG)
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

	/** -------------------------------------------------------
	 * Make a deep copy of a ColEdge array
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
