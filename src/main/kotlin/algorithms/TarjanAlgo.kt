package algorithms

import graphs.Graph
import graphs.Vertex
import java.util.Stack
import kotlin.math.min

fun <K>sccSearch(graph: Graph<K>): Array<Array<Vertex<K>>> {
    var index = 1
    val stack = Stack<Vertex<K>>()
    val sccList = arrayOf(arrayOf<Vertex<K>>())

    fun strongConnect(v: Vertex<K>): Array<Vertex<K>> {
        v.sccIndex = index
        v.lowLink = index
        index++
        stack.push(v)
        v.onStack = true

        for (w in graph.giveNeighbors(v) ?: emptySet()) {
            if (w.sccIndex == 0) {
                strongConnect(w)
                v.lowLink = min(v.lowLink, w.lowLink)
            } else if (w.onStack) {
                v.lowLink = min(v.lowLink, w.sccIndex)
            }
        }

        val scc = arrayOf<Vertex<K>>()
        if (v.lowLink == v.sccIndex) {
            do {
                val w = stack.pop()
                w.onStack = false
                scc.plusElement(w)
            } while (w != v)
        }
        return scc
    }

    for (vertex in graph.adjacencyList.keys) {
        if (vertex.sccIndex == 0) {
            val scc = strongConnect(vertex)
            if (scc.isNotEmpty()) {
                sccList.plusElement(scc)
            }
        }
    }

    return sccList
}
