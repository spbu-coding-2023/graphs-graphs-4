//package functionalityTest
//
//import model.functionality.JohnsonAlg
//import model.graphs.DirectedGraph
//import model.graphs.Vertex
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//
//class JohnsonAlgTest {
//    private val graph = DirectedGraph<Int>()
//
//    @Test
//    fun findCyclesEin() { //Simple case(Graph 1)
//        for (i in 1..13) {
//            graph.addVertex(i)
//        }
//        val nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        graph.addEdge(1, 2)
//        graph.addEdge(2, 3)
//        graph.addEdge(3, 1)
//        graph.addEdge(4, 5)
//        graph.addEdge(5, 6)
//        graph.addEdge(6, 4)
//        graph.addEdge(7, 8)
//        graph.addEdge(8, 9)
//        graph.addEdge(9, 7)
//        graph.addEdge(10, 11)
//        graph.addEdge(11, 12)
//        graph.addEdge(12, 13)
//        graph.addEdge(13, 10)
//
//        val resultEin = JohnsonAlg<Int>(graph).findCycles(nodes[11])
//        val expectedResultEin = setOf(listOf(nodes[11], nodes[12], nodes[9], nodes[10]))
//        assertEquals(expectedResultEin, resultEin)
//
//        val resultZwei = JohnsonAlg<Int>(graph).findCycles(nodes[4])
//        val expectedResultZwei = setOf(listOf(nodes[4], nodes[5], nodes[3]))
//        assertEquals(expectedResultZwei, resultZwei)
//    }
//
//    @Test
//    fun findCyclesZwei() {
//        for (i in 1..13) {
//            graph.addVertex(i)
//        }
//        val nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        graph.addEdge(1, 2)
//        graph.addEdge(2, 3)
//        graph.addEdge(3, 4)
//        graph.addEdge(4, 5)
//        graph.addEdge(5, 6)
//        graph.addEdge(6, 3)
//        graph.addEdge(6, 7)
//        graph.addEdge(7, 8)
//        graph.addEdge(8, 9)
//        graph.addEdge(9, 7)
//        graph.addEdge(10, 11)
//        graph.addEdge(11, 12)
//        graph.addEdge(12, 13)
//        graph.addEdge(13, 10)
//        graph.addEdge(13, 11)
//
//        val result = JohnsonAlg<Int>(graph).findCycles(nodes[3])
//        val expectedResult = setOf(listOf(nodes[3], nodes[4], nodes[5], nodes[2]))
//        assertEquals(expectedResult, result)
//    }
//
//    @Test
//    fun findCyclesDrei() {
//        for (i in 1..9) {
//            graph.addVertex(i)
//        }
//        val nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        graph.addEdge(8, 9)
//        graph.addEdge(9, 8)
//        graph.addEdge(1, 2)
//        graph.addEdge(2, 7)
//        graph.addEdge(1, 8)
//        graph.addEdge(2, 9)
//        graph.addEdge(2, 3)
//        graph.addEdge(3, 2)
//        graph.addEdge(3, 1)
//        graph.addEdge(3, 4)
//        graph.addEdge(3, 6)
//        graph.addEdge(4, 5)
//        graph.addEdge(5, 2)
//        graph.addEdge(1, 5)
//        graph.addEdge(6, 4)
//
//        val result = JohnsonAlg<Int>(graph).findCycles(nodes[0])
//        val first = listOf(nodes[0], nodes[1], nodes[2])
//        val second = listOf(nodes[0], nodes[4], nodes[1], nodes[2])
//        val expectedResult = setOf(first, second)
//        assertEquals(expectedResult, result)
//    }
//
//    @Test
//    fun VeryMuchCicles() {
//        for (i in 1..7) {
//            graph.addVertex(i)
//        }
//        val nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        graph.addEdge(1, 3)
//        graph.addEdge(3, 2)
//        graph.addEdge(3, 6)
//        graph.addEdge(6, 2)
//        graph.addEdge(6, 4)
//        graph.addEdge(7, 4)
//        graph.addEdge(7, 5)
//        graph.addEdge(5, 4)
//        graph.addEdge(4, 2)
//        graph.addEdge(2, 1)
//        graph.addEdge(6, 7)
//
//        val result = JohnsonAlg<Int>(graph).findCycles(nodes[0])
//        val first = listOf(nodes[0], nodes[2], nodes[1])
//        val second = listOf(nodes[0], nodes[2], nodes[5], nodes[1])
//        val third = listOf(nodes[0], nodes[2], nodes[5], nodes[3], nodes[1])
//        val fourth = listOf(nodes[0], nodes[2], nodes[5], nodes[6], nodes[3], nodes[1])
//        val fifth = listOf(nodes[0], nodes[2], nodes[5], nodes[6], nodes[4], nodes[3], nodes[1])
//        val expectedResult = setOf(first, second, third, fourth, fifth)
//        assertEquals(expectedResult, result)
//    }
//
//    @Test
//    fun NoCycles() {
//        for (i in 1..7) {
//            graph.addVertex(i)
//        }
//        val nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        val result = JohnsonAlg<Int>(graph).findCycles(nodes[0])
//        assertEquals(setOf<Vertex<Int>>(), result)
//    }
//}
