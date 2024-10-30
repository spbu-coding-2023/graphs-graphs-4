package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.JohnsonAlg

@Serializable
class DirectedGraph<T> : AbstractGraph<T>(), GraphDirected<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        adjList.getOrPut(vertex1) { HashSet() }.add(UnweightedEdge(vertex1, vertex2))
    }

    override fun findCycles(startNode: Vertex<T>): HashSet<List<Vertex<T>>> {
        return JohnsonAlg<T>(this).findCycles(startNode)
    }

//    fun addEdge(key1: T, key2: T) {
//        addEdge(Vertex(key1), Vertex(key2))
//    }

//    fun addEdge(edge: UnweightedEdge<T>) {
//        addEdge(edge.from, edge.to)
//    }

//    override fun addEdges(vararg edges: UnweightedEdge<T>) {
//        for (edge in edges) {
//            addEdge(edge)
//        }
//    }

    override fun findSCC(): Set<Set<Vertex<T>>> {
        TODO("Not yet implemented")
//        return StrConCompFinder(this).sccSearch()
    }

//    fun distanceRank(): Map<Vertex<T>, Double> {
//        return DistanceRank<T>(this).rank()
//    }
}
