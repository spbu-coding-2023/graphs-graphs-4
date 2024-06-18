package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.BridgeFinder

@Serializable
open class UndirectedWeightedGraph<T> : AbstractGraph<T>(), GraphUndirected<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: Double) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        adjList.getOrPut(vertex1) { HashSet() }.add(WeightedEdge(vertex1, vertex2, weight))
        adjList.getOrPut(vertex2) { HashSet() }.add(WeightedEdge(vertex2, vertex1, weight))
    }

    override fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>> {
        return BridgeFinder<Vertex<T>, T>().findBridges(this)
    }
//    open fun addEdge(key1: T, key2: T) {
//        addEdge(Vertex(key1), Vertex(key2))
//    }

//    open fun addEdge(edge: UnweightedEdge<T>) {
//        addEdge(edge.from, edge.to)
//    }

//    open fun addEdges(vararg edges: UnweightedEdge<T>) {
//        for (edge in edges) {
//            addEdge(edge)
//        }
//    }

    override fun findMinSpanTree(): Set<Edge<T>>? {
        TODO()
    }
}
