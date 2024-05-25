package model.functionality

import model.graphs.DirectedGraph
import model.graphs.TarjanAlgoVertexStats
import model.graphs.Vertex
import java.util.Stack
import kotlin.math.min

class StrConCompFinder<T>(val graph: DirectedGraph<T>) {
    fun sccSearch(): Set<Set<Vertex<T>>> {
        var index = 1
        val stack = Stack<Vertex<T>>()
        val sccSearchHelper = HashMap<Vertex<T>, TarjanAlgoVertexStats>()
        for (vertex in graph) {
            sccSearchHelper[vertex] = TarjanAlgoVertexStats()
        }

        val result = mutableSetOf<Set<Vertex<T>>>()
        fun strongConnect(vertex: Vertex<T>): Set<Vertex<T>> {
            val vertexStats = sccSearchHelper[vertex] ?: throw Exception("как лучше обработать ситуацию?")
            vertexStats.sccIndex = index
            vertexStats.lowLink = index
            vertexStats.onStack = true
            stack.push(vertex)
            index++

            for (neighbor in graph.adjList[vertex] ?: emptySet()) {
                val neighborStats = sccSearchHelper[neighbor] ?: throw Exception("как лучше обработать ситуацию?")

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
                    val visitedVertexStats = sccSearchHelper[visitedVertex] ?: TODO()
                    visitedVertexStats.onStack = false
                    scc.add(visitedVertex)
                } while (visitedVertex != vertex)
            }

            return scc
        }

        for (vertex in graph) {
            val vertexStats = sccSearchHelper[vertex] ?: TODO()

            if (vertexStats.sccIndex == 0) {
                val scc = strongConnect(vertex)

                if (scc.isNotEmpty()) {
                    result.add(scc)
                }
            }
        }

        return result
    }
}
