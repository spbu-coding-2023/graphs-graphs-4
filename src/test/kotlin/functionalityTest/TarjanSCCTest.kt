package functionalityTest

import model.functionality.TarjanSCC
import model.graphs.DirectedGraph
import model.graphs.Edge
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

        val resultEin = graph.Tarjan(nodes[10]).toSet()
        //val resultEin = TarjanSCC<Int, Edge<Int>>().findSCC(nodes[10], graph).toSet()
        val expectedResultEin = setOf(nodes[11], nodes[10], nodes[12], nodes[13])
        assertEquals(expectedResultEin, resultEin)

        val resultZwei = graph.Tarjan(nodes[7]).toSet()
        //val resultZwei = TarjanSCC<Int>().findSCC(nodes[7], graph).toSet()
        val expectedResultZwei = setOf(nodes[7], nodes[8], nodes[9])
        assertEquals(expectedResultZwei, resultZwei)

        val resultDrei = graph.Tarjan(nodes[4]).toSet()
        //val resultDrei = TarjanSCC<Int>().findSCC(nodes[4], graph).toSet()
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

        val resultEin = graph.Tarjan(nodes[3]).toSet()
        //val resultEin = TarjanSCC<Int>().findSCC(nodes[3], graph).toSet()
        val expectedResultEin = setOf(nodes[6], nodes[1], nodes[2], nodes[3], nodes[4], nodes[5])
        assertEquals(expectedResultEin, resultEin)

        val resultZwei = graph.Tarjan(nodes[7]).toSet()
        //val resultZwei = TarjanSCC<Int>().findSCC(nodes[7], graph).toSet()
        val expectedResultZwei = setOf(nodes[9], nodes[7], nodes[8])
        assertEquals(expectedResultZwei, resultZwei)
    }


    //THIS METHOD EXISTS IN ANOTHER METHOD AND TWICE
// IMPLEMENT THE SAME IS CRINGE SO THERE'S NO THIS TEST ANYMORE

    /*@Test
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

        val resultEin = graph.Tarjan(nodes[12]).toSet()
        //val resultEin = TarjanSCC<Int>().findSCC(nodes[12], graph).toSet()
        val expectedResultEin = setOf(nodes[13], nodes[11], nodes[12])
        assertEquals(expectedResultEin, resultEin)

        val resultZwei = graph.Tarjan(nodes[0]).toSet()
        //val resultZwei = TarjanSCC<Int>().findSCC(nodes[0], graph).toSet()
        val expectedResultZwei = setOf(nodes[0])
        assertEquals(expectedResultZwei, resultZwei)

        val resultDrei = graph.Tarjan(nodes[5]).toSet()
        //val resultDrei = TarjanSCC<Int>().findSCC(nodes[5], graph).toSet()
        val expectedResultDrei = setOf(nodes[10], nodes[5], nodes[6], nodes[7], nodes[8], nodes[9])
        assertEquals(expectedResultDrei, resultDrei)

        val resultViel = graph.Tarjan(nodes[3]).toSet()
        //val resultViel = TarjanSCC<Int>().findSCC(nodes[3], graph).toSet()
        val expectedResultViel = setOf(nodes[4], nodes[2], nodes[3])
        assertEquals(expectedResultViel, resultViel)

        val resultFunf = graph.Tarjan(nodes[1]).toSet()
        //val resultFunf = TarjanSCC<Int>().findSCC(nodes[1], graph).toSet()

        val commonResult = setOf(resultFunf, expectedResultViel, expectedResultDrei, expectedResultEin, expectedResultZwei)
        //val commonSCCs = graph.Tarjan(nodes[10]).toSet()
        val commonSCCs = TarjanSCC().findSCCs(graph).toSet()
        assertEquals(commonResult, commonSCCs)
    }*/
}
