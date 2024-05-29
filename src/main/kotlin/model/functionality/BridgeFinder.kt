package model.functionality

import model.graphs.*
import model.graphs.interfaces.Graph
import model.graphs.interfaces.GraphEdge
import kotlin.math.min

class BridgeFinder<E: GraphEdge<T>, T> {
	private var discoveryTime = hashMapOf<Vertex<T>, Int>()
	private var bridges: Set<Pair<Vertex<T>, Vertex<T>>> = emptySet()
	private var parent = hashMapOf<Vertex<T>, Vertex<T>?>()
	private var low = hashMapOf<Vertex<T>, Int>()
	private var timer: Int = 0

	fun findBridges(graph: Graph<E, T>): Set<Pair<Vertex<T>, Vertex<T>>> {
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

	private fun dfsRecursive(graph: Graph<E, T>, vertex: Vertex<T>) {
		discoveryTime[vertex] = timer
		low[vertex] = timer
		timer += 1

		when (graph) {
			is UndirectedGraph -> {
				graph.getAdjEdges(vertex).forEach { edge ->
					val neighbor = edge.to
					if (discoveryTime[neighbor] == -1) {
						parent[neighbor] = vertex
						dfsRecursive(graph, neighbor)

						val lowVertex: Int = low[vertex] ?: -1
						val lowIt: Int = low[neighbor] ?: -1
						val discVertex: Int = discoveryTime[vertex] ?: -1

						low[vertex] = min(lowVertex, lowIt)

						if (lowIt > discVertex) {
							bridges = bridges.plus(Pair(vertex, neighbor))
						}
					} else {
						if (parent[vertex] != neighbor) {
							val lowVertex: Int = low[vertex] ?: -1
							val discTimeIt: Int = discoveryTime[neighbor] ?: -1

							low[vertex] = min(lowVertex, discTimeIt)
						}
					}
				}
			}

			is UndirectedWeightedGraph<T, *> -> {
				graph.getAdjEdges(vertex).forEach { edge ->
					val neighbor = edge.from
					if (discoveryTime[neighbor] == -1) {
						parent[neighbor] = vertex
						dfsRecursive(graph, neighbor)

						val lowVertex: Int = low[vertex] ?: -1
						val lowIt: Int = low[neighbor] ?: -1
						val discVertex: Int = discoveryTime[vertex] ?: -1

						low[vertex] = min(lowVertex, lowIt)

						if (lowIt > discVertex) {
							bridges = bridges.plus(Pair(vertex, neighbor))
						}
					} else {
						if (parent[vertex] != neighbor) {
							val lowVertex: Int = low[vertex] ?: -1
							val discTimeIt: Int = discoveryTime[neighbor] ?: -1

							low[vertex] = min(lowVertex, discTimeIt)
						}
					}
				}
			}
		}
	}
}
