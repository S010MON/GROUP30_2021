package phase3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ColourTest 
{
	String algo = "bt";	// Set to null for auto

	/* choose from:
	 * bf 	- Brute Force
	 * g	- Greedy
	 * bt 	- Back Tracking
	 * d	- DSATUR
	 * s	- 3 SAT
	 */
	
	String[][] phase1 = {
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph00_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph01_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph02_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph03_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph04_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph05_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph06_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph07_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph08_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph09_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph10_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph11_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph12_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph13_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph14_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph15_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph16_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph17_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph18_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph19_2020.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase1-graphs/graph20_2020.txt",algo},	
	};
	
	String[][] phase3 = {
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph01.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph02.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph03.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph04.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph05.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph06.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph07.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph08.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph09.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph10.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph11.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph12.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph13.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph14.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph15.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph16.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph17.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph18.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph19.txt",algo},
			{"/home/leon/JavaWorkspace/GROUP30_2021/src/phase3-graphs/phase3_2020_graph20.txt",algo},	
	};
	
	
	@Test
	void test() 
	{
		Colour.main(phase1[10]);
	}

}