package model.functionality

import model.graphs.Graph
import model.graphs.Vertex
import java.util.*
import kotlin.Double.Companion.NEGATIVE_INFINITY
import kotlin.Double.Companion.POSITIVE_INFINITY

@Suppress("CyclomaticComplexMethod")
class ShortestPathFinder<GRAPH_TYPE, T>(private val graph: Graph<GRAPH_TYPE, T>) {
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
		val dist: MutableMap<Vertex<T>, Double> = mutableMapOf()
		graph.vertices().forEach {
			dist[it] = POSITIVE_INFINITY
		}

		dist[start] = 0.0

		@Suppress("UnusedPrivateProperty")
		@Suppress("DuplicatedCode")
		for (i in 1..graph.size) {
			for (vertex in graph.vertices()) {
				for (neighbors in graph.getNeighbors(vertex)) {
					val weight: Number
					val neighbor: Vertex<T>

					if (neighbors is Pair<*, *>) {
						weight = neighbors.second as Number
						neighbor = neighbors.first as Vertex<T>
					} else {
						weight = 1
						neighbor = neighbors as Vertex<T>
					}


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

		@Suppress("DuplicatedCode")
		for (vertex in graph.vertices()) {
			for (neighbors in graph.getNeighbors(vertex)) {
				val weight: Number
				val neighbor: Vertex<T>

				if (neighbors is Pair<*, *>) {
					weight = neighbors.second as Number
					neighbor = neighbors.first as Vertex<T>
				} else {
					weight = 1
					neighbor = neighbors as Vertex<T>
				}

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

	@Suppress("NestedBlockDepth")
	fun dijkstra(start: Vertex<T>): Map<Vertex<T>, Double> {
		val dist: MutableMap<Vertex<T>, Double> = mutableMapOf()
		graph.vertices().forEach {
			dist[it] = POSITIVE_INFINITY
		}
		val priorityQueue = PriorityQueue<Pair<Vertex<T>, Double>>(compareBy { it.second })

		dist[start] = 0.0
		priorityQueue.add(Pair(start, 0.0))

		while (priorityQueue.isNotEmpty()) {
			val (current, currentDist) = priorityQueue.poll()

			var weight: Number
			var neighbor: Vertex<T>

			for(child in graph.getNeighbors(current)) {

				@Suppress("DuplicatedCode")
				if (child is Pair<*, *>) {
					weight = child.second as Number
					neighbor = child.first as Vertex<T>
				} else {
					weight = 1
					neighbor = child as Vertex<T>
				}

				val next = neighbor
				val nextDist: Double = currentDist.plus(weight).toDouble()

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
