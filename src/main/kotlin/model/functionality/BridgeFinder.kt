package model.functionality

import model.graphs.Edge
import model.graphs.GraphUndirected
import model.graphs.Vertex
import kotlin.math.min

class BridgeFinder<T, E: Edge<T>> {
    private var discoveryTime = hashMapOf<Vertex<T>, Int>()
    private var bridges: Set<E> = emptySet()
    private var parent = hashMapOf<Vertex<T>, Vertex<T>?>()
    private var low = hashMapOf<Vertex<T>, Int>()
    private var timer: Int = 0

    fun findBridges(graph: GraphUndirected<T, E>): Set<E> {
        for (element in graph.vertices()) {
            discoveryTime[element] = -1
            low[element] = -1
            parent[element] = null
        }

        graph.vertices().forEach {
            if (discoveryTime[it] == -1) {
                timer = 0
                dfsRecursive(graph, it)
            }
        }

        return bridges
    }

    private fun dfsRecursive(graph: GraphUndirected<T, E>, vertex: Vertex<T>) {
        discoveryTime[vertex] = timer
        low[vertex] = timer
        timer += 1

        graph.getNeighbors(vertex).forEach {
            if (discoveryTime[it.to] == -1) {
                parent[it.to] = vertex
                dfsRecursive(graph, it.to)

                val lowVertex: Int = low[vertex] ?: -1
                val lowIt: Int = low[it.to] ?: -1
                val discVertex: Int = discoveryTime[vertex] ?: -1

                low[vertex] = min(lowVertex, lowIt)

                if (lowIt > discVertex) {
                    bridges = bridges.plus(it)
                }
            } else {
                if (parent[vertex] != it.to) {
                    val lowVertex: Int = low[vertex] ?: -1
                    val discTimeIt: Int = discoveryTime[it.to] ?: -1

                    low[vertex] = min(lowVertex, discTimeIt)
                }
            }
        }
    }
}
