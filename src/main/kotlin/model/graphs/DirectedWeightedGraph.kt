package model.graphs

import kotlinx.serialization.Serializable

@Serializable
class DirectedWeightedGraph<T> : AbstractGraph<T>(), GraphDirected<T>, GraphWeighted<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: Double) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        adjList.getOrPut(vertex1) { HashSet() }.add(WeightedEdge(vertex1, vertex2, weight))
    }

    override fun findSCC(): Set<Set<Vertex<T>>> {
        return emptySet()//StrConCompFinder(this as DirectedGraph<T>).sccSearch()
    }

//    override fun findMinSpanTree(): Set<Edge<T>>? {
//        return MinSpanTreeFinder(this).mstSearch()
//    }


//    override fun addEdge(key1: T, key2: T, weight: NUMBER_TYPE) {
//        addEdge(Vertex(key1), Vertex(key2), weight)
//    }
//
//    override fun addEdge(edge: WeightedEdge<T, NUMBER_TYPE>) {
//        addEdge(edge.from, edge.to, edge.weight)
//    }
//
//    override fun addEdges(vararg edges: WeightedEdge<T, NUMBER_TYPE>) {
//        for (edge in edges) {
//            addEdge(edge)
//        }
//    }
}
