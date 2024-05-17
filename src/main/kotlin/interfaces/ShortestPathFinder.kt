package interfaces

import graphs.Vertex
import graphs.WeightedGraph
import kotlin.Double.Companion.NEGATIVE_INFINITY
import kotlin.Double.Companion.POSITIVE_INFINITY

class ShortestPathFinder<T>(private val graph: WeightedGraph<T>)  {
	fun bellmanFord(start: Vertex<T>): Map<Vertex<T>, Double> {
		val dist = graph.adjList.mapValues { POSITIVE_INFINITY }.toMutableMap()
		dist[start] = 0.0

		for (i in 1..graph.size) {
			for ((vertex, edges) in graph.adjList) {
				for ((neighbor, weight) in edges) {
					val distVertex = dist[vertex]
					val distNeighbor = dist[neighbor] ?: POSITIVE_INFINITY

					if (distVertex != null) {
						if (distVertex + weight < distNeighbor) {
							dist[neighbor] = distVertex + weight
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

		return dist
	}
}