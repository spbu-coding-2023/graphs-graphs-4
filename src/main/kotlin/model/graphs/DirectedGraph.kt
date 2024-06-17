package model.graphs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.functionality.DistanceRank
import model.functionality.JohnsonAlg
import model.functionality.StrConCompFinder

@Serializable
class DirectedGraph<T> : UndirectedGraph<T>() {
    @SerialName("DirectedGraph")
    override var adjList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()

    override fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        adjList.getOrPut(vertex1) { HashSet() }.add(vertex2)
    }

    override fun addEdge(key1: T, key2: T) {
        addEdge(Vertex(key1), Vertex(key2))
    }

    override fun addEdge(edge: Edge<T>) {
        addEdge(edge.from, edge.to)
    }

    override fun addEdges(vararg edges: Edge<T>) {
        for (edge in edges) {
            addEdge(edge)
        }
    }

    fun cyclesForVertex(vertex: Vertex<T>): HashSet<List<Vertex<T>>> {
        return JohnsonAlg(this).findCycles(vertex)
    }

    override fun findSCC(): Set<Set<Vertex<T>>> {
        return StrConCompFinder(this).sccSearch()
    }

    fun distanceRank(): Map<Vertex<T>, Double> {
        return DistanceRank<T>(this).rank()
    }

    override fun findMinSpanTree(): Set<GraphEdge<T>>? {
        return null
    }
}
