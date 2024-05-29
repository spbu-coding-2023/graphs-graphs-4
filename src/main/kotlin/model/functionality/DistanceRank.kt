package model.functionality

import model.graphs.DirectedGraph
import model.graphs.Vertex
import java.util.PriorityQueue
import kotlin.math.exp
import kotlin.math.log10

class DistanceRank<T>(val graph: DirectedGraph<T>) {
    private val vertexQueue = PriorityQueue<Pair<Vertex<T>, Double>>(compareBy {it.second} )
    private val Dist = mutableMapOf<Vertex<T>, Double>().withDefault { 1e6 }
    private var size = 0.0
    private var t: Double = 0.0
    private val beta = 0.1
    private val gamma = 0.3
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

    fun rank(): Map<Vertex<T>, Double> {
        for(i in graph.adjList.keys) Dist[i] = 1e10

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
            visitedStartingVertices[vertex] = true


            size++
            t = (size / graph.adjList.keys.size)
            val alpha = exp(-t*beta)

            graph.adjList[vertex]?.forEach{child ->
                distance = (1 - alpha) * Dist[vertex]!! + alpha * newDistance
                if(startingVertices.contains(child) && !visitedStartingVertices[child]!!){
                    Dist[child] = distance
                    enqueue(child, distance)
                } else if(distance < Dist[child]!!){
                    if (Dist.getValue(child) == 1e10) {
                        enqueue(child, distance)
                    }
                    Dist[child] = distance
                }
            }

        }
        return Dist
    }
}