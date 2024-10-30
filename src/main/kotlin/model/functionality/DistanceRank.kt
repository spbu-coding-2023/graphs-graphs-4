package model.functionality

import model.graphs.DirectedGraph
import model.graphs.Edge
import model.graphs.Vertex
import java.util.*
import kotlin.math.exp
import kotlin.math.log10

@Suppress("MagicNumber")
class DistanceRank<T, E: Edge<T>>(val graph: DirectedGraph<T>) {
    private val vertexQueue = PriorityQueue<Pair<Vertex<T>, Double>>(compareBy { it.second })
    private val dist = mutableMapOf<Vertex<T>, Double>().withDefault { 1e6 }
    private var size = 0.0
    private var t: Double = 0.0
    private val beta = 0.1
    private val gamma = 0.65
    private var distance = 0.0
    private val visitedStartingVertices = mutableMapOf<Vertex<T>, Boolean>().withDefault { false }

    private fun enqueue(vertex: Vertex<T>, distance: Double) {
        vertexQueue.add(Pair(vertex, distance))
    }

    private fun dequeue(): Pair<Vertex<T>, Double> {
        return vertexQueue.poll()
    }

    private fun getOutDegree(vertex: Vertex<T>): Int {
        return graph.adjList[vertex]?.size ?: 0
    }

    @Suppress("NestedBlockDepth")
    fun rank(): Map<Vertex<T>, Double> {
        for (i in graph.adjList.keys) dist[i] = 1e10

        //val allSCCs = TarjanSCC<T, E>().findSCCs(graph)
        val allSCCs = graph.findSCC()
        val startingVertices = mutableSetOf<Vertex<T>>()


        allSCCs.forEach { scc ->
            val vertex = scc.random()
            val outDegree = getOutDegree(vertex).toDouble()
            val initialDist = 1 + log10(outDegree + 1)
            enqueue(vertex, initialDist)
            dist[vertex] = initialDist
            startingVertices.add(vertex)
            visitedStartingVertices[vertex] = false
        }

        while (!vertexQueue.isEmpty()) {
            val (vertex, currentDistance) = dequeue()
            val newDistance = log10(getOutDegree(vertex).toDouble() + 1) + gamma * currentDistance
            visitedStartingVertices[vertex] = true


            size++
            t = (size / graph.adjList.keys.size)
            val alpha = exp(-t * beta)

            graph.adjList[vertex]?.forEach { child ->
                distance = (1 - alpha) * dist[vertex]!! + alpha * newDistance
                if (startingVertices.contains(child.to) && !visitedStartingVertices[child.to]!!) {
                    dist[child.to] = distance
                    enqueue(child.to, distance)
                } else if (distance < dist[child.to]!!) {
                    if (dist.getValue(child.to) == 1e10) {
                        enqueue(child.to, distance)
                    }
                    dist[child.to] = distance
                }
            }

        }
        return dist
    }
}
