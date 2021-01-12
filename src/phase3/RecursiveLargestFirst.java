package phase3;

import java.util.Arrays;

public class RecursiveLargestFirst {
	private int[][] adjacent;
	private int vertices;
	private int edges;
	private int chromaticNumber;
	private int[] color; // color of vertices, -1 if not colored
	private int[] degree; //array stores degrees (number of edges connected to vertex) of all vertices
	private int[] NN;//set of non neighbour vertices
	private int NNCount; //size of NN (non-neighbour) array aka number of NN set
	private int todo; //number of vertices unprocessed
	
	private ColEdge[] graph;
	
	public RecursiveLargestFirst() {
		
	}
	/**
	 * Computes the CHROMATIC NUMBER of a graph
	 * @param e	Array of edges.
	 * @param m	Number of vertices.
	 * @param n Number of edges.
	 * @return chromatic number
	 */
	public int solve(ColEdge[] e, int m, int n) {
		graph = e;
		vertices = m;
		edges = n;
		
		adjacent = new int[vertices][vertices];
		color = new int[vertices]; // color of vertices, -1 if not colored
		degree = new int[vertices]; //array stores degrees (number of edges connected to vertex) of all vertices
		NN =  new int[vertices];//set of non neighbour vertices
				
		System.out.println("Running RLF");
		initialise();
		coloring();
		System.out.println(Arrays.deepToString(adjacent));
		System.out.println(Arrays.toString(color));
		return chromaticNumber;
	}
	public void coloring() {
		int x,y;
		int colorNumber = -1;
		int verticesInCommon = 0;
		
		while(todo > 0) {
			x = maxDegreeVertex();
			colorNumber++;
			color[x] = colorNumber;
			todo--;
			updateNonNeighbours(colorNumber);
			while(NNCount>0) { //as long as there are NN
				//find another vertex y that has the maximum amount of neighbours in commin with x. Vertices in common is that number.
				y = appropriateY(colorNumber, verticesInCommon);
				//if there are no vertices in common, then y is equal to vertex with max degree in NN
				if(verticesInCommon == 0) {
					y = maxDegreeOfNN();
				}
				color[y] = colorNumber;
				todo--;
				updateNonNeighbours(colorNumber);
			}
		}
		chromaticNumber = colorNumber + 1;
	}
	//find vertex in NN with max degree
	private int maxDegreeOfNN() {
		int tempy, temp, max;
		tempy = -1;
		max = 0;
		for(int i = 0; i < NNCount; i++) {
			temp = 0;
			for(int j = 0; j < vertices; j++) {
				if(color[j] == -1 && adjacent[NN[i]][j] == 1) {
					temp++;
				}
			}
			if(temp > max) {
				max = temp;
				tempy = NN[i];
			}
		}
		if(max == 0) {
			return NN[0];
		}
		return tempy;
	}
	//method that provides suitable y from the NN set
	private int appropriateY(int colorNumber, int verticesInCommon) {
		int temp, tempy, y;
		y = -1;
		//scanned array stores uncolored vertices, except vertex being processed
		int[] scanned = new int[vertices];
		verticesInCommon = 0;
		for(int i = 0; i < NNCount; i++) { // check all vertices in NN
			//tempy is the currently processed vertex
			tempy = NN[i];
			//temp is the neighbours in common of tempy
			//and vertices color colorNumber
			temp = 0;
			scannedInitialise(scanned);
			//reset scanned array so we can check all vertices in case they are adjacent to i-th vertex in NN
			for(int x = 0; x < vertices; x++) {
				if(color[x] == colorNumber) {
					for(int z = 0; z < vertices; z++) {
						if(color[z] == -1 && scanned[z] == -1) {
							if(adjacent[x][z] == 1 && adjacent[tempy][z] == 1) {
								temp++;
								scanned[z] = 1; //vertex z is scanned
							}
						}
					}
				}
			}
			if(temp > verticesInCommon) {
				verticesInCommon = temp;
				y = tempy;
			}
		}
		return y; //returns -1 if none is
	}
	private void scannedInitialise(int[] scanned) {
		for(int i = 0; i < vertices; i++) {
			scanned[i] = -1;
		}
	}
	//update NN array, after this you have an array that contains all NN vertices which are uncolored
	private void updateNonNeighbours(int colorNumber) {
		NNCount = 0;
		//all vertices that are uncolored, we add to the NN set
		for(int i = 0; i < vertices; i++) {
			if(color[i] == -1) {
				NN[NNCount] = i;
				NNCount++; 
			}
		}
		//remove all vertices which are in NN, which are adjacent to the vertex which is colored colorNumber
		for(int i = 0; i < vertices; i++) {
			if(color[i] == colorNumber) {
				for(int j = 0; j < NNCount; j++) {
					while(adjacent[i][NN[j]] == 1 && NNCount > 0) {
						NN[j] = NN[NNCount - 1];
						NNCount--;
					}
				}
			}
		}
		
	}
	private void initialise(){
		for(int i = 0; i<vertices; i++) { //initialise set of degrees to all zero, and set of colors of each vector to -1
			degree[i] = 0;
			color[i] = -1;
			for(int j = 0; j<vertices; j++) { //also initialise adjecency matrix to 0 (no neighbours)
				adjacent[i][j] = 0;
			}
		}
		for(int i = 0; i<edges; i++) { //fill in degrees of each vector and adjecency matrix
			degree[graph[i].v]++;
			degree[graph[i].u]++;
			adjacent[graph[i].v][graph[i].u] = 1;
			adjacent[graph[i].u][graph[i].v] = 1;
		}
		NNCount = 0;
		todo = vertices;
	}
	
	private int maxDegreeVertex() {
		int maxDegree = -1;
		int maxVertex = 0;
		for(int i = 0; i<vertices; i++) {
			if(color[i] == -1) { //if vertex is uncoloured
				if(degree[i]>maxDegree) { //check if degree of vertex i is larger than the current largest degree
					maxDegree = degree[i];
					maxVertex = i;
				}
			}
		}
		return maxVertex;
	}
}
