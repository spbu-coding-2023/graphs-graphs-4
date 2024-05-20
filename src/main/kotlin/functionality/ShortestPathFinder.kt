package functionality

import graphs.Vertex
import graphs.WeightedGraph
import kotlin.Double.Companion.NEGATIVE_INFINITY
import kotlin.Double.Companion.POSITIVE_INFINITY
import java.util.PriorityQueue

class ShortestPathFinder<T, NUMBER_TYPE : Number>(private val graph: WeightedGraph<T, NUMBER_TYPE>) {
	operator fun Number.plus(other: Number): Number {
		return when (this) {
			is Long -> this.toLong() + other.toLong()
			is Int -> this.toLong() + other.toLong()
			is Short -> this.toLong() + other.toLong()
			is Double -> this.toDouble() + other.toDouble()
			is Float -> this.toDouble() + other.toDouble()
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

		return dist
	}

	fun Dijkstra(start: Vertex<T>): Map<Vertex<T>, Double> {
		val dist = graph.adjList.mapValues { POSITIVE_INFINITY }.toMutableMap()
		val priorityQueue = PriorityQueue<Pair<Vertex<T>, Double>>(compareBy { it.second })

		dist[start] = 0.0
		priorityQueue.add(Pair(start, 0.0))

		while (priorityQueue.isNotEmpty()) {
			val (current, currentDist) = priorityQueue.poll()
			var flag = false
			dist[current]?.let {if(currentDist > it) flag = true}
			if (flag) continue

			graph.adjList[current]?.forEach{
				val next = it.first
				val nextDist: Double = currentDist.plus(it.second).toDouble()

				dist[next]?.let{
					if(nextDist < it){
						dist[next] = nextDist
						priorityQueue.add(Pair(next, nextDist))
					}
				}
			}
		}

		return dist


	}
}
