package phase3;

import java.io.File;

public class Colour
{
	public static boolean DEBUG = false;

	public static void main(String[] args) 
	{
		String inputfile = "/home/leon/git/GROUP30_2021/src/graphs/phase3_2020_graph02.txt";
		String alg = "";
		int times = 1;
		int limit = 120; // 2 minutes
		ReadGraph reader = new ReadGraph();
		
		/* No arguments provided */
		if(args.length == 0)
		{
			prompt();
			System.exit(0);
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
		DepthFirstSearch dfs = new DepthFirstSearch(e, n);
		if(!dfs.containsLoop())
		{
			System.out.println("G(X) = 2");
			System.exit(0);
		}
		
		/* Switch for separate algorithms or automatic selection */
		long start = System.nanoTime();
		switch (alg) {
			/* Run 3SAT */
			case "s":	
				boolean solved = false;
				int i = 1;
				while (!solved) {
					SAT3 sat3 = new SAT3(e, m, n, i, inputfile);
					solved = sat3.run();
					if (!solved) i++;
				}
				System.out.println("Colors needed: " + i);
				double time = (System.nanoTime()-start)/1000000.0;
				Logger.logResults("SAT-3", inputfile, i, time);
				System.out.println("Time needed: " + time + " ms");
			break;
			
			/* Run DSatur */
			case "d":
				for (int j = 0; j < times; j++) {
					//TODO: Update DSatur
					ColEdge[] eCopy = reader.copyEdges(e);
					DSATUR dsatur = new DSATUR();
					dsatur.run(e, m, n, inputfile);
					double time2 = (System.nanoTime()-start)/1000000.0;
					System.out.println("Time needed: " + time2 + " ms");
				}
				break;
	
			/* Run Greedy */
			case "g":
				for (int k = 0; k < times; k++) {
					Greedy greedy = new Greedy();
					ColEdge[] eCopy = reader.copyEdges(e);
					int chromaticNumber = greedy.run(eCopy, m, n, inputfile);
					System.out.println("Colors needed (upper bound): " + chromaticNumber);
					double time3 = (System.nanoTime()-start)/1000000.0;
					System.out.println("Time needed: " + time3 + " ms");
				}
				break;
			
			/* Run Backtracking */
			case "bt":	
				for (int l = 0; l < times; l++) {
					Backtracking b = new Backtracking();
					int chromaticNumber = b.run(e, n, m, inputfile);
					System.out.println("Colors needed: " + chromaticNumber);
					double time2 = (System.nanoTime()-start)/1000000.0;
					System.out.println("Time needed: " + time2 + " ms");
				}
				break;
		
			/* Run BruteForce */
			case "bf":
				BruteForceNoPruningThreaded b = new BruteForceNoPruningThreaded();
				for (int o = 0; o < times; o++) {
					int chromaticNumber = b.run(e, n, inputfile);
					System.out.println("Colors needed: " + chromaticNumber);
					double time3 = (System.nanoTime()-start)/1000000.0;
					System.out.println("Time needed: " + time3 + " ms");
				}
				break;
		
			/* Run Automatic */
			default: 
			for (int p = 0; p < times; p++) {
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
						ColEdge[] eCopy = ColEdge.copyEdges(e);
						solved = false;
						if (max == min) {
							chromaticNumber = min; // If range is 1, it is exact.
							solved = true;
						}
					} else {
						DSATUR dsatur = new DSATUR();
						ColEdge[] eCopy = ColEdge.copyEdges(e);
						max = dsatur.run(eCopy, m, n, inputfile);
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
					double time3 = (System.nanoTime()-start)/1000000.0;
					Logger.logResults("AUTO", inputfile, min + "-" + max, time3);
					System.out.println("Time needed: " + time3 + " ms");
				} else {
					System.out.println("Colors needed: " + chromaticNumber);
					double time3 = (System.nanoTime()-start)/1000000.0;
					Logger.logResults("AUTO", inputfile, chromaticNumber, time3);
					System.out.println("Time needed: " + time3 + " ms");
				}
			}
			
			
		}
	}
	
	/** -------------------------------------------------------
	 *  Prompt the user for input in the case of an exception
	 */
	private static void prompt()
	{
		System.out.println("Enter a filepath for a graph for a chomatic number");
		System.out.println("Or use type one of the following options to pick an algorithm\n"
				+ "\nbf	Brute Force"
				+ "\ng	Greedy"
				+ "\nd	DSatur"
				+ "\nbt	Backtracking"
				+ "\ns	3 Sat");
	}
	
	/** -------------------------------------------------------
	 * Checks if a file exists
	 * @param path Path to file
	 * @return {@code true} if the file exists
	 * {@code false} if the file does not exist
	 */
	private static boolean fileExists(String path){
		File f = new File(path);
		return f.exists();
	}
}