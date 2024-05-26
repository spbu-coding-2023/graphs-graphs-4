package model.functionality

import model.graphs.Vertex
import model.graphs.WeightedEdge
import model.graphs.WeightedGraph

class MinSpanTreeFinder<K, W: Number>(private val graph: WeightedGraph<K, W>) {
    private val spanningTree = mutableSetOf<WeightedEdge<K, W>>()


    fun mstSearch(): Set<WeightedEdge<K, W>>? {
        val firstVertex = graph.first()
        val linkedVertices = mutableSetOf<Vertex<K>>()
        linkedVertices.add(firstVertex)

        while (linkedVertices.size != graph.size) {
            val minEdge = findEdgeWithMinWeight(linkedVertices, linkedVertices)

            if (minEdge != null) {
                spanningTree.add(minEdge)
                val newTreeVertex = Vertex(minEdge.to)
                linkedVertices.add(newTreeVertex)
            } else {
                return null
            }
        }

        return spanningTree
    }

    fun findEdgeWithMinWeight(vertices: Set<Vertex<K>>, banList: MutableSet<Vertex<K>>): WeightedEdge<K, W>? {
        var minEdge: WeightedEdge<K, W>? = null
        for (vertex in vertices) {
            val edge = findAdjEdgeWithMinWeight(vertex, banList)
            if (edge != null && (minEdge == null || edge < minEdge)) {
                minEdge = edge
            }
        }

        return minEdge
    }

    fun findAdjEdgeWithMinWeight(vertex: Vertex<K>, banList: MutableSet<Vertex<K>>): WeightedEdge<K, W>? {
        val verticesWithWeights = graph.getNeighbors(vertex)
        verticesWithWeights.removeIf { banList.contains(it.first) }

        val vertexWithMinWeight = verticesWithWeights.minByOrNull {
            WeightedEdge(vertex, it.first, it.second)
        } ?: return null

        val vertexKey = vertex.key
        val neighborKey = vertexWithMinWeight.first.key
        val weight = vertexWithMinWeight.second
        val edge = WeightedEdge(vertexKey, neighborKey, weight)
        return edge
    }
}
