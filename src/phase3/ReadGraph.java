package phase3;
import java.io.*;
import java.util.*;

// -------------------------------------------------------

public class ReadGraph
{
	public static boolean DEBUG = false;
	public final static String COMMENT = "//";

	// -------------------------------------------------------

	public static void main(String args[]) {
		ALGORITHMS.add("g");
		ALGORITHMS.add("bt");
		ALGORITHMS.add("d");
		ALGORITHMS.add("bf");
		ALGORITHMS.add("s");
		ALGORITHMS.add("*");
		
		String inputfile = (String) request("File", "Enter the path to the graph file or the graph id");
		String alg = (String) request("Algorithm", "Select an algorithm:\nGreedy:\t\tg\nBacktracking:\tbt\nDSatur:\t\td\nBrute force:\tbf\n3-SAT:\t\ts\nAutomatic:\t*");
		int times = (int) request("Integer", "Run how many times?");
		if (times <= 1) times = 1;
		if (times > 100) times = 100;
		int vx = 0;
		if (alg.equals("s")) vx = (int) request("Integer", "How many colors to check?\n(0 = Until valid)");
		if (vx <= 1) vx = 0;
		if (vx > 100) vx = 100;
		int limit = 60;
		if (alg.equals("*")) limit = (int) request("Integer", "Time limit? (in seconds)");
		if (limit < 1) limit = 1;
		if (limit > 60000) limit = 60000;
		DEBUG = (boolean) request("Boolean", "Enable debugging? (Y/N)");

		boolean seen[] = null;

		int n = -1; // ! n is the number of vertices in the graph
		int m = -1; // ! m is the number of edges in the graph
		ColEdge e[] = null; // ! e will contain the edges of the graph

		try {
			FileReader fr = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(fr);

			String record = new String();

			// ! The first few lines of the file are allowed to be comments, staring with a // symbol.

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
		
	//-------------------------------------------------------

		
		/* Switch for separate algorithms or automatic selection */
		
		ColEdge[] eCopy = copyEdges(e);
		long start = System.nanoTime();
		switch (alg) {
			case "s":		
			
		/* Run 3SAT */
			boolean solved = false;
			if (vx <= 1) {
				int i = 2;
				while (!solved) {
					SAT3 sat3 = new SAT3(e, m, n, i, inputfile);
					solved = sat3.run();
					if (!solved) i++;
				}
				System.out.println("Colors needed: " + i);
				double time = (System.nanoTime()-start)/1000000.0;
				Logger.logResults("SAT-3", inputfile, i, time);
				System.out.println("Time needed: " + time + " ms");
			} else {
				SAT3 sat3 = new SAT3(e, m, n, vx, inputfile);
				solved = sat3.run();
				System.out.println("Values returned: " + solved);
				double time = (System.nanoTime()-start)/1000000.0;
				System.out.println("Time needed: " + time + " ms");
			}
			break;
			
		/* Run DSatur */
			case "d":
			for (int i = 0; i < times; i++) {
				//TODO: Update DSatur
				eCopy = copyEdges(e);
				DSATUR dsatur = new DSATUR(m, eCopy);dsatur.run();
				double time = (System.nanoTime()-start)/1000000.0;
				System.out.println("Time needed: " + time + " ms");
			}
			break;

		/* Run Greedy */
			case "g":
			for (int i = 0; i < times; i++) {
				Greedy greedy = new Greedy();
				eCopy = copyEdges(e);
				int chromaticNumber = greedy.run(eCopy, m, n, inputfile);
				System.out.println("Colors needed (upper bound): " + chromaticNumber);
				double time = (System.nanoTime()-start)/1000000.0;
				System.out.println("Time needed: " + time + " ms");
			}
			break;
			
		/* Run Backtracking */
			case "bt":	
			for (int i = 0; i < times; i++) {
				Backtracking b = new Backtracking();
				int chromaticNumber = b.run(e, n, m, inputfile);
				System.out.println("Colors needed: " + chromaticNumber);
				double time = (System.nanoTime()-start)/1000000.0;
				System.out.println("Time needed: " + time + " ms");
			}
			break;
		
		/* Run BruteForce */
			case "bf":
			BruteForceNoPruningThreaded b = new BruteForceNoPruningThreaded();
			for (int i = 0; i < times; i++) {
				int chromaticNumber = b.run(e, n, inputfile);
				System.out.println("Colors needed: " + chromaticNumber);
				double time = (System.nanoTime()-start)/1000000.0;
				System.out.println("Time needed: " + time + " ms");
			}
		
		/* Run Automatic */
			break;
			default: 
			for (int i = 0; i < times; i++) {
				boolean exact = true;
				int chromaticNumber = 0;
				int max = 0;
				int min = 2;
				if (m == 0) chromaticNumber = 1;
				else if (n <= 10) chromaticNumber = (new BruteForceNoPruningThreaded()).run(e, n, inputfile); // If trivial, use brute force.
				else {
					if (n > 2000 || m > 50000) {
						Greedy greedy = new Greedy();
						max = greedy.run(e, m, n, inputfile);
						eCopy = copyEdges(e);
						solved = false;
						if (max == min) {
							chromaticNumber = min; // If range is 1, it is exact.
							solved = true;
						}
					} else {
						//TODO: Update DSatur
						eCopy = copyEdges(e);
						DSATUR dsatur = new DSATUR(m, eCopy);
						dsatur.run();
						solved = false;
						if (max == min) {
							chromaticNumber = min; // If range is 1, it is exact.
							solved = true;
						}
					}
					
					while (!solved) {
						Boolean[] isSolved = new Boolean[1];
						SAT3 sat3 = new SAT3(e, m, n, min, inputfile);
						Thread satThread = new Thread(new Runnable(){
							@Override
							public void run() {
								boolean solved = sat3.run();
								isSolved[0] = solved;
							}
						});
						satThread.start();
						while (isSolved[0] == null && (System.nanoTime()-start)/1000000000.0 < limit) {
							double timer = (Math.round((System.nanoTime()-start)/100000000.0) / 10.0);
							System.out.print("\r[" + timer  + "] Current Range: " + min + " - " + max);
						};
						if ((System.nanoTime()-start)/1000000000.0 >= limit){
							solved = true;
							exact = false; // If time limit is reached, return a range
						} else {
							solved = isSolved[0];
							if (!solved) min++;
							else chromaticNumber = min;
							if (max == min) {
								chromaticNumber = min; // If range is 1, it is exact.
								solved = true;
							}
							double timer = (Math.round((System.nanoTime()-start)/100000000.0) / 10.0);
							System.out.print("\r[" + timer  + "] Current Range: " + min + " - " + max);
						}
					}
					System.out.println("\r");
				}
				if (!exact) {
					System.out.println("Colors needed: " + min + " - " + max);
					double time = (System.nanoTime()-start)/1000000.0;
					Logger.logResults("AUTO", inputfile, min + "-" + max, time);
					System.out.println("Time needed: " + time + " ms");
				} else {
					System.out.println("Colors needed: " + chromaticNumber);
					double time = (System.nanoTime()-start)/1000000.0;
					Logger.logResults("AUTO", inputfile, chromaticNumber, time);
					System.out.println("Time needed: " + time + " ms");
				}
			}
			
			
		}
	}
	static final ArrayList<String> ALGORITHMS = new ArrayList<>();

	/**
	 * Asks the user for a specific type of input
	 * @param type A type: {@code Integer}, {@code String}, {@code Algorithm}, or {@code File}
	 * @param string The prompt given to the user
	 * @return When the input is valid, the input
	 */
	private static Object request(String type, String string) {
		System.out.println(string);
		Scanner in = new Scanner(System.in);
		Object output = null;
		while (output == null || (type == "Algorithm" && !ALGORITHMS.contains(output)) || (type == "File" && !fileExists((String) output))) {
			String inp = in.nextLine();
			try {
				if (type == "Integer") output = Integer.parseInt(inp);
				if (type == "Boolean" && (inp.equals("y") || inp.equals("yes") || inp.equals("t") || inp.equals("true") || inp.equals("1"))) output = true; // Account for different 'true' and 'false' values
				if (type == "Boolean" && (inp.equals("n") || inp.equals("no") || inp.equals("f") || inp.equals("false") || inp.equals("0"))) output = false;
				if (type == "String" || type == "Algorithm" || type == "File") {
					if (type == "File" && !inp.contains(".txt")) {
						if (inp.length() == 1) inp = "graph0" + inp + "_2020.txt";
						else inp = "graph" + inp + "_2020.txt";
					}
					output = inp;
				}
			} catch (Exception e) {
				System.out.println( inp + " is not a valid " + type );
			}
			if (output == null || type == "Algorithm" && !ALGORITHMS.contains(output) || (type == "File" && !fileExists((String) output))) System.out.println( inp + " is not a valid " + type );
		}
		return output;
	}

	//-------------------------------------------------------
	/**
	 * Checks is a file exists
	 * @param path Path to file
	 * @return {@code true} if the file exists
	 * {@code false} if the file does not exist
	 */
	private static boolean fileExists(String path){
		File f = new File(path);
		return f.exists();
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
	
//	THIS CODE IS NEVER REACHED
//	/**
//	This method generates a matrix with the following elements:
//	- column 0 contains all the vertex numbers in ascending order (starting from 1)
//	- column 1 contains the respective weight of each vertex
//	- column 2 contains the respective color of each vertex (initially set to -1)
//	@author Noah Croes
//	@param e array of ColEdge class instance. In this case it is always e object from main method
//	@param m edges amount
//	@param n vertices amount
//	@return matrix containing the nodes, their respective weights and color
//	*/
//	public static int[][] vertexMatrix(ColEdge[] e, int m, int n){
//		int[][] vMat = new int[n][3];
//		for(int i = 0; i<n; i++){
//		vMat[i][0] = i + 1;
//		}
//		for(int vertexNumber = 1; vertexNumber <= n; vertexNumber++){
//			for(int i = 0; i<m; i++){
//				if(e[i].u == vertexNumber || e[i].v == vertexNumber){
//					vMat[vertexNumber-1][1] += 1;
//				}
//			}
//		}
//		for(int i = 0; i < n; i++){
//			vMat[i][2] = -1; //all colors are set to -1, meaning blank. If >=0, then the vertex has a color.
//		}
//		return vMat;
//	}
//	
//	//-------------------------------------------------------
//	/**
//	Method for printing out the instructions of how the matrix from {@link #vertexMatrix(ColEdge[], int, int)} should be interpreted
//	@author Noah Croes
//	@param vMat is the matrix generated by {@link #vertexMatrix(ColEdge[], int, int)}
//	*/
//	public static void matrixPrinter(int[][] vMat){
//		System.out.println();
//		for(int i = 0; i < vMat.length; i++){
//			System.out.println("The nodeweight of vertex "+(i+1)+" is: "+vMat[i][1]+". And the color is: "+vMat[i][2]);
//		}
//	}
//	
//	//-------------------------------------------------------
//	/**
//	method that assigns a color to a specific node on ColEdge[] e, 
//	while also updating the color in the matrix previously generated by {@link #vertexMatrix(ColEdge[], int, int)}.
//	@author Noah Croes
//	@param color specific color that you want to assign
//	@param nodeNumber the specific node (vertex) that you want to asssign the color to
//	@param e array of ColEdge class instance. In this case it is always e object from main method
//	@param vMat is the matrix generated by {@link #vertexMatrix(ColEdge[], int, int)}, which will be updated
//	@return e which contains the newly assigned color on each concerned ColEdge e[n], where n is the edge number of the graph
//	*/
//	public static ColEdge[] colorFiller(int color, int nodeNumber, ColEdge[] e, int[][] vMat){
//		for(int i = 0; i<e.length; i++){
//			if(e[i].u == nodeNumber){
//				e[i].colU = color;
//			}
//			if(e[i].v == nodeNumber){
//				e[i].colV = color;
//			}	
//		}
//		vMat[nodeNumber-1][2] = color;
//		return e;
//	}
//	
//	//-------------------------------------------------------
//	/**
//	Method that decides whether the whole graph is currently legal or not (regarding coloring). 
//	It uses the {@link ColEdge#legal()} method, which checks one specific edge. This method does it for all edges.
//	@author Noah Croes
//	@param e array of ColEdge class instance. In this case it is always e object from main method
//	@return boolean on whether the whole graph is legal
//	*/
//	public static boolean legalGraph(ColEdge[] e){
//		int i;
//		System.out.println();
//		for(i = 0; (e[i].legal() == true) && (i < e.length-1); i++){}
//		if(e[i].legal() == false){return false;}
//		return true;
//	}	
}
