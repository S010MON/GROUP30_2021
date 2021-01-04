package phase3;

public class BruteForceThread {

	private final static boolean DEBUG = false;
	int maxColors, id, startfrom;
	Vertex[] vertices;
	ColoringResult[] writeTo;
	/**
	 * Creates a thread with the listed variables
	 * @param maxColors Maximum number of colors 
	 * @param vertices
	 * @param startfrom
	 * @param id
	 */
	public BruteForceThread(int maxColors, Vertex[] vertices, int startfrom, int id, ColoringResult[] writeTo) {
		this.startfrom = startfrom;
		this.maxColors = maxColors;
		this.vertices = vertices;
		this.id = id;
		this.writeTo = writeTo;
	}

	/**
	 * Brute force thread main method
	 */
	public void run()
	{
		try
		{
			if (DEBUG) System.out.println("Starting thread " + getId());
			writeTo[id] = vertices[startfrom].next(vertices, maxColors);
			if (DEBUG) System.out.println("Thread " + getId() + " finished");
		}
		catch(Exception e)
		{
			System.out.println("Exception caught for thread " + getId());
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}