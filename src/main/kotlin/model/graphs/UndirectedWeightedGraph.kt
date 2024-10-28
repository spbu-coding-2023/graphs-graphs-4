package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.MinSpanTreeFinder

@Serializable
open class UndirectedWeightedGraph<T> : AbstractGraph<T, WeightedEdge<T>>(), GraphUndirected<T, WeightedEdge<T>>, GraphWeighted<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: Double) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        // для орграфов тоже надо будет реализовать дубликаты
        // избавиться от !! (?)

        val edge = adjList[vertex1]?.find { it.to == vertex2 }

        if (edge != null) {
            edge.copies += 1
            adjList[vertex2]!!.find { it.to == vertex1 }!!.copies += 1
        } else {
            adjList.getOrPut(vertex1) { HashSet() }.add(WeightedEdge(vertex1, vertex2, weight))
            adjList.getOrPut(vertex2) { HashSet() }.add(WeightedEdge(vertex2, vertex1, weight))
        }
    }

    override fun addEdge(edge: WeightedEdge<T>) {
        addEdge(edge.from, edge.to, edge.weight)
    }

    override fun findMinSpanTree(): Set<Edge<T>>? {
        return MinSpanTreeFinder(this).mstSearch()
    }

    override fun runLeidenMethod(RANDOMNESS: Double, RESOLUTION: Double): HashSet<HashSet<Vertex<T>>> {
        TODO("Not yet implemented")
    }
}
