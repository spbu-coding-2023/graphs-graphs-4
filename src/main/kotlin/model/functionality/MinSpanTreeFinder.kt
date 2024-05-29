package model.functionality

import model.graphs.Vertex
import model.graphs.WeightedEdge
import model.graphs.interfaces.Graph

class MinSpanTreeFinder<K, W: Comparable<W>>(private val graph: Graph<WeightedEdge<K, W>, K>) {
    private val spanningTree = mutableSetOf<WeightedEdge<K, W>>()


    fun mstSearch(): Set<WeightedEdge<K, W>>? {
        val firstVertex = graph.first()
        val linkedVertices = mutableSetOf<Vertex<K>>()
        linkedVertices.add(firstVertex)

        while (linkedVertices.size != graph.size) {
            val minEdge = findEdgeWithMinWeight(linkedVertices, linkedVertices)

            if (minEdge != null) {
                spanningTree.add(minEdge)
                val newTreeVertex = minEdge.to
                linkedVertices.add(newTreeVertex)
            } else {
                return null
            }
        }

        return spanningTree
    }

    private fun findEdgeWithMinWeight(vertices: Set<Vertex<K>>, banList: Set<Vertex<K>>): WeightedEdge<K, W>? {
        var minEdge: WeightedEdge<K, W>? = null
        for (vertex in vertices) {
            val edge = findAdjEdgeWithMinWeight(vertex, banList)
            if (edge != null && (minEdge == null || edge < minEdge)) {
                minEdge = edge
            }
        }

        return minEdge
    }

    private fun findAdjEdgeWithMinWeight(vertex: Vertex<K>, banList: Set<Vertex<K>>): WeightedEdge<K, W>? {
        val adjEdges = graph.getAdjEdges(vertex)
        val availableEdges = hashSetOf<WeightedEdge<K, W>>()
        for (edges in adjEdges) {
            if (!(banList.contains(edges.to) || banList.contains(edges.from))) {
                availableEdges.add(edges)
            }
        }

        val minEdge = availableEdges.minOrNull()
        /*val vertexWithMinWeight = adjEdges.minByOrNull {
            WeightedEdge(vertex, it.first, it.second)
        } ?: return null

        val neighbor = vertexWithMinWeight.first
        val weight = vertexWithMinWeight.second
        val edge = WeightedEdge(vertex, neighbor, weight)*/

        return minEdge
    }
}
