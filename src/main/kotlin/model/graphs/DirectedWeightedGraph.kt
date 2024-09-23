package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.StrConCompFinder

@Serializable
class DirectedWeightedGraph<T> : AbstractGraph<T, WeightedEdge<T>>(), GraphDirected<T, WeightedEdge<T>>, GraphWeighted<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: Double) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        adjList.getOrPut(vertex1) { HashSet() }.add(WeightedEdge(vertex1, vertex2, weight))
    }

    override fun findSCC(): Set<Set<Vertex<T>>> {
        return StrConCompFinder(this).sccSearch()
    }
}
