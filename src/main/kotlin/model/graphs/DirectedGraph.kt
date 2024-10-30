package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.DistanceRank
import model.functionality.JohnsonAlg
import model.functionality.StrConCompFinder
import model.functionality.TarjanSCC

@Serializable
class DirectedGraph<T> :
    AbstractGraph<T, UnweightedEdge<T>>(),
    GraphDirected<T, UnweightedEdge<T>> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        adjList.getOrPut(vertex1) { HashSet() }.add(UnweightedEdge(vertex1, vertex2))
    }


    override fun findCycles(startNode: Vertex<T>): HashSet<List<Vertex<T>>> {
        return JohnsonAlg(this).findCycles(startNode)
    }


    override fun addEdge(edge: UnweightedEdge<T>) {
        addEdge(edge.from, edge.to)
    }

    override fun findSCC(): Set<Set<Vertex<T>>> {
        return StrConCompFinder(this).sccSearch()
    }

//    fun distanceRank(): Map<Vertex<T>, Double> {
//        return DistanceRank<T>(this).rank()
//    }
}
