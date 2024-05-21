package functionality

import java.util.Stack
import graphs.Vertex
import graphs.AbstractGraph
import kotlin.math.min

class JohnsonAlg<T>(val graph: AbstractGraph<Vertex<T>, T>) {
    private val stack = Stack<Vertex<T>>()
    private val blocked = mutableMapOf<Vertex<T>, Boolean>()
    private val blockedMap = mutableMapOf<Vertex<T>, MutableSet<Vertex<T>>>()
    private val allCycles = HashSet<List<Vertex<T>>>()


    fun findCycles(startVertex: Vertex<T>): HashSet<List<Vertex<T>>> {
        val relevantSCC = TarjanSCC<T>().findSCC(startVertex, graph)
        relevantSCC.let {
            startFindCycles(startVertex, it)
        }
        return allCycles
    }

    private fun startFindCycles(startVertex: Vertex<T>, subgraph: HashSet<Vertex<T>>) {
        val subGraphNodes = subgraph.associateWith { vertex ->
            graph.adjList[startVertex]?.filter { subgraph.contains(it) } ?: listOf()
        }
        subgraph.forEach { node ->
            blocked[node] = false
            blockedMap[node] = mutableSetOf()
        }
        dfsCycleFind(startVertex, startVertex, subGraphNodes)
    }

    private fun dfsCycleFind(start: Vertex<T>, current: Vertex<T>, subGraph: Map<Vertex<T>, List<Vertex<T>>>) {
        stack.add(current)
        blocked[current] = true

        for (neighbor in subGraph[current] ?: emptyList()) {
            if (neighbor == start && stack.size > 1) {
                allCycles.add(ArrayList(stack))
            } else if (blocked[neighbor] == false) {
                dfsCycleFind(start, neighbor, subGraph)
            } else {
                blockedMap[neighbor]?.add(current)
            }
        }

        processUnblocking(current)
    }


    private fun processUnblocking(current: Vertex<T>) {
        val queue = ArrayDeque<Vertex<T>>()
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
        }
    }


}

class TarjanSCC<T> {
    val stack = Stack<Vertex<T>>()
    val num = mutableMapOf<Vertex<T>, Int>()
    val lowest = mutableMapOf<Vertex<T>, Int>()
    val visited = hashSetOf<Vertex<T>>()
    val processed = hashSetOf<Vertex<T>>()
    var curIndex = 1

    fun findSCC(vertex: Vertex<T>, graph: AbstractGraph<Vertex<T>, T>): HashSet<Vertex<T>>{
        return dfsTarjan(vertex, graph)
    }

    fun findSCCs(graph: AbstractGraph<Vertex<T>, T>): HashSet<HashSet<Vertex<T>>>{
        val allSCCs: HashSet<HashSet<Vertex<T>>> = HashSet<HashSet<Vertex<T>>>()
        for(v in graph.adjList.keys){
            if(!visited.contains(v)) allSCCs.add(dfsTarjan(v, graph))
        }
        return allSCCs
    }


    fun dfsTarjan(vertex: Vertex<T>, graph: AbstractGraph<Vertex<T>, T>): HashSet<Vertex<T>>{
        num[vertex] = curIndex
        lowest[vertex] = curIndex
        curIndex++
        stack.add(vertex)
        visited.add(vertex)

        graph.adjList[vertex]?.forEach{
            if(!stack.contains(it)){
                dfsTarjan(it, graph)
                lowest[vertex] = min(lowest[vertex]!!, lowest[it]!!)
                //Говорят что это крайне желательно
            }
            else if(!processed.contains(it) && stack.contains(it)){
                lowest[vertex] = min(lowest[vertex]!!, num[it]!!)
                //И здесь то же самое
            }
        }
        processed.add(vertex)

        val scc: HashSet<Vertex<T>> = HashSet<Vertex<T>>()
        if(lowest[vertex] == num[vertex]){
            var sccVertex: Vertex<T>
            do{
                sccVertex = stack.pop()
                scc.add(sccVertex)
            }while(sccVertex != vertex)
        }

        return scc
    }
}