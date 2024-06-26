package model.functionality

import model.graphs.UndirectedGraph
import model.graphs.Vertex
import java.util.Stack
import kotlin.math.min

fun <K>sccSearch(graph: UndirectedGraph<K>): Array<Array<Vertex<K>>> {
    var index = 1
    val stack = Stack<Vertex<K>>()
    val result = arrayOf(arrayOf<Vertex<K>>())
    val sccSearchHelper = HashMap<Vertex<K>, TarjanAlgoVertexStats>()
    for (vertex in graph) {
        sccSearchHelper[vertex] = TarjanAlgoVertexStats()
    }

    fun strongConnect(vertex: Vertex<K>): Array<Vertex<K>> {
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

        val scc = arrayOf<Vertex<K>>()
        if (vertexStats.lowLink == vertexStats.sccIndex) {
            do {
                val visitedVertex = stack.pop()
                val visitedVertexStats = sccSearchHelper[visitedVertex] ?: TODO()
                visitedVertexStats.onStack = false
                scc.plusElement(visitedVertex)
            } while (visitedVertex != vertex)
        }

        return scc
    }

    for (vertex in graph) {
        val vertexStats = sccSearchHelper[vertex] ?: TODO()

        if (vertexStats.sccIndex == 0) {
            val scc = strongConnect(vertex)

            if (scc.isNotEmpty()) {
                result.plusElement(scc)
            }
        }
    }

    return result
}
