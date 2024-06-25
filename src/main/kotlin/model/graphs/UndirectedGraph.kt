package model.graphs

import kotlinx.serialization.Serializable

@Serializable
open class UndirectedGraph<T> : AbstractGraph<T>(), GraphUndirected<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        adjList.getOrPut(vertex1) { HashSet() }.add(UnweightedEdge(vertex1, vertex2))
        adjList.getOrPut(vertex2) { HashSet() }.add(UnweightedEdge(vertex2, vertex1))
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

    override fun runLeidenMethod() {
        TODO("Not yet implemented")
    }

}
