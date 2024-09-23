package model.graphs

import kotlinx.serialization.Serializable

@Serializable
open class UndirectedWeightedGraph<T> : AbstractGraph<T>(), GraphUndirected<T>, GraphWeighted<T> {
    fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: Double) {
        require(adjList.containsKey(vertex1))
        require(adjList.containsKey(vertex2))

        // для орграфов тоже надо будет реализовать дубликаты
        // избавиться от !! (?)

        val edge = adjList[vertex1]?.find { it.to == vertex2 }

        if (edge != null) {
            edge.copies += 1
            adjList[vertex2]!!.find { it.to == vertex1 }!!.copies += 1
        } else {
            adjList.getOrPut(vertex1) { HashSet() }.add(WeightedEdge(vertex1, vertex2, weight))
            adjList.getOrPut(vertex2) { HashSet() }.add(WeightedEdge(vertex2, vertex1, weight))
        }
    }
//    open fun addEdge(key1: T, key2: T) {
//        addEdge(Vertex(key1), Vertex(key2))
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
        TODO("Not yet implemented")
    }
}
