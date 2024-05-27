package model.functionality

import model.graphs.*
import kotlin.math.min

class BridgeFinder<GRAPH_TYPE, T> {
	private var discoveryTime = hashMapOf<Vertex<T>, Int>()
	private var bridges: Set<Pair<Vertex<T>, Vertex<T>>> = emptySet()
	private var parent = hashMapOf<Vertex<T>, Vertex<T>?>()
	private var low = hashMapOf<Vertex<T>, Int>()
	private var timer: Int = 0

	fun findBridges(graph: Graph<GRAPH_TYPE, T>): Set<Pair<Vertex<T>, Vertex<T>>> {
		when (graph) {
			is DirectedGraph -> {
				throw IllegalArgumentException("Directed graphs are not supported")
			}

			is DirectedWeightedGraph<*, *> -> {
				throw IllegalArgumentException("Directed graphs are not supported")
			}
		}

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

	private fun dfsRecursive(graph: Graph<GRAPH_TYPE, T>, vertex: Vertex<T>) {
		discoveryTime[vertex] = timer
		low[vertex] = timer
		timer += 1

		when (graph) {
			is UndirectedGraph -> {
				graph.getNeighbors(vertex).forEach {
					if (discoveryTime[it] == -1) {
						parent[it] = vertex
						dfsRecursive(graph, it)

						val lowVertex: Int = low[vertex] ?: -1
						val lowIt: Int = low[it] ?: -1
						val discVertex: Int = discoveryTime[vertex] ?: -1

						low[vertex] = min(lowVertex, lowIt)

						if (lowIt > discVertex) {
							bridges = bridges.plus(Pair(vertex, it))
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

			is UndirectedWeightedGraph<T, *> -> {
				graph.getNeighbors(vertex).forEach {
					if (discoveryTime[it.first] == -1) {
						parent[it.first] = vertex
						dfsRecursive(graph, it.first)

						val lowVertex: Int = low[vertex] ?: -1
						val lowIt: Int = low[it.first] ?: -1
						val discVertex: Int = discoveryTime[vertex] ?: -1

						low[vertex] = min(lowVertex, lowIt)

						if (lowIt > discVertex) {
							bridges = bridges.plus(Pair(vertex, it.first))
						}
					} else {
						if (parent[vertex] != it.first) {
							val lowVertex: Int = low[vertex] ?: -1
							val discTimeIt: Int = discoveryTime[it.first] ?: -1

							low[vertex] = min(lowVertex, discTimeIt)
						}
					}
				}
			}
		}
	}
}

