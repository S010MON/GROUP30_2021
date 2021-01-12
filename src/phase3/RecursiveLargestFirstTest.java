package phase3;

public class RecursiveLargestFirstTest {
	void testGraph() {
		System.out.println("\nGraph Test");
		ColEdge[] graph;
		int graphN = 6;
		 
		graph = new ColEdge[6];
		graph[0] = new ColEdge(); graph[0].v = 0; graph[0].u = 1; 
		graph[1] = new ColEdge(); graph[1].v = 1; graph[1].u = 2; 
		graph[2] = new ColEdge(); graph[2].v = 2; graph[2].u = 3; 
		graph[3] = new ColEdge(); graph[3].v = 1; graph[3].u = 4; 	
		graph[4] = new ColEdge(); graph[4].v = 2; graph[4].u = 4;
		graph[5] = new ColEdge(); graph[5].v = 2; graph[5].u = 5;
		
		RecursiveLargestFirst rlf = new RecursiveLargestFirst();
		System.out.println("Chromatic number is: "+rlf.solve(graph, 5, 6));
		
	}
	public static void main(String[] args) {
		RecursiveLargestFirstTest test = new RecursiveLargestFirstTest();
		test.testGraph();
	}
}
