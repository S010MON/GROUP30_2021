package phase3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DepthFirstSearchTest {
	
	private static ColEdge[] tree;
	private static int treeN = 6;
	private static ColEdge[] bigraph;
	private static int bigraphN = 7;
	private static ColEdge[] graph;
	private static int graphN = 6;

	
	@BeforeAll
	static void setup()
	{
		tree = new ColEdge[5];
		tree[0] = new ColEdge(); tree[0].v = 1; tree[0].u = 2;
		tree[1] = new ColEdge(); tree[1].v = 1; tree[1].u = 3;
		tree[2] = new ColEdge(); tree[2].v = 2; tree[2].u = 4;
		tree[3] = new ColEdge(); tree[3].v = 2; tree[3].u = 5;
		tree[4] = new ColEdge(); tree[4].v = 3; tree[4].u = 6;
		System.out.println("Tree Built");
		
		bigraph = new ColEdge[5];
		bigraph[0] = new ColEdge(); bigraph[0].v = 1; bigraph[0].u = 2;
		bigraph[1] = new ColEdge(); bigraph[1].v = 3; bigraph[1].u = 2;
		bigraph[2] = new ColEdge(); bigraph[2].v = 5; bigraph[2].u = 4;
		bigraph[3] = new ColEdge(); bigraph[3].v = 7; bigraph[3].u = 4;
		bigraph[4] = new ColEdge(); bigraph[4].v = 3; bigraph[4].u = 6;
		
		graph = new ColEdge[5];
		graph[0] = new ColEdge(); graph[0].v = 1; graph[0].u = 2; 
		graph[1] = new ColEdge(); graph[1].v = 1; graph[1].u = 3; 
		graph[2] = new ColEdge(); graph[2].v = 2; graph[2].u = 4; 
		graph[3] = new ColEdge(); graph[3].v = 2; graph[3].u = 5; 	
		graph[4] = new ColEdge(); graph[4].v = 3; graph[4].u = 5;
	}
	
	@Test
	void test() 
	{
		DepthFirstSearch DFS = new DepthFirstSearch(bigraph, bigraphN);
	}

}
