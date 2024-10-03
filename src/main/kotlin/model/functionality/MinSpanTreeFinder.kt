package model.functionality

import model.graphs.Edge
import model.graphs.GraphUndirected
import model.graphs.UndirectedGraph

class MinSpanTreeFinder<T, E: Edge<T>>(private val graph: GraphUndirected<T, E>) {
    fun mstSearch(): Set<Edge<T>> {
        val spanningTree = UndirectedGraph<T>()
        //step 1: add any vertex in spanning tree
        val firstVertex = graph.firstOrNull() ?: return spanningTree.edges()
        spanningTree.addVertex(firstVertex)

        while (spanningTree.size != graph.size) {
            //step 2: search edge that connects different connection components
            var minEdge: Edge<T>? = null

            for (vertex in spanningTree) {
                val adjEdges = graph.getNeighbors(vertex)

                for (edge in adjEdges) {
                    if (!spanningTree.contains(edge.to) && minEdge == null) {
                        minEdge = edge
                    } else if (spanningTree.contains(edge.to) && minEdge != null) {
                        if (minEdge > edge) minEdge = edge
                    }
                }
            }

            //step 3: add this edge and adjacent vertex in spanning tree
            if (minEdge != null) {
                val minEdgeVertex = minEdge.to
                spanningTree.addVertex(minEdgeVertex)
                spanningTree.addEdge(minEdge.from, minEdge.to)
            } else { //graph isn't connected
                return emptySet()
            }
        }

        return spanningTree.edges()
    }
}
