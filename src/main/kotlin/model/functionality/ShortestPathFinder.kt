package model.functionality

import model.graphs.GraphWeighted
import model.graphs.Vertex
import model.graphs.WeightedEdge
import kotlin.Double.Companion.NEGATIVE_INFINITY
import kotlin.Double.Companion.POSITIVE_INFINITY

class ShortestPathFinder<T>(private val graph: GraphWeighted<T>) {
    internal fun bellmanFord(start: Vertex<T>): Map<Vertex<T>, Double> {
        val dist: MutableMap<Vertex<T>, Double> = mutableMapOf()
        graph.vertices().forEach {
            dist[it] = POSITIVE_INFINITY
        }

        dist[start] = 0.0

        for (i in 1..graph.size + 1) {
            for (vertex in graph.vertices()) {
                for (edge in graph.getNeighbors(vertex)) {
                    edge as WeightedEdge

                    val distVertex = dist[vertex]
                    val distNeighbor = dist[edge.to] ?: POSITIVE_INFINITY

                    if ((distVertex != null) && (i <= graph.size)) {
                        if (distVertex + edge.weight < distNeighbor) {
                            dist[edge.to] = (distVertex + edge.weight)
                        }
                    } else if (i == graph.size + 1) {
                        if (distVertex != null) {
                            if (distVertex + edge.weight < distNeighbor) {
                                dist[edge.to] = NEGATIVE_INFINITY
                            }
                        }
                    }
                }
            }
        }

        return dist
    }

//    @Suppress("NestedBlockDepth")
//    fun dijkstra(start: Vertex<T>): Map<Vertex<T>, Double> {
//        val dist: MutableMap<Vertex<T>, Double> = mutableMapOf()
//        graph.vertices().forEach {
//            dist[it] = POSITIVE_INFINITY
//        }
//        val priorityQueue = PriorityQueue<Pair<Vertex<T>, Double>>(compareBy { it.second })
//
//        dist[start] = 0.0
//        priorityQueue.add(Pair(start, 0.0))
//
//        while (priorityQueue.isNotEmpty()) {
//            val (current, currentDist) = priorityQueue.poll()
//
//            var weight: Number
//            var neighbor: Vertex<T>
//
//            for (child in graph.getNeighbors(current)) {
//
//                @Suppress("DuplicatedCode")
//                if (child is Pair<*, *>) {
//                    weight = child.second as Number
//                    neighbor = child.first as Vertex<T>
//                } else {
//                    weight = 1
//                    neighbor = child as Vertex<T>
//                }
//
//                val next = neighbor
//                val nextDist: Double = currentDist.plus(weight).toDouble()
//
//                dist[next]?.let {
//                    if (nextDist < it) {
//                        dist[next] = nextDist
//                        priorityQueue.add(Pair(next, nextDist))
//                    }
//                }
//            }
//        }
//
//        return dist
//    }
}
