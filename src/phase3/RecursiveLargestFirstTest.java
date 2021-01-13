package phase3;

public class RecursiveLargestFirstTest 
{
	void testGraph() 
	{
		System.out.println("\nGraph Test");
		
		ReadGraph reader = new ReadGraph();
		String inputfile = "/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph00_2020.txt";
		RecursiveLargestFirst rlf = new RecursiveLargestFirst();
		
		int gX = rlf.solve(reader.read(inputfile), reader.getM(), reader.getN());
		System.out.println("Chromatic number is: " + gX);	
	}
	
	public static void main(String[] args) 
	{
		RecursiveLargestFirstTest test = new RecursiveLargestFirstTest();
		test.testGraph();
	}
}
