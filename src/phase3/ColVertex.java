package phase3;

import java.util.ArrayList;

public class ColVertex {
    
    // ArrayList of connnection
    public ArrayList<Integer> connections = new ArrayList<>();
	int color = -1;
    int id;
    /**
     * Creates a Vertex object with id {@code id}
     * @param id id
     */
    public ColVertex(int id) {
        this.id = id;
    }

    /**
     * Adds the id of a vertex to the list of connections
     * @param add Connection to add
     */
    public void append(Integer add) {
        connections.add(add);
    }

    /**
     * Converts a ColEdge[] array into a ColVertex[] array 
     * @param e Array of ColEdge objects
     * @param n Number of vertices
     * @return An array of Vertex objects
     */
    public static ColVertex[] toVertexArray(ColEdge[] e, int n) {
        // Create an array of vertices, each consisting of a list of connected vertices
        ColVertex[] vertices = new ColVertex[n + 1];

        // For each edge, connect both vertices
        for (ColEdge edge : e) {
            if (vertices[edge.u] == null) {
                vertices[edge.u] = new ColVertex(edge.u);
                vertices[edge.u].color = edge.colU;
            }
            vertices[edge.u].append(edge.v);
            if (vertices[edge.v] == null) {
                vertices[edge.v] = new ColVertex(edge.v);
                vertices[edge.v].color = edge.colV;
            }
            vertices[edge.v].append(edge.u);
        }
        return vertices;
    }

    public static int findMostConnectedUncoloredVertex(ColVertex[] v) {
        int max = 0;
        int index = 0;
        for (ColVertex colVertex : v) {
            if (colVertex != null && colVertex.color == -1 && colVertex.connections.size() > max) {
                max = colVertex.connections.size();
                index = colVertex.id;
            }
        }
        return index;
    }

    public static int findLeastSignificantInvalidVertex(ColVertex[] v) {
        int leastIndex = 0;
        int leastCount = 0;
        for (ColVertex colVertex : v) {
            if (colVertex != null && colVertex.color != -1 && !isValid(colVertex, v) && (colVertex.connections.size() < leastCount || leastCount == 0)) {
                leastIndex = colVertex.id;
                leastCount = colVertex.connections.size();
            }
        }
        return leastIndex;
    }

	private static boolean isValid(ColVertex colVertex, ColVertex[] v) {
        boolean valid = true;
        if (colVertex.color != -1) {
            for (Integer c : colVertex.connections) {
                if (v[c].color == (colVertex.color)) valid = false;
            }
        }
        return valid;
    }

	public static int findPoorlyColoredVertex(ColVertex[] vertices) {
        int[] cols = colorSignificance(vertices);
        int leastCount = 0;
        int leastIndex = 0;
        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Integer> significance = new ArrayList<>();
        sort(cols, colors, significance);
        int index = 0;
        double maxSigDif = 0.0;
        int k = 0;
        for (ColVertex v : vertices) {
            if (v != null) {
                int sv = significance.get(colors.indexOf(v.color));
                int i = 0;
                while (i < colors.size() && (!isColorableIn(v.id, vertices, colors.get(i)))) i++;
                double sigDif;
                if (i < colors.size()) {
                    sigDif = significance.get(i) - sv;
                    if (sigDif == 0 && i < colors.indexOf(v.color)) sigDif = 0.5;
                }
                else sigDif = 0;
                if (sigDif > maxSigDif) {
                    maxSigDif = sigDif;
                    index = k;
                }
            }
            k++;
        }
        return index;
	}

    // Sort by significance (desc)
    public static void sort(int[] cols, ArrayList<Integer> colors, ArrayList<Integer> significance) {
        int j = 0;
        for (int i : cols) {
            if (i != 0) {
                int k = 0;
                while (k < colors.size() && significance.get(k) >= i) k++;
                colors.add(k, j);
                significance.add(k, i);
            }
            j++;
        }
    }

    private static boolean isColorableIn(int id, ColVertex[] vertices, Integer col) {
        boolean valid = true;
        if (vertices[id] != null && vertices[id].color != -1) {
            for (Integer c : vertices[id].connections) {
                if (vertices[c].color == col) valid = false;
            }
        }
        return valid;
    }

    public static int[] colorSignificance(ColVertex[] vertices) {
        int[] colors = new int[256];
        for (ColVertex v : vertices) {
            if (v != null && v.color != -1) colors[v.color]++;
        }
        return colors;
    }


    // Brute Force Methods
    public boolean next(ColVertex[] vertices, int maxColors) {
        color = 0; // Reset the color, all possibilities have to be checked
        boolean solved = false;
        while (color <= maxColors) {
            if (verify(vertices, 1)) return true;                                                                           // If the entire graph is valid, then stop and report
            if (this != vertices[vertices.length-1]) solved = vertices[id+1].next(vertices, maxColors);                     // If this is not the last vertex, run this method on the next vertex               
            if (solved) return true;                                                                                        // If the entire graph is reported to be valid, then stop
            color++;
            if (id == 1) return false; // id 1 always has to be color 0, because colors are interchangeable (i.e. Graph {0,1,2,0} is the same as {1,2,0,1})
        }
        if (color > maxColors) color = 0;
        return false;
    }
    public static boolean verify(ColVertex[] vertices, int start) {
        boolean isVerified = true;
        // If none of the vertices have a connected vertex with the same color, then it is valid.
        for (int i = start; i < vertices.length && isVerified; i++) {
            ColVertex vertex = vertices[i];
            if (vertex.color == -1) return false;
            for (Integer edge : vertex.connections) {
                if (vertices[edge].id >= start && vertex.color == vertices[edge].color) return false;
            }
        }
        return true;
    }

    public void nextPossibility(int maxColor, ColVertex[] vertices) {
        if (color == maxColor) {
            color = 0;
            if (this != vertices[vertices.length-1]) vertices[id+1].nextPossibility(maxColor, vertices); // Pass on to the next vertex
        } else color++;
    }
}
