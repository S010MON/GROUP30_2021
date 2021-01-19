package phase3;

public class RecursiveLargestFirstTest 
{
	void testGraph() 
	{
		System.out.println("\nGraph Test");
		
		ReadGraph reader = new ReadGraph();
		String filename = "graph03_2020.txt";
		String path = "phase1-graphs/"+filename;
		RecursiveLargestFirst rlf = new RecursiveLargestFirst();
		int gX = rlf.solve(reader.read(filename), reader.getM(), reader.getN(), filename);
		System.out.println("Chromatic number is: " + gX);	
	}
	
	public static void main(String[] args) 
	{
		RecursiveLargestFirstTest test = new RecursiveLargestFirstTest();
		test.testGraph();
	}
}