package model.functionality

import model.graphs.Vertex
import model.graphs.WeightedEdge
import model.graphs.interfaces.Graph

class ShortestPathFinder<T, NUMBER_TYPE : Comparable<NUMBER_TYPE>>(private val graph: Graph<WeightedEdge<T, NUMBER_TYPE>, T>) {
	operator fun Number.plus(other: NUMBER_TYPE): Number {
		return when {
			this is Long && other is Long -> this.toLong() + other.toLong()
			this is Int && other is Int -> this.toLong() + other.toLong()
			this is Short && other is Short -> this.toLong() + other.toLong()
			this is Double && other is Double -> this.toDouble() + other.toDouble()
			this is Float && other is Double  -> this.toDouble() + other.toDouble()
			else -> throw IllegalArgumentException("Unknown numeric type")
		}
	}

	operator fun Number.compareTo(other: Number): Int {
		return when (this) {
			is Long -> this.toLong().compareTo(other.toLong())
			is Int -> this.toInt().compareTo(other.toInt())
			is Short -> this.toShort().compareTo(other.toShort())
			is Double -> this.toDouble().compareTo(other.toDouble())
			is Float -> this.toFloat().compareTo(other.toFloat())
			else -> throw IllegalArgumentException("Unknown numeric type")
		}
	}

	@Suppress("NestedBlockDepth")
	internal fun bellmanFord(start: Vertex<T>): Map<Vertex<T>, Double> {
		TODO()
		/*
		val dist = graph.adjList.mapValues { POSITIVE_INFINITY }.toMutableMap()
		dist[start] = 0.0

		@Suppress("UnusedPrivateProperty")
		for (i in 1..graph.size) {
			for ((vertex, edges) in graph.adjList) {
				for ((neighbor, weight) in edges) {
					val distVertex = dist[vertex]
					val distNeighbor = dist[neighbor] ?: POSITIVE_INFINITY

					if (distVertex != null) {
						if (distVertex + weight < distNeighbor) {
							dist[neighbor] = (distVertex + weight).toDouble()
						}
					}
				}
			}
		}

		// Check for negative-weight cycles
		for ((vertex, edges) in graph.adjList) {
			for ((neighbor, weight) in edges) {
				val distVertex = dist[vertex]
				val distNeighbor = dist[neighbor] ?: POSITIVE_INFINITY

				if (distVertex != null) {
					if (distVertex + weight < distNeighbor) {
						dist[neighbor] = NEGATIVE_INFINITY
					}
				}
			}
		}

		return dist*/
	}
}
