package phase3;

import java.util.*;

public class Backtracking extends GraphColouringAlgorithm
		{

			public Backtracking() {
				bound = Bound.UPPER;
			}
		
		/**
		 * Computes the UPPER BOUND of a graph
		 * @param e	Array of edges.
		 * @param m	Number of vertices.
		 * @param n Number of edges.
		 * @param fileName Number of edges.
		 * @return UPPER BOUND
		 */
		public int solve(ColEdge[] e, int m, int n, String inputfile) {

			long startTime = System.nanoTime();

			int v = 1; 
			int [][] matrix= new int[n+1][n+1];
				

			for(int i=0;i<e.length;i++){
				
				int a = e[i].u;
				int b = e[i].v;
				
				matrix[a-1][b-1]=1;
				matrix[b-1][a-1]=1;
			}
			
		
			// Colour array
			int [] colour = new int[n+1];
			colour[0] = 1;
		
			// Here the result of the is printed 
			graphColour(v, colour, matrix);

			// Method call to print the chomatic number 
			int chrom = max(colour);
			if (Colour.DEBUG) System.out.println(chrom);
			double time = (System.nanoTime() - startTime)/1000000.0;
			if (Colour.DEBUG) System.out.println("Chromatic number: " + chrom);
			if (Colour.DEBUG) System.out.println("Time needed: " + (time + " ms"));
			//Logger.logResults("Backtracking", inputfile , chrom, time);
			Colour.set(bound, chrom);
			return chrom;
				
		}

		static void graphColour(int v, int[] colour, int[][] matrix) {
		
			if (Colour.DEBUG) {System.out.println(Arrays.deepToString(matrix)); }

			
			// BASE CASE 
			// Prints the combination of colours when all the vertices are coloured
			for (int i = 0; i <= matrix.length - 1 ; i++) {

				// If all vertices are coloured 
				if (colour[i] != 0) {

				// the array of colours is printed
				if (Colour.DEBUG) System.out.println(Arrays.toString(colour));

				} else {

			// cr is the colour that needs to be tested
			for (int cr = 1; cr <= matrix.length - 1; cr++) {

				// isAvailble() method is called in order to make sure that a colour can be used
				if (isAvailable (v, colour, cr, matrix)) {
					// A colour is assigned to each index of the array
					colour[v] = cr;
					break; 
				}
			} 

			if ((v+1) <= matrix.length) {
			
				// This is the recursive call of the method that goes through all the other vertices that still need to be coloured
				//RECURSIVE CALL
				graphColour(v+1, colour, matrix);

			}

			return;
			}
		}

		return;
	}
			
		
		//This method checks if that colour is available to place
		//int v is the vertex to be coloured 
		//int colour is a possible colour that can be associated to vertex v 

		/**
		 * This method checks if that colour is available to place
		 * int v is the vertex to be coloured 
		 * int colour is a possible colour that can be associated to vertex v 
		 * @param v Vertex to colour
		 * @param colour Array of colours
		 * @param cr Colour to test
		 * @param matrix Matrix
		 * @return if a color is available
		 */
		static boolean isAvailable (int v, int[] colour, int cr, int[][] matrix) {
		
			for (int i = 0; i < matrix.length; i++) {
		
				//Here I check whether the vertex v is adjacent to the vertex i that the for loop is checking 
				if (matrix[v][i] == 1 && cr == colour[i]) {
		
					/*I return false if the colour of the two adjacent vertices match 
					because I cannot use that colour*/
					if(Colour.DEBUG) {System.out.println("Colour " + colour[i] + " available: false"); }
						return false; 
				}
			}
			// Returns true if it is safe to use that colour 
			if(Colour.DEBUG) {System.out.println("Colour " + cr + " available: true"); }
				return true; 
		}

		/**
		 * Returns the chromatic number
		 * @param colour Array of colors
		 * @return The chromatic number
		 */
		static int max (int [] colour) {
			
			int max = colour[0];

			for (int c = 0; c < colour.length; c++) {

				if (colour[c] > max) {
					max = colour[c];
				}
			}

			return max;
		}

}


