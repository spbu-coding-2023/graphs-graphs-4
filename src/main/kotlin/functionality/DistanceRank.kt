package functionality

import model.graphs.DirectedGraph
import model.graphs.Vertex
import java.util.PriorityQueue
import kotlin.math.exp
import kotlin.math.log10

class DistanceRank<T>(val graph: DirectedGraph<T>) {
    private val vertexQueue = PriorityQueue<Pair<Vertex<T>, Double>>(compareBy {it.second} )
    private val Dist = mutableMapOf<Vertex<T>, Double>().withDefault { 1e6 }
    private var size = 0
    private var t: Double = 0.0
    private val beta = 0.1
    private val gamma = 0.5
    private var distance = 0.0
    private val visitedStartingVertices = mutableMapOf<Vertex<T>, Boolean>().withDefault { false }

    private fun enqueue(vertex: Vertex<T>, distance: Double){
        vertexQueue.add(Pair(vertex, distance))
    }

    private fun dequeue(): Pair<Vertex<T>, Double>{
        return vertexQueue.poll()
    }

    private fun getOutDegree(vertex: Vertex<T>): Int{
        return graph.adjList[vertex]?.size ?: 0
    }

    private fun Rank(): Map<Vertex<T>, Double> {

        val allSCCs = TarjanSCC<T>().findSCCs(graph)
        val startingVertices = mutableSetOf<Vertex<T>>()


       allSCCs.forEach{ scc ->
            val vertex = scc.random()
            val outDegree = getOutDegree(vertex).toDouble()
            val initialDist = log10(outDegree + 1)
            enqueue(vertex, initialDist)
            Dist[vertex] = initialDist
            startingVertices.add(vertex)
            visitedStartingVertices[vertex] = false
        }

        while(!vertexQueue.isEmpty()){
            val (vertex, currentDistance) = dequeue()
            val newDistance = log10(getOutDegree(vertex).toDouble() + 1) + gamma * currentDistance


            size++
            t = (size / graph.adjList.keys.size).toDouble()
            val alpha = exp(-t*beta)

            graph.adjList[vertex]?.forEach{child ->
                distance = (1 - alpha) * Dist[vertex]!! + alpha * newDistance
                if(startingVertices.contains(child) && !visitedStartingVertices[child]!!){
                    Dist[child] = distance
                    visitedStartingVertices[vertex] = true
                    enqueue(child, distance)
                } else if(distance < Dist[vertex]!!){
                    Dist[child] = distance
                    if (Dist.getValue(child) == 1e6) {
                        enqueue(child, distance)
                    }
                }
            }

        }
        return Dist
    }
}