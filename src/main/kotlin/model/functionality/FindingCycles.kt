package model.functionality

import model.graphs.DirectedGraph
import model.graphs.Edge
import model.graphs.GraphDirected
import model.graphs.Vertex
import java.util.*
import kotlin.math.min

class JohnsonAlg<T>(val graph: GraphDirected<T>) {
    private val stack = Stack<Vertex<T>>()
    private val blocked = mutableMapOf<Vertex<T>, Boolean>()
    private val blockedMap = mutableMapOf<Vertex<T>, MutableSet<Vertex<T>>>()
    private val allCycles = HashSet<List<Vertex<T>>>()


    fun findCycles(startVertex: Vertex<T>): HashSet<List<Vertex<T>>> {
        val relevantSCC = TarjanSCC<T>().findSCC(startVertex, graph)
        startFindCycles(startVertex, relevantSCC)
        return allCycles
    }

    private fun startFindCycles(startVertex: Vertex<T>, subgraph: HashSet<Vertex<T>>) {
        val subGraphNodes = subgraph.associateWith { vertex ->
            graph.getNeighbors(vertex).filter { subgraph.contains(it.to) } ?: listOf()
        }
        subgraph.forEach { node ->
            blocked[node] = false
            blockedMap[node] = mutableSetOf()
        }
        dfsCycleFind(startVertex, startVertex, subGraphNodes)
    }

    private fun dfsCycleFind(start: Vertex<T>, current: Vertex<T>, subGraph: Map<Vertex<T>, List<Edge<T>>>): Boolean {
        stack.add(current)
        blocked[current] = true
        var foundCycle = false

        for (neighbor in subGraph[current] ?: emptyList()) {
            if (neighbor.to == start && stack.size > 1) {
                allCycles.add(ArrayList(stack))
                foundCycle = true
            } else if (blocked[neighbor.to] == false) {
                val gotCycle = dfsCycleFind(start, neighbor.to, subGraph)
                foundCycle = foundCycle || gotCycle
            }
        }

        if (foundCycle) unblock(current)
        else {
            for (neighbor in subGraph[current] ?: emptyList()) {
                if (!blockedMap[neighbor.to]!!.contains(current)) {
                    blockedMap[neighbor.to]!!.add(current)
                }
            }
        }
        stack.pop()
        return foundCycle
    }


    /*private fun processUnblocking(current: Vertex<T>) {
        val queue = ArrayDeque<Vertex<T>>()
        stack.pop()
        queue.add(current)

        while (queue.isNotEmpty()) {
            val vertex = queue.removeFirst()
            blocked[vertex] = false

            blockedMap[vertex]?.forEach { dependent ->
                if (blockedMap[dependent]?.all { blocked[it] == false } == true) {
                    queue.add(dependent)
                }
            }
            blockedMap[vertex]?.clear()
        }*/

    private fun unblock(vertex: Vertex<T>) {
        blocked[vertex] = false
        if (blockedMap[vertex]?.size != 0) {
            blockedMap[vertex]?.forEach {
                if (blocked[it] == true) unblock(it)
            }
        }
        blockedMap[vertex]?.clear()
    }


}

class TarjanSCC<T> {
    val stack = Stack<Vertex<T>>()
    val num = mutableMapOf<Vertex<T>, Int>()
    val lowest = mutableMapOf<Vertex<T>, Int>()
    val visited = hashSetOf<Vertex<T>>()
    val processed = hashSetOf<Vertex<T>>()
    var curIndex = 1

    fun findSCC(vertex: Vertex<T>, graph: GraphDirected<T>): HashSet<Vertex<T>> {
        return dfsTarjan(vertex, graph)
    }

    fun containsInAnySCC(allSCCs: HashSet<HashSet<Vertex<T>>>, v: Vertex<T>): Boolean {
        for (scc in allSCCs) {
            if (scc.contains(v)) return false
        }
        return true
    }

    fun findSCCs(graph: GraphDirected<T>): HashSet<HashSet<Vertex<T>>> {
        val allSCCs: HashSet<HashSet<Vertex<T>>> = HashSet<HashSet<Vertex<T>>>()
        for (v in graph.vertices()) {
            if (containsInAnySCC(allSCCs, v)) allSCCs.add(dfsTarjan(v, graph))
        }
        return allSCCs
    }


    fun dfsTarjan(vertex: Vertex<T>, graph: GraphDirected<T>): HashSet<Vertex<T>> {
        num[vertex] = curIndex
        lowest[vertex] = curIndex
        curIndex++
        stack.add(vertex)
        visited.add(vertex)

        graph.getNeighbors(vertex).forEach {
            if (!stack.contains(it.to)) {
                dfsTarjan(it.to, graph)
                lowest[vertex] = min(lowest[vertex]!!, lowest[it.to]!!)
                //As they say it's not recommended
            } else if (stack.contains(it.to)) {
                lowest[vertex] = min(lowest[vertex]!!, num[it.to]!!)
                //The same situation hier
            }
        }
        processed.add(vertex)

        val scc: HashSet<Vertex<T>> = HashSet<Vertex<T>>()
        if (lowest[vertex] == num[vertex]) {
            var sccVertex: Vertex<T>
            do {
                sccVertex = stack.pop()
                scc.add(sccVertex)
            } while (sccVertex != vertex)
        }

        return scc
    }
}
