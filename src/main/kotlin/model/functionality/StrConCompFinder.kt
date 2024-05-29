package model.functionality

import model.graphs.TarjanAlgoVertexStats
import model.graphs.UndirectedGraph
import model.graphs.Vertex
import java.util.*
import kotlin.math.min

class StrConCompFinder<T>(private val graph: UndirectedGraph<T>) {
    private val strConCompSet = mutableSetOf<Set<Vertex<T>>>()

    fun sccSearch(): Set<Set<Vertex<T>>> {
        var index = 1
        val stack = Stack<Vertex<T>>()
        val sccSearchHelper = HashMap<Vertex<T>, TarjanAlgoVertexStats>()
        for (vertex in graph) {
            sccSearchHelper[vertex] = TarjanAlgoVertexStats()
        }

        fun strongConnect(vertex: Vertex<T>): Set<Vertex<T>> {
            val vertexStats = sccSearchHelper[vertex]
                ?: throw IllegalArgumentException("$vertex vertex does not presented in graph.")
            vertexStats.sccIndex = index
            vertexStats.lowLink = index
            vertexStats.onStack = true
            stack.push(vertex)
            index++

            val neighbors = graph.getNeighbors(vertex)
            for (neighbor in neighbors) {
                val neighborStats = sccSearchHelper[neighbor]
                    ?: throw IllegalArgumentException("$neighbor vertex does not presented in graph.")

                if (sccSearchHelper[neighbor]?.sccIndex == 0) {
                    strongConnect(neighbor)
                    vertexStats.lowLink = min(vertexStats.lowLink, neighborStats.lowLink)
                } else if (neighborStats.onStack) {
                    vertexStats.lowLink = min(vertexStats.lowLink, neighborStats.sccIndex)
                }
            }

            val scc = mutableSetOf<Vertex<T>>()
            if (vertexStats.lowLink == vertexStats.sccIndex) {
                do {
                    val visitedVertex = stack.pop()
                    val visitedVertexStats = sccSearchHelper[visitedVertex]
                        ?: throw IllegalArgumentException("$visitedVertex vertex does not presented in graph.")
                    visitedVertexStats.onStack = false
                    scc.add(visitedVertex)
                } while (visitedVertex != vertex)
            }

            return scc
        }

        for (vertex in graph) {
            val vertexStats = sccSearchHelper[vertex]
                ?: throw IllegalArgumentException("$vertex vertex does not presented in graph.")

            if (vertexStats.sccIndex == 0) {
                val scc = strongConnect(vertex)

                if (scc.isNotEmpty()) {
                    strConCompSet.add(scc)
                }
            }
        }

        return strConCompSet
    }
}
