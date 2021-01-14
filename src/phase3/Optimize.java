// package phase3;

// public class Optimize {

// 	protected void optimize() {

// 		ColVertex[] vertices = ColVertex.toVertexArray(gd.getColEdges(), gd.getVertexCount());
		
// 		// Show least significant invalid vertex
// 		int lsiv = ColVertex.findLeastSignificantInvalidVertex(vertices);
// 		if (lsiv != 0) {
// 			color(lsiv, vertices);
// 			return;
// 		}

// 		// Show most significant uncolored vertex
// 		int mcv = ColVertex.findMostConnectedUncoloredVertex(vertices);
// 		if (mcv != 0) {
// 			recolor(mcv, vertices);
// 			return;
// 		}

// 		// Show bad colorings
// 		int pcv = ColVertex.findPoorlyColoredVertex(vertices);
// 		if (pcv != 0) {
// 			remove(pcv, vertices);
// 			return;
// 		}
// 	}
// }
