package algorithms

import graphs.Graph
import graphs.Vertex
import kotlin.math.min

class BridgeFinder<T> {
	var discoveryTime = hashMapOf<Vertex<T>, Int>()
	var parent = hashMapOf<Vertex<T>, Vertex<T>?>()
	var low = hashMapOf<Vertex<T>, Int>()
	var timer : Int = 0

	fun findBridges(graph: Graph<T>) {
		for (element in graph.adjacencyList.keys) {
			discoveryTime[element] = -1
			low[element] = -1
			parent[element] = null
		}

		graph.adjacencyList.keys.forEach {
			if (discoveryTime[it] == -1) {
				timer = 0
				dfsRecursive(graph, it)
			}
		}
	}

	private fun dfsRecursive(graph: Graph<T>, vertex: Vertex<T>) {
		discoveryTime[vertex] = timer
		low[vertex] = timer
		timer += 1

		graph.adjacencyList[vertex]?.forEach {
				if (discoveryTime[it] == -1) {
					parent[it] = vertex
					dfsRecursive(graph, it)

					val lowVertex : Int = low[vertex] ?: -1
					val lowIt : Int = low[it] ?: -1
					val discVertex : Int = discoveryTime[vertex] ?: -1

					low[vertex] = min(lowVertex, lowIt)

					if (lowIt > discVertex) {
						println(Pair(it.key, vertex.key))
					}
				} else {
					if (parent[vertex] != it) {
						val lowVertex : Int = low[vertex] ?: -1
						val discTimeIt : Int = discoveryTime[it] ?: -1

						low[vertex] = min(lowVertex, discTimeIt)
					}
				}
		}
	}
}

