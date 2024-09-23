package model.functionality

import model.graphs.Edge
import model.graphs.GraphUndirected
import model.graphs.UndirectedGraph

class MinSpanTreeFinder<T, E: Edge<T>>(private val graph: GraphUndirected<T, E>) {
    fun mstSearch(): Set<Edge<T>> {
        val spanningTree = UndirectedGraph<T>()

        val firstVertex = graph.firstOrNull() ?: return spanningTree.edges()
        spanningTree.addVertex(firstVertex)

        while (spanningTree.size != graph.size) {
            fun findEdgeWithMinWeight(): Edge<T>? {
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

                return minEdge
            }

            val minEdge = findEdgeWithMinWeight()

            if (minEdge != null) {
                val minEdgeVertex = minEdge.to
                spanningTree.addVertex(minEdgeVertex)
                spanningTree.addEdge(minEdge.from, minEdge.to)
            } else {
                return emptySet()
            }
        }

        return spanningTree.edges()
    }
}
