package phase3;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
	public static boolean DEBUG = false;

	public static void main(String[] args) 
	{
		String inputfile = null;
		String alg;
		boolean autoDetect = true;
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
			autoDetect = false;
			inputfile = args[0];
		}
		
		/* Read the file */
		ColEdge[] e = reader.read(inputfile);
		int m = reader.getM();
		int n = reader.getN();
		
		/* Check for Bipartite Graphs and Trees */
		DepthFirstSearch dfs = new DepthFirstSearch(e, n);
		if(!dfs.containsLoop())
			System.out.println("G(X) = 2");
		
//		/* Switch for separate algorithms or automatic selection */
//		long start = System.nanoTime();
//		switch (alg) {
//			case "s":		
//			
//		/* Run 3SAT */
//			boolean solved = false;
//			if (vx <= 1) {
//				int i = 2;
//				while (!solved) {
//					SAT3 sat3 = new SAT3(e, m, n, i, inputfile);
//					solved = sat3.run();
//					if (!solved) i++;
//				}
//				System.out.println("Colors needed: " + i);
//				double time = (System.nanoTime()-start)/1000000.0;
//				Logger.logResults("SAT-3", inputfile, i, time);
//				System.out.println("Time needed: " + time + " ms");
//			} else {
//				SAT3 sat3 = new SAT3(e, m, n, vx, inputfile);
//				solved = sat3.run();
//				System.out.println("Values returned: " + solved);
//				double time = (System.nanoTime()-start)/1000000.0;
//				System.out.println("Time needed: " + time + " ms");
//			}
//			break;
//			
//		/* Run DSatur */
//			case "d":
//			for (int i = 0; i < times; i++) {
//				//TODO: Update DSatur
//				ColEdge[] eCopy = reader.copyEdges(e);
//				DSATUR dsatur = new DSATUR();
//				dsatur.run(e, m, n, inputfile);
//				double time = (System.nanoTime()-start)/1000000.0;
//				System.out.println("Time needed: " + time + " ms");
//			}
//			break;
//	
//		/* Run Greedy */
//			case "g":
//			for (int i = 0; i < times; i++) {
//				Greedy greedy = new Greedy();
//				ColEdge[] eCopy = reader.copyEdges(e);
//				int chromaticNumber = greedy.run(eCopy, m, n, inputfile);
//				System.out.println("Colors needed (upper bound): " + chromaticNumber);
//				double time = (System.nanoTime()-start)/1000000.0;
//				System.out.println("Time needed: " + time + " ms");
//			}
//			break;
//			
//		/* Run Backtracking */
//			case "bt":	
//			for (int i = 0; i < times; i++) {
//				Backtracking b = new Backtracking();
//				int chromaticNumber = b.run(e, n, m, inputfile);
//				System.out.println("Colors needed: " + chromaticNumber);
//				double time = (System.nanoTime()-start)/1000000.0;
//				System.out.println("Time needed: " + time + " ms");
//			}
//			break;
//		
//		/* Run BruteForce */
//			case "bf":
//			BruteForce b = new BruteForce();
//			for (int i = 0; i < times; i++) {
//				int chromaticNumber = b.run(e, n, inputfile);
//				System.out.println("Colors needed: " + chromaticNumber);
//				double time = (System.nanoTime()-start)/1000000.0;
//				System.out.println("Time needed: " + time + " ms");
//			}
//		
//		/* Run Automatic */
//			break;
//			default: 
//			for (int i = 0; i < times; i++) {
//				boolean exact = true;
//				int chromaticNumber = 0;
//				int max = 0;
//				int min = 2;
//				if (m == 0) chromaticNumber = 1;
//				else if (n <= 10) chromaticNumber = (new BruteForce()).run(e, n, inputfile); // If trivial, use brute force.
//				else {
//					if (n > 2000 || m > 50000) {
//						Greedy greedy = new Greedy();
//						max = greedy.run(e, m, n, inputfile);
//						ColEdge[] eCopy = reader.copyEdges(e);
//						solved = false;
//						if (max == min) {
//							chromaticNumber = min; // If range is 1, it is exact.
//							solved = true;
//						}
//					} else {
//						//TODO: Update DSatur
//						ColEdge[] eCopy = reader.copyEdges(e);
//						DSATUR dsatur = new DSATUR();
//						dsatur.run(e, m, n, inputfile);
//						solved = false;
//						if (max == min) {
//							chromaticNumber = min; // If range is 1, it is exact.
//							solved = true;
//						}
//					}
//					
//					while (!solved) {
//						Boolean[] isSolved = new Boolean[1];
//						SAT3 sat3 = new SAT3(e, m, n, min, inputfile);
//						Thread satThread = new Thread(new Runnable(){
//							@Override
//							public void run() {
//								boolean solved = sat3.run();
//								isSolved[0] = solved;
//							}
//						});
//						satThread.start();
//						while (isSolved[0] == null && (System.nanoTime()-start)/1000000000.0 < limit) {
//							double timer = (Math.round((System.nanoTime()-start)/100000000.0) / 10.0);
//							System.out.print("\r[" + timer  + "] Current Range: " + min + " - " + max);
//						};
//						if ((System.nanoTime()-start)/1000000000.0 >= limit){
//							solved = true;
//							exact = false; // If time limit is reached, return a range
//						} else {
//							solved = isSolved[0];
//							if (!solved) min++;
//							else chromaticNumber = min;
//							if (max == min) {
//								chromaticNumber = min; // If range is 1, it is exact.
//								solved = true;
//							}
//							double timer = (Math.round((System.nanoTime()-start)/100000000.0) / 10.0);
//							System.out.print("\r[" + timer  + "] Current Range: " + min + " - " + max);
//						}
//					}
//					System.out.println("\r");
//				}
//				if (!exact) {
//					System.out.println("Colors needed: " + min + " - " + max);
//					double time = (System.nanoTime()-start)/1000000.0;
//					Logger.logResults("AUTO", inputfile, min + "-" + max, time);
//					System.out.println("Time needed: " + time + " ms");
//				} else {
//					System.out.println("Colors needed: " + chromaticNumber);
//					double time = (System.nanoTime()-start)/1000000.0;
//					Logger.logResults("AUTO", inputfile, chromaticNumber, time);
//					System.out.println("Time needed: " + time + " ms");
//				}
//			}
//			
//			
//		}
	}
	
	
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

	//-------------------------------------------------------
	/**
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
