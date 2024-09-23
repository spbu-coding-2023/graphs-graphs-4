package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.CommunityDetector

@Serializable
open class UndirectedGraph<T> : AbstractGraph<T>(), GraphUndirected<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        val edge = adjList[vertex1]?.find { it.to == vertex2 }

        if (edge != null) {
            edge.copies += 1
            adjList[vertex2]!!.find { it.to == vertex1 }!!.copies += 1
        } else {
            adjList.getOrPut(vertex1) { HashSet() }.add(UnweightedEdge(vertex1, vertex2))
            adjList.getOrPut(vertex2) { HashSet() }.add(UnweightedEdge(vertex2, vertex1))
        }
    }

    // добавляет одно конкретное ребро, пока надо только алг поиска
    // сообществ
    fun addSingleEdge(edge: UnweightedEdge<T>) {
        require(adjList.containsKey(edge.from))
        require(adjList.containsKey(edge.to))

        edge.copies += 1
        adjList.getOrPut(edge.from) { HashSet() }.add(edge)
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

    override fun runLeidenMethod(RANDOMNESS: Double, RESOLUTION: Double): HashSet<HashSet<Vertex<T>>> {
        return CommunityDetector(this, RESOLUTION, RANDOMNESS).leiden()
    }

}
