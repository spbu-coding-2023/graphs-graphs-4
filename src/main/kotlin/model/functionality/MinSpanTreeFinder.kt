package model.functionality

import model.graphs.UndirectedWeightedGraph
import model.graphs.Vertex
import model.graphs.WeightedEdge

class MinSpanTreeFinder<K, W : Number>(private val graph: UndirectedWeightedGraph<K, W>) {
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
        val neighbors = graph.getNeighbors(vertex)
        neighbors.removeIf { banList.contains(it.first) }

        val vertexWithMinWeight = neighbors.minByOrNull {
            WeightedEdge(vertex, it.first, it.second)
        } ?: return null

        val neighbor = vertexWithMinWeight.first
        val weight = vertexWithMinWeight.second
        val edge = WeightedEdge(vertex, neighbor, weight)

        return edge
    }
}
