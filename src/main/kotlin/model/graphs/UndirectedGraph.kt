package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.BridgeFinder

@Serializable
open class UndirectedGraph<T> : AbstractGraph<T>(), GraphUndirected<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        adjList.getOrPut(vertex1) { HashSet() }.add(UnweightedEdge(vertex2))
        adjList.getOrPut(vertex2) { HashSet() }.add(UnweightedEdge(vertex1))
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

//    override fun edges(): Set<UnweightedEdge<T>> {
//        val edges = HashSet<UnweightedEdge<T>>()
//        for (vertex in adjList.keys) {
//            for (neighbour in adjList[vertex] ?: continue) {
//                edges.add(UnweightedEdge(vertex, neighbour, null))
//            }
//        }
//
//        return edges
//    }

    override fun findMinSpanTree(): Set<Edge<T>>? {
        TODO()
    }

}
