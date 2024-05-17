package interfaces

import graphs.UndirectedGraph
import graphs.Vertex
import kotlin.math.min

class BridgeFinder<T> {
	private var discoveryTime = hashMapOf<Vertex<T>, Int>()
	private var bridges: Set<Pair<Vertex<T>, Vertex<T>>> = emptySet()
	private var parent = hashMapOf<Vertex<T>, Vertex<T>?>()
	private var low = hashMapOf<Vertex<T>, Int>()
	private var timer: Int = 0

	fun findBridges(graph: UndirectedGraph<T>): Set<Pair<Vertex<T>, Vertex<T>>> {
		for (element in graph.adjList.keys) {
			discoveryTime[element] = -1
			low[element] = -1
			parent[element] = null
		}

		graph.adjList.keys.forEach {
			if (discoveryTime[it] == -1) {
				timer = 0
				dfsRecursive(graph, it)
			}
		}

		return bridges
	}

	private fun dfsRecursive(graph: UndirectedGraph<T>, vertex: Vertex<T>) {
		discoveryTime[vertex] = timer
		low[vertex] = timer
		timer += 1

		graph.adjList[vertex]?.forEach {
			if (discoveryTime[it] == -1) {
				parent[it] = vertex
				dfsRecursive(graph, it)

				val lowVertex: Int = low[vertex] ?: -1
				val lowIt: Int = low[it] ?: -1
				val discVertex: Int = discoveryTime[vertex] ?: -1

				low[vertex] = min(lowVertex, lowIt)

				if (lowIt > discVertex) {
					bridges = bridges.plus(Pair(it, vertex))
				}
			} else {
				if (parent[vertex] != it) {
					val lowVertex: Int = low[vertex] ?: -1
					val discTimeIt: Int = discoveryTime[it] ?: -1

					low[vertex] = min(lowVertex, discTimeIt)
				}
			}
		}
	}
}

