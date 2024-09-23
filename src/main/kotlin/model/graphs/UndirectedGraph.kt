package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.CommunityDetector
import model.functionality.MinSpanTreeFinder

// Resolution parameter x > 0 for community detection
// Higher resolution -> more communities
const val RESOLUTION = 0.02

// Higher randomness -> more random node movements
const val RANDOMNESS = 0.001

@Serializable
open class UndirectedGraph<T> : AbstractGraph<T, UnweightedEdge<T>>(), GraphUndirected<T, UnweightedEdge<T>> {
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

    override fun findMinSpanTree(): Set<Edge<T>>? {
        return MinSpanTreeFinder(this).mstSearch()
    }

    override fun runLeidenMethod(): HashSet<HashSet<Vertex<T>>> {
        return CommunityDetector(this, RESOLUTION, RANDOMNESS).leiden()
    }

}
