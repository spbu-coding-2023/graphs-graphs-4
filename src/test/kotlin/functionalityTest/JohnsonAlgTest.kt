package functionalityTest

import model.functionality.JohnsonAlg
import model.graphs.DirectedGraph
import model.graphs.Vertex
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JohnsonAlgTest {
    private val graph = DirectedGraph<Int>()

    @Test
    fun findCyclesEin() { //Simple case(Graph 1)
        for (i in 1..13) {
            graph.addVertex(i)
        }
        val nodes = graph.adjList.keys.toList().sortedBy { it.key }

        graph.addEdge(nodes[1-1], nodes[2-1])
        graph.addEdge(nodes[2-1], nodes[3-1])
        graph.addEdge(nodes[3-1], nodes[1-1])
        graph.addEdge(nodes[4-1], nodes[5-1])
        graph.addEdge(nodes[5-1], nodes[6-1])
        graph.addEdge(nodes[6-1], nodes[4-1])
        graph.addEdge(nodes[7-1], nodes[8-1])
        graph.addEdge(nodes[8-1], nodes[9-1])
        graph.addEdge(nodes[9-1], nodes[7-1])
        graph.addEdge(nodes[10-1], nodes[11-1])
        graph.addEdge(nodes[11-1], nodes[12-1])
        graph.addEdge(nodes[12-1], nodes[13-1])
        graph.addEdge(nodes[13-1], nodes[10-1])

        val resultEin = JohnsonAlg<Int>(graph).findCycles(nodes[11+1])
        val expectedResultEin = setOf(listOf(nodes[12], nodes[9], nodes[10], nodes[11]))
        assertEquals(expectedResultEin, resultEin)

        val resultZwei = JohnsonAlg<Int>(graph).findCycles(nodes[4+1])
        val expectedResultZwei = setOf(listOf(nodes[5], nodes[3], nodes[4]))
        assertEquals(expectedResultZwei, resultZwei)
    }

    @Test
    fun findCyclesZwei() {
        for (i in 1..13) {
            graph.addVertex(i)
        }
        val nodes = graph.adjList.keys.toList().sortedBy { it.key }

        graph.addEdge(nodes[1-1], nodes[2-1])
        graph.addEdge(nodes[2-1], nodes[3-1])
        graph.addEdge(nodes[3-1], nodes[4-1])
        graph.addEdge(nodes[4-1], nodes[5-1])
        graph.addEdge(nodes[5-1], nodes[6-1])
        graph.addEdge(nodes[6-1], nodes[3-1])
        graph.addEdge(nodes[6-1], nodes[7-1])
        graph.addEdge(nodes[7-1], nodes[8-1])
        graph.addEdge(nodes[8-1], nodes[9-1])
        graph.addEdge(nodes[9-1], nodes[7-1])
        graph.addEdge(nodes[10-1], nodes[11-1])
        graph.addEdge(nodes[11-1], nodes[12-1])
        graph.addEdge(nodes[12-1], nodes[13-1])
        graph.addEdge(nodes[13-1], nodes[10-1])
        graph.addEdge(nodes[13-1], nodes[11-1])

        val result_ein = JohnsonAlg<Int>(graph).findCycles(nodes[3])
        val expectedResult_ein = setOf(listOf(nodes[3], nodes[4], nodes[5], nodes[2]))
        assertEquals(expectedResult_ein, result_ein)

        val result_zwei = JohnsonAlg<Int>(graph).findCycles(nodes[7])
        val expectedResult_zwei = setOf(listOf(nodes[7], nodes[8], nodes[6]))
        assertEquals(expectedResult_zwei, result_zwei)

        val result_drei = JohnsonAlg<Int>(graph).findCycles(nodes[12])
        val expectedResult_drei = setOf(listOf(nodes[12], nodes[9], nodes[10], nodes[11]), listOf(nodes[12], nodes[10], nodes[11]))
        assertEquals(expectedResult_drei, result_drei)
    }

    @Test
    fun findCyclesDrei() {
        for (i in 1..9) {
            graph.addVertex(i)
        }
        val nodes = graph.adjList.keys.toList().sortedBy { it.key }

        graph.addEdge(nodes[8-1], nodes[9-1])
        graph.addEdge(nodes[9-1], nodes[8-1])
        graph.addEdge(nodes[1-1], nodes[2-1])
        graph.addEdge(nodes[2-1], nodes[7-1])
        graph.addEdge(nodes[1-1], nodes[8-1])
        graph.addEdge(nodes[2-1], nodes[9-1])
        graph.addEdge(nodes[2-1], nodes[3-1])
        graph.addEdge(nodes[3-1], nodes[2-1])
        graph.addEdge(nodes[3-1], nodes[1-1])
        graph.addEdge(nodes[3-1], nodes[4-1])
        graph.addEdge(nodes[3-1], nodes[6-1])
        graph.addEdge(nodes[4-1], nodes[5-1])
        graph.addEdge(nodes[5-1], nodes[2-1])
        graph.addEdge(nodes[1-1], nodes[5-1])
        graph.addEdge(nodes[6-1], nodes[4-1])

        val result = JohnsonAlg<Int>(graph).findCycles(nodes[0])
        val first = listOf(nodes[0], nodes[1], nodes[2])
        val second = listOf(nodes[0], nodes[4], nodes[1], nodes[2])
        val expectedResult = setOf(first, second)
        assertEquals(expectedResult, result)
    }

    @Test
    fun VeryMuchCicles() {
        for (i in 1..7) {
            graph.addVertex(i)
        }
        val nodes = graph.adjList.keys.toList().sortedBy { it.key }

        graph.addEdge(nodes[1-1], nodes[3-1])
        graph.addEdge(nodes[3-1], nodes[2-1])
        graph.addEdge(nodes[3-1], nodes[6-1])
        graph.addEdge(nodes[6-1], nodes[2-1])
        graph.addEdge(nodes[6-1], nodes[4-1])
        graph.addEdge(nodes[7-1], nodes[4-1])
        graph.addEdge(nodes[7-1], nodes[5-1])
        graph.addEdge(nodes[5-1], nodes[4-1])
        graph.addEdge(nodes[4-1], nodes[2-1])
        graph.addEdge(nodes[2-1], nodes[1-1])
        graph.addEdge(nodes[6-1], nodes[7-1])

        val result = JohnsonAlg<Int>(graph).findCycles(nodes[0])
        val first = listOf(nodes[0], nodes[2], nodes[1])
        val second = listOf(nodes[0], nodes[2], nodes[5], nodes[1])
        val third = listOf(nodes[0], nodes[2], nodes[5], nodes[3], nodes[1])
        val fourth = listOf(nodes[0], nodes[2], nodes[5], nodes[6], nodes[3], nodes[1])
        val fifth = listOf(nodes[0], nodes[2], nodes[5], nodes[6], nodes[4], nodes[3], nodes[1])
        val expectedResult = setOf(first, second, third, fourth, fifth)
        assertEquals(expectedResult, result)
    }

    @Test
    fun NoCycles() {
        for (i in 1..7) {
            graph.addVertex(i)
        }
        val nodes = graph.adjList.keys.toList().sortedBy { it.key }

        val result = JohnsonAlg<Int>(graph).findCycles(nodes[0])
        assertEquals(setOf<Vertex<Int>>(), result)
    }
}
