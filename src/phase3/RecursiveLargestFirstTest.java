package phase3;

public class RecursiveLargestFirstTest 
{
	void testGraph() 
	{
		System.out.println("\nGraph Test");
		
		ReadGraph reader = new ReadGraph();
		String inputfile = "phase1-graphs/graph04_2020.txt";
		RecursiveLargestFirst rlf = new RecursiveLargestFirst();
		
		int gX = rlf.solve(reader.read(inputfile), reader.getM(), reader.getN(), "test");
	}
	
	public static void main(String[] args) 
	{
		RecursiveLargestFirstTest test = new RecursiveLargestFirstTest();
		test.testGraph();
	}
}
