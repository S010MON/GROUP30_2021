package phase3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DepthFirstSearchTest {
	
	@Test
	void testTree() 
	{
		System.out.println("\nTree");
		
		/* Arrange */
		ColEdge[] tree;
		int treeN = 6;
		tree = new ColEdge[5];
		tree[0] = new ColEdge(); tree[0].v = 1; tree[0].u = 2;
		tree[1] = new ColEdge(); tree[1].v = 1; tree[1].u = 3;
		tree[2] = new ColEdge(); tree[2].v = 2; tree[2].u = 4;
		tree[3] = new ColEdge(); tree[3].v = 2; tree[3].u = 5;
		tree[4] = new ColEdge(); tree[4].v = 3; tree[4].u = 6;
		
		/* Act */
		DepthFirstSearch dfs = new DepthFirstSearch();
		System.out.println(dfs.depthFirstSearch(tree, treeN) + " SubGraphs");
		boolean actLoop = dfs.containsLoop();
		System.out.println("Loop " + actLoop);
		boolean actConnect = dfs.connected();
		System.out.println("Connected " + actConnect);
		
		/* Assert */
		boolean expLoop = false;
		boolean expConnect = true;
		assertEquals(expLoop, actLoop);
		assertEquals(expConnect, actConnect);
	}

	@Test
	void testBiGraph()
	{
		System.out.println("\nBipartite Graph");
		
		/* Arrange */
		ColEdge[] bigraph;
		int bigraphN = 8;
		bigraph = new ColEdge[5];
		bigraph[0] = new ColEdge(); bigraph[0].v = 1; bigraph[0].u = 2;
		bigraph[1] = new ColEdge(); bigraph[1].v = 2; bigraph[1].u = 3;
		bigraph[2] = new ColEdge(); bigraph[2].v = 5; bigraph[2].u = 4;
		bigraph[3] = new ColEdge(); bigraph[3].v = 6; bigraph[3].u = 7;
		bigraph[4] = new ColEdge(); bigraph[4].v = 7; bigraph[4].u = 8;
		
		/* Act */
		DepthFirstSearch dfs = new DepthFirstSearch();
		System.out.println(dfs.depthFirstSearch(bigraph, bigraphN) + " SubGraphs");
		boolean actLoop = dfs.containsLoop();
		System.out.println("Loop " + actLoop);
		boolean actConnect = dfs.connected();
		System.out.println("Connected " + actConnect);
		
		/* Assert */
		boolean expLoop = false;
		boolean expConnect = false;
		assertEquals(expLoop, actLoop);
		assertEquals(expConnect, actConnect);
	}

	@Test
	void testGraph()
	{
		System.out.println("\nGraph");
		
		/* Arrange */
		ColEdge[] graph;
		int graphN = 5;
		
		graph = new ColEdge[5];
		graph[0] = new ColEdge(); graph[0].v = 1; graph[0].u = 2; 
		graph[1] = new ColEdge(); graph[1].v = 1; graph[1].u = 3; 
		graph[2] = new ColEdge(); graph[2].v = 2; graph[2].u = 4; 
		graph[3] = new ColEdge(); graph[3].v = 2; graph[3].u = 5; 	
		graph[4] = new ColEdge(); graph[4].v = 3; graph[4].u = 5;
		
		/* Act */
		DepthFirstSearch dfs = new DepthFirstSearch();
		System.out.println(dfs.depthFirstSearch(graph, graphN) + " SubGraphs");
		boolean actLoop = dfs.containsLoop();
		System.out.println("Loop " + actLoop);
		boolean actConnect = dfs.connected();
		System.out.println("Connected " + actConnect);
		
		/* Assert */
		boolean expLoop = true;
		boolean expConnect = true;
		assertEquals(expLoop, actLoop);
		assertEquals(expConnect, actConnect);
	}

	
	@Test
	void testDiscGraph()
	{
		System.out.println("\nDisconected Graph");
		
		/* Arrange */
		ColEdge[] discgraph;
		int discgraphN = 6;
		
		discgraph = new ColEdge[6];
		discgraph[0] = new ColEdge(); discgraph[0].v = 1; discgraph[0].u = 2; 
		discgraph[1] = new ColEdge(); discgraph[1].v = 2; discgraph[1].u = 3; 
		discgraph[2] = new ColEdge(); discgraph[2].v = 3; discgraph[2].u = 1; 
		discgraph[3] = new ColEdge(); discgraph[3].v = 4; discgraph[3].u = 5; 	
		discgraph[4] = new ColEdge(); discgraph[4].v = 5; discgraph[4].u = 6;
		discgraph[5] = new ColEdge(); discgraph[5].v = 6; discgraph[5].u = 4;
		
		/* Act */
		DepthFirstSearch dfs = new DepthFirstSearch();
		System.out.println(dfs.depthFirstSearch(discgraph, discgraphN) + " SubGraphs");
		boolean actLoop = dfs.containsLoop();
		System.out.println("Loop " + actLoop);
		boolean actConnect = dfs.connected();
		System.out.println("Connected " + actConnect);
		
		/* Assert */
		boolean expLoop = true;
		boolean expConnect = false;
		assertEquals(expLoop, actLoop);
		assertEquals(expConnect, actConnect);
	}

}
