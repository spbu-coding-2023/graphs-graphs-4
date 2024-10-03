package model.functionality

import model.graphs.Edge
import model.graphs.GraphUndirected
import model.graphs.Vertex

class MinSpanTreeFinder<T, E: Edge<T>>(private val graph: GraphUndirected<T, E>) {
    fun mstSearch(): Set<E> {
        val spanningTreeEdges = mutableSetOf<E>()
        val spanningTreeVertices = mutableSetOf<Vertex<T>>()

        //step 1: add first vertex in spanning tree
        val firstVertex = graph.firstOrNull() ?: return spanningTreeEdges
        spanningTreeVertices.add(firstVertex)

        while (spanningTreeVertices.size != graph.size) {
            //step 2: search edge that connects different connection components
            var minEdge: E? = null

            for (vertex in spanningTreeVertices) {
                val edges = graph.edges()

                for (edge in edges) {
                    val u = edge.from
                    val v = edge.to

                    if (//we want edge that connects vertex presented in spanning tree and vertex that isn't in tree
                        ((vertex == u && !spanningTreeVertices.contains(v))
                        || (vertex == v && !spanningTreeVertices.contains(u)))
                        && (minEdge == null || minEdge > edge)
                        ) minEdge = edge
                }
            }

            //step 3: add this edge and adjacent vertex in spanning tree
            if (minEdge != null) {
                spanningTreeVertices.add(minEdge.from)
                spanningTreeVertices.add(minEdge.to)
                spanningTreeEdges.add(minEdge)
            } else { //graph isn't connected
                return emptySet()
            }
        }

        return spanningTreeEdges
    }
}
