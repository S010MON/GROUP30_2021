package phase3;

import phase3.GraphColouringAlgorithm.Bound;

public class Colour
{
	public static boolean DEBUG = false;
	public static boolean OUTPUTALLRESULTS = true;
	static int max = Integer.MAX_VALUE;
	static int min = 3;

	public static void main(String[] args) 
	{
		String inputfile = "src/graphs/phase3_2020_graph02.txt";
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
			alg = args[0];
			inputfile = args[0];
		}
		
		/* Read the file */
		ColEdge[] e = reader.read(inputfile);
		int m = reader.getM();
		int n = reader.getN();
		
		/* Check for Bipartite Graphs and Trees */
		DepthFirstSearch dfs = new DepthFirstSearch();
		try
		{
			dfs.run(e, n);
			if(dfs.isTree() || dfs.checkGraph())
			{
				System.out.println("CHROMATIC NUMBER = 2");
				System.exit(0);
			}
		} catch(Exception exception) {}
		
		/* Switch for separate algorithms or automatic selection */
		long start = System.nanoTime();
		switch (alg) {
			/* Run 3SAT */
			case "s":	
				for (int i = 0; i < times; i++) run(new SAT3(), e, m, n, inputfile);
				break;
			
			/* Run DSatur */
			case "d":
				for (int i = 0; i < times; i++) run(new DSATUR(), e, m, n, inputfile);
				break;
	
			/* Run Greedy */
			case "g":
				for (int i = 0; i < times; i++) run(new Greedy(), e, m, n, inputfile);
				break;
			
			/* Run Backtracking */
			case "bt":	
				for (int i = 0; i < times; i++) run(new Backtracking(), e, m, n, inputfile);
				break;
		
			/* Run BruteForce */
			case "bf":
				for (int i = 0; i < times; i++) run(new Backtracking(), e, m, n, inputfile);
				break;
		
			/* Run Automatic */
			default: 
			for (int p = 0; p < times; p++) {
				// TODO: Add DFS
				int lower = 3;
				int upper = Integer.MAX_VALUE;
				int chromaticNumber = 0;
				boolean solved = false;
				if (m != 0) System.out.println("NEW BEST LOWER BOUND = 3");
				if (m == 0) chromaticNumber = 1;
				else if (n <= 10) chromaticNumber = run(new BruteForceNoPruningThreaded(), e, m, n, inputfile); // If trivial, use brute force.
				else {
					if (n > 2000 || m > 50000) {
						upper = run(new Greedy(), e, m, n, inputfile);
					} else {
						upper = run(new DSATUR(), e, m, n, inputfile);
						if (upper == lower) {
							chromaticNumber = lower; // If range is 1, it is exact.
							solved = true;
						}
					}
					if (!solved) {
						chromaticNumber = run(new SAT3(), e, m, n, inputfile);
					}
				}
				System.out.println("CHROMATIC NUMBER = " + chromaticNumber);
				double time3 = (System.nanoTime()-start)/1000000.0;
				Logger.logResults("AUTO", inputfile, chromaticNumber, time3);
				if(DEBUG) {System.out.println("Time needed: " + time3 + " ms");}
			}
			
			
		}
	}
	
	private static int run(GraphColouringAlgorithm gca, ColEdge[] e, int m, int n, String fileName) {
		long time = System.nanoTime();
		int out = gca.solve(e, m, n, fileName);
		Logger.logResults(gca.getClass().toString(), fileName, out, (System.nanoTime() - time) / 1000000.0);
		return out;
	}

	public static void set(Bound b, int value) {
		Thread.dumpStack();
		if (OUTPUTALLRESULTS) {
			switch (b) {
				case EXACT:
					System.out.println("CHROMATIC NUMBER = " + value);
					break;
				case LOWER:
					min = Math.max(min, value);
					System.out.println("NEW BEST LOWER BOUND = " + min);
					break;
				case UPPER:
					max = Math.min(max, value);
					System.out.println("NEW BEST UPPER BOUND = " + max);
					break;
			}
		}
	}
}