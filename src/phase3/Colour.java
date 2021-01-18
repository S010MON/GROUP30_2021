package phase3;

import phase3.GraphColouringAlgorithm.Bound;

public class Colour
{
	public static final boolean DEBUG = false;
	public static boolean outputAllResults = true;
	static int max = Integer.MAX_VALUE;
	static int min = 3;

	public static void main(String[] args) 
	{
		String inputfile;
		String alg = "";
		int times = 1;
		ReadGraph reader = new ReadGraph();
		
		/* No arguments provided */
		if(args.length == 0)
		{
			throw new RuntimeException("Not enough arguments.");
		}
		
		/* If only a filename is provided */
		else if(args.length == 1)
		{
			inputfile = args[0];
		}
		
		/* If an algorithm is requested */
		else if(args.length == 2)
		{
			alg = args[1];
			inputfile = args[0];
		}

		else {
			throw new RuntimeException("Too many arguments.");
		}
		
		/* Read the file */
		ColEdge[] e = reader.read(inputfile);
		int m = reader.getM();
		int n = reader.getN();
		
		switch (alg) {
			/* Run 3SAT */
			case "s":	
				for (int i = 0; i < times; i++) {
					outputAllResults = false;
					System.out.println("OUTPUT: " + run(new SAT3(), e, m, n, inputfile));
				}
				break;
			
			/* Run DSatur */
			case "d":
				for (int i = 0; i < times; i++) {
					outputAllResults = false;
					System.out.println("OUTPUT: " + run(new DSATUR(), e, m, n, inputfile));
				}
				break;
	
			/* Run Greedy */
			case "g":
				for (int i = 0; i < times; i++) {
					outputAllResults = false;
					System.out.println("OUTPUT: " + run(new Greedy(), e, m, n, inputfile));
				}
				break;
			
			/* Run Backtracking */
			case "bt":	
				for (int i = 0; i < times; i++) {
					outputAllResults = false;
					System.out.println("OUTPUT: " + run(new Backtracking(), e, m, n, inputfile));
				}
				break;
		
			/* Run BruteForce */
			case "bf":
				for (int i = 0; i < times; i++) {
					outputAllResults = false;
					System.out.println("OUTPUT: " + run(new BruteForceNoPruningThreaded(), e, m, n, inputfile));
				}
				break;

			/* Run RLF */
			case "r":
				for (int i = 0; i < times; i++) {
					outputAllResults = false;
					System.out.println("OUTPUT: " + run(new RecursiveLargestFirst(), e, m, n, inputfile));
				}
				break;
		
			/* Run Depth First Search */
			case "dfs":
				double start = System.currentTimeMillis();
				DepthFirstSearch d = new DepthFirstSearch();
				d.run(ColEdge.copyEdges(e), n);
				double time = (System.nanoTime()-start)/1000000.0;
				if(d.isTree() || d.checkGraph()) {
					System.out.println("CHROMATIC NUMBER = 2");
					Logger.logResults( "DepthFirstSearch", inputfile, 2, (System.nanoTime() - time) / 1000000.0);
				} else {
					System.out.println("CHROMATIC NUMBER > 2");
					Logger.logResults("DepthFirstSearch", inputfile, -1, (System.nanoTime() - time) / 1000000.0);
				}
				break;
				
			/* Run Automatic */
			default: 
			
			for (int p = 0; p < times; p++) {
				int lower = 3;
				int upper = Integer.MAX_VALUE;
				boolean solved = false;
				if (m != 0) System.out.println("NEW BEST LOWER BOUND = 3");
				if (m == 0) {
				} else if (n <= 20 && m <= 40)
					run(new BruteForceNoPruningThreaded(), e, m, n, inputfile);
				else {
					DepthFirstSearch dfs = new DepthFirstSearch(); // 2. Run DFS.
					try	{
						dfs.run(ColEdge.copyEdges(e), n);
					} catch(Exception exception) {
						throw new RuntimeException("Failed to run DFS.");
					}
					if(dfs.isTree() || dfs.checkGraph())
					{
						System.out.println("CHROMATIC NUMBER = 2");
					}
					else
					{
						if (m > 10000) { // 3. If edges > 10000, run Greedy; else run DSatur.
							upper = run(new Greedy(), e, m, n, inputfile);
						} else {
							upper = run(new DSATUR(), e, m, n, inputfile);
						}
						if (upper == lower) {
							solved = true;
						}
					}
				}
				if (!solved) {
					run(new SAT3(), e, m, n, inputfile);
				}
			}
		}
	}
	
	/**
	 * Runs a GraphColouringAlgorithm
	 * @param gca GraphColouringAlgorithm
	 * @param e Array of ColEdge objects
	 * @param m Number of vertices
	 * @param n Number of edges
	 * @param fileName Filename to use for logging
	 * @return Chromatic number
	 */
	private static int run(GraphColouringAlgorithm gca, ColEdge[] e, int m, int n, String fileName) {
		long time = System.nanoTime();
		int out = gca.solve(e, m, n, fileName);
		Logger.logResults(gca.getClass().toString(), fileName, out, (System.nanoTime() - time) / 1000000.0);
		return out;
	}

	/**
	 * Updates the bounds of the chromatic number
	 * @param b Bound
	 * @param value Value
	 */
	public static void set(Bound b, int value) {
		if (outputAllResults) {
			switch (b) {
				case EXACT:
					System.out.println("CHROMATIC NUMBER = " + value);
					break;
				case LOWER:
					if (value > min) {
						min = value;
						System.out.println("NEW BEST LOWER BOUND = " + min);
					}
					break;
				case UPPER:
					if (value < max) {
						max = value;
						System.out.println("NEW BEST UPPER BOUND = " + max);
					}
					break;
					
			}
			if (min == max && b != Bound.EXACT) {
				System.out.println("CHROMATIC NUMBER = " + min);
			}
		}
	}
}