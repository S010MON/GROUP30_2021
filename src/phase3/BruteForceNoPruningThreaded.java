package phase3;

/**
 * A Simple brute force algorithm that runs over every single possibility of how to color the graph.
 * It is extremely slow, however the chromatic number is guaranteed to be correct.
 * This version uses threading, which increases efficiency significantly.
 * 
 * @author I. Heijnens
 * @version 1.0
 */

import java.util.*;

public class BruteForceNoPruningThreaded extends GraphColouringAlgorithm {
    
    public BruteForceNoPruningThreaded() {
        bound = Bound.UPPER;
    }

    /**
	 * Computes the CHROMATIC NUMBER of a graph
	 * @param e	Array of edges.
	 * @param m	Number of vertices.
	 * @param n Number of edges.
     * @param fileName Number of edges.
	 * @return CHROMATIC NUMBER
	 */
    public int solve(ColEdge[] e, int m, int n, String inputfile) {
        e = ColEdge.copyEdges(e);
        long startTime = System.nanoTime();
        int maxColors = 2;
        ColVertex[] vertices = toVertexArray(e, n);
        int i = 0;
        boolean ok = false;
        // Set all the colors to 0
        for (ColVertex v : vertices) {
            if (v == null) v = vertices[i] = new ColVertex(i);
            v.color = 0;
            i++;
        }
        vertices[0].color = -2; // This is to prevent any potential issues with the verification
        while (!ok) {
            i = 0;
            
            // Try to solve using maxColors colors, if it fails, try again with maxColors+1
            ok = attemptBruteForce(maxColors, vertices);
            Colour.set(Bound.LOWER, maxColors + 1);

            if (!ok)
                maxColors++;
        }
        double time = (System.nanoTime() - startTime)/1000000.0;
        if (Colour.DEBUG) System.out.println("Chromatic number: " + (maxColors + 1));
        if (Colour.DEBUG) System.out.println("Time needed: " + (time + " ms"));
        Logger.logResults("BruteForceNoPruning", inputfile , maxColors + 1, time);
        Colour.set(bound, maxColors + 1);
        return maxColors + 1;
    }

    /**
     * Start the brute force with threading algorithm
     * @param maxColors Maximum number of colors
     * @param vertices Array of Vertex objects
     * @return {@code true} if it is possible to color the graph using {@code maxColors} colors
     * {@code false} if it is not possible to color the graph using {@code maxColors} colors
     */
    private static boolean attemptBruteForce(int maxColors, ColVertex[] vertices) {
        boolean result = threadedBruteForce(maxColors, vertices);
        if (Colour.DEBUG) System.out.println("Tried " + (maxColors + 1) + " colors: " + result);
        return result;
    }

    /**
     * Runs the brute force algorithm spread across multiple threads
     * Start the brute force with threading algorithm
     * @param maxColors Maximum number of colors
     * @param vertices Array of Vertex objects
     * @return {@code true} if it is possible to color the graph using {@code maxColors} colors
     * {@code false} if it is not possible to color the graph using {@code maxColors} colors
     */
    private static boolean threadedBruteForce(int maxColors, ColVertex[] vertices) {    
        int threadlevels;
        if (maxColors >= 1) threadlevels = (int) (Math.log10(Runtime.getRuntime().availableProcessors() * 2) / Math.log10(maxColors+1)); // Max 2 threads per processor
        else threadlevels = 1;
        if (threadlevels + 2 > vertices.length) threadlevels = Math.max(1,vertices.length - 2);
        BruteForceThread[] threads = new BruteForceThread[(int) (Math.pow(maxColors + 1, threadlevels))];
        Boolean[] threadResults = new Boolean[threads.length];
        int i = 0;
        for (int j = 0; j < threads.length; j++) {
            (threads[i] = new BruteForceThread(maxColors,  fullCopy(vertices), threadlevels + 1, i, threadResults)).start();
            i++;
            if (i < threads.length) vertices[2].nextPossibility(maxColors, vertices);
        }
        for (int j = 0; j < vertices.length; j++) {
            while (!allFalse(threadResults)) if (hasTrue(threadResults)) return true; // Unless a thread has evaluated to true, wait for all threads to evaluate to false
        }
        return false;
    }
    

    /**
     * Creates a full copy of a vertex with all vertices copied
     * @param vertices Array of Vertex objects
     * @return A full copy of a vertex with all vertices copied
     */
    private static ColVertex[] fullCopy(ColVertex[] vertices) {
        ColVertex[] nv = new ColVertex[vertices.length];
        int i = 0;
        for (ColVertex vertex : vertices) {
            nv[i] = new ColVertex(i);
            nv[i].color = vertex.color;
            nv[i].connections = vertex.connections;
            i++;
        }
        return nv;
    }

    /**
     * Checks whether an array of Booleans has a true value
     * @param arr Array of Boolean objects
     * @return {@code true} if {@code arr} contains a true value
     * {@code false} if {@code arr} does not contain a true value
     */
    private static boolean hasTrue(Boolean[] arr) {
        for (Boolean boolean1 : arr) {
            if (boolean1 != null && boolean1 == true) return true;
        }
        return false;
    }
    /**
     * Checks whether an array of Booleans has a true value
     * @param arr Array of Boolean objects
     * @return {@code true} if all values in {@code arr} are false
     * {@code false} if not all values in {@code arr} are false
     */
    private static boolean allFalse(Boolean[] arr) {
        for (Boolean boolean1 : arr) {
            if (boolean1 == null || boolean1 != false) return false;
        }
        return true;
    }

    /**
     * Recursive method of the brute force algorithm
     * 
     * @param e Array of ColEdge objects
     * @param n Number of vertices
     * @return An array of Vertex objects
     * 
     */
    private static ColVertex[] toVertexArray(ColEdge[] e, int n) {
        // Create an array of vertices, each consisting of a list of connected vertices
        ColVertex[] vertices = new ColVertex[n + 1];

        // For each edge, connect both vertices
        for (ColEdge edge : e) {
            if (vertices[edge.u] == null)
                vertices[edge.u] = new ColVertex(edge.u);
            vertices[edge.u].append(edge.v);
            if (vertices[edge.v] == null)
                vertices[edge.v] = new ColVertex(edge.v);
            vertices[edge.v].append(edge.u);
        }
        return vertices;
    }
}
