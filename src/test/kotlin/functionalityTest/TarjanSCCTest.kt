package functionalityTest

import model.functionality.TarjanSCC
import model.graphs.DirectedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TarjanSCCTest {
    private val graph = DirectedGraph<Int>()

    @Test
    fun findSCC_Ein() {
        for (i in 0..15) {
            graph.addVertex(i)
        }
        val nodes = graph.adjList.keys.toList().sortedBy { it.key }
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[3], nodes[1])
        graph.addEdge(nodes[4], nodes[5])
        graph.addEdge(nodes[5], nodes[6])
        graph.addEdge(nodes[6], nodes[4])
        graph.addEdge(nodes[7], nodes[8])
        graph.addEdge(nodes[8], nodes[9])
        graph.addEdge(nodes[9], nodes[7])
        graph.addEdge(nodes[10], nodes[11])
        graph.addEdge(nodes[11], nodes[12])
        graph.addEdge(nodes[12], nodes[13])
        graph.addEdge(nodes[13], nodes[10])

        val resultEin = TarjanSCC<Int>().findSCC(nodes[10], graph).toSet()
        val expectedResultEin = setOf(nodes[11], nodes[10], nodes[12], nodes[13])
        assertEquals(expectedResultEin, resultEin)

        val resultZwei = TarjanSCC<Int>().findSCC(nodes[7], graph).toSet()
        val expectedResultZwei = setOf(nodes[7], nodes[8], nodes[9])
        assertEquals(expectedResultZwei, resultZwei)

        val resultDrei = TarjanSCC<Int>().findSCC(nodes[4], graph).toSet()
        val expectedResultDrei = setOf(nodes[5], nodes[6], nodes[4])
        assertEquals(expectedResultDrei, resultDrei)
    }

    @Test
    fun findSCC_Zwei() {
        for (i in 1..15) {
            graph.addVertex(i)
        }
        val nodes = graph.adjList.keys.toList().sortedBy { it.key }
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[3], nodes[4])
        graph.addEdge(nodes[4], nodes[5])
        graph.addEdge(nodes[5], nodes[6])
        graph.addEdge(nodes[6], nodes[3])
        graph.addEdge(nodes[6], nodes[7])
        graph.addEdge(nodes[7], nodes[8])
        graph.addEdge(nodes[8], nodes[9])
        graph.addEdge(nodes[9], nodes[7])
        graph.addEdge(nodes[10], nodes[11])
        graph.addEdge(nodes[11], nodes[12])
        graph.addEdge(nodes[12], nodes[13])
        graph.addEdge(nodes[13], nodes[14])
        graph.addEdge(nodes[14], nodes[10])
        graph.addEdge(nodes[4], nodes[1])

        val resultEin = TarjanSCC<Int>().findSCC(nodes[3], graph).toSet()
        val expectedResultEin = setOf(nodes[6], nodes[1], nodes[2], nodes[3], nodes[4], nodes[5])
        assertEquals(expectedResultEin, resultEin)

        val resultZwei = TarjanSCC<Int>().findSCC(nodes[7], graph).toSet()
        val expectedResultZwei = setOf(nodes[9], nodes[7], nodes[8])
        assertEquals(expectedResultZwei, resultZwei)
    }

    @Test
    fun findSCC_Drei() {
        for (i in 1..14) {
            graph.addVertex(i)
        }
        val nodes = graph.adjList.keys.toList().sortedBy { it.key }

        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[3], nodes[4])
        graph.addEdge(nodes[4], nodes[2])
        graph.addEdge(nodes[3], nodes[5])
        graph.addEdge(nodes[5], nodes[6])
        graph.addEdge(nodes[6], nodes[7])
        graph.addEdge(nodes[7], nodes[5])
        graph.addEdge(nodes[6], nodes[8])
        graph.addEdge(nodes[8], nodes[9])
        graph.addEdge(nodes[9], nodes[10])
        graph.addEdge(nodes[10], nodes[6])
        graph.addEdge(nodes[10], nodes[11])
        graph.addEdge(nodes[11], nodes[12])
        graph.addEdge(nodes[12], nodes[13])
        graph.addEdge(nodes[13], nodes[11])

        val resultEin = TarjanSCC<Int>().findSCC(nodes[12], graph).toSet()
        val expectedResultEin = setOf(nodes[13], nodes[11], nodes[12])
        assertEquals(expectedResultEin, resultEin)

        val resultZwei = TarjanSCC<Int>().findSCC(nodes[0], graph).toSet()
        val expectedResultZwei = setOf(nodes[0])
        assertEquals(expectedResultZwei, resultZwei)

        val resultDrei = TarjanSCC<Int>().findSCC(nodes[5], graph).toSet()
        val expectedResultDrei = setOf(nodes[10], nodes[5], nodes[6], nodes[7], nodes[8], nodes[9])
        assertEquals(expectedResultDrei, resultDrei)

        val resultViel = TarjanSCC<Int>().findSCC(nodes[3], graph).toSet()
        val expectedResultViel = setOf(nodes[4], nodes[2], nodes[3])
        assertEquals(expectedResultViel, resultViel)

        val resultFunf = TarjanSCC<Int>().findSCC(nodes[1], graph).toSet()

        val commonResult = setOf(resultFunf, expectedResultViel, expectedResultDrei, expectedResultEin, expectedResultZwei)
        val commonSCCs = TarjanSCC<Int>().findSCCs(graph).toSet()
        assertEquals(commonResult, commonSCCs)
    }
}
