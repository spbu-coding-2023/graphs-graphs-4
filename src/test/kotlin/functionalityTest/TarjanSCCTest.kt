package functionalityTest

import model.functionality.TarjanSCC
import model.graphs.DirectedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TarjanSCCTest {
	private val graph = DirectedGraph<Int>()

	@Test
	fun findSCC_Ein() {
		for (i in 1..15) {
			graph.addVertex(i)
		}
		val nodes = graph.adjList.keys.toList().sortedBy { it.key }
		graph.addEdge(1, 2)
		graph.addEdge(2, 3)
		graph.addEdge(3, 1)
		graph.addEdge(4, 5)
		graph.addEdge(5, 6)
		graph.addEdge(6, 4)
		graph.addEdge(7, 8)
		graph.addEdge(8, 9)
		graph.addEdge(9, 7)
		graph.addEdge(10, 11)
		graph.addEdge(11, 12)
		graph.addEdge(12, 13)
		graph.addEdge(13, 10)

		val resultEin = TarjanSCC<Int>().findSCC(nodes[10], graph).toSet()
		val expectedResultEin = setOf(nodes[10], nodes[9], nodes[11], nodes[12])
		assertEquals(expectedResultEin, resultEin)

		val resultZwei = TarjanSCC<Int>().findSCC(nodes[7], graph).toSet()
		val expectedResultZwei = setOf(nodes[6], nodes[7], nodes[8])
		assertEquals(expectedResultZwei, resultZwei)

		val resultDrei = TarjanSCC<Int>().findSCC(nodes[4], graph).toSet()
		val expectedResultDrei = setOf(nodes[4], nodes[5], nodes[3])
		assertEquals(expectedResultDrei, resultDrei)
	}

	@Test
	fun findSCC_Zwei() {
		for (i in 1..14) {
			graph.addVertex(i)
		}
		val nodes = graph.adjList.keys.toList().sortedBy { it.key }
		graph.addEdge(1, 2)
		graph.addEdge(2, 3)
		graph.addEdge(3, 4)
		graph.addEdge(4, 5)
		graph.addEdge(5, 6)
		graph.addEdge(6, 3)
		graph.addEdge(6, 7)
		graph.addEdge(7, 8)
		graph.addEdge(8, 9)
		graph.addEdge(9, 7)
		graph.addEdge(10, 11)
		graph.addEdge(11, 12)
		graph.addEdge(12, 13)
		graph.addEdge(13, 14)
		graph.addEdge(14, 10)
		graph.addEdge(4, 1)

		val resultEin = TarjanSCC<Int>().findSCC(nodes[3], graph).toSet()
		val expectedResultEin = setOf(nodes[0], nodes[1], nodes[2], nodes[3], nodes[4], nodes[5])
		assertEquals(expectedResultEin, resultEin)

		val resultZwei = TarjanSCC<Int>().findSCC(nodes[7], graph).toSet()
		val expectedResultZwei = setOf(nodes[6], nodes[7], nodes[8])
		assertEquals(expectedResultZwei, resultZwei)
	}

	@Test
	fun findSCC_Drei() {
		for (i in 1..13) {
			graph.addVertex(i)
		}
		val nodes = graph.adjList.keys.toList().sortedBy { it.key }

		graph.addEdge(1, 2)
		graph.addEdge(2, 3)
		graph.addEdge(3, 4)
		graph.addEdge(4, 2)
		graph.addEdge(3, 5)
		graph.addEdge(5, 6)
		graph.addEdge(6, 7)
		graph.addEdge(7, 5)
		graph.addEdge(6, 8)
		graph.addEdge(8, 9)
		graph.addEdge(9, 10)
		graph.addEdge(10, 6)
		graph.addEdge(10, 11)
		graph.addEdge(11, 12)
		graph.addEdge(12, 13)
		graph.addEdge(13, 11)

		val resultEin = TarjanSCC<Int>().findSCC(nodes[11], graph).toSet()
		val expectedResultEin = setOf(nodes[10], nodes[11], nodes[12])
		assertEquals(expectedResultEin, resultEin)

		val resultZwei = TarjanSCC<Int>().findSCC(nodes[0], graph).toSet()
		val expectedResultZwei = setOf(nodes[0])
		assertEquals(expectedResultZwei, resultZwei)

		val resultDrei = TarjanSCC<Int>().findSCC(nodes[4], graph).toSet()
		val expectedResultDrei = setOf(nodes[4], nodes[5], nodes[6], nodes[7], nodes[8], nodes[9])
		assertEquals(expectedResultDrei, resultDrei)

		val resultViel = TarjanSCC<Int>().findSCC(nodes[2], graph).toSet()
		val expectedResultViel = setOf(nodes[1], nodes[2], nodes[3])
		assertEquals(expectedResultViel, resultViel)

		val commonResult = setOf(expectedResultViel, expectedResultDrei, expectedResultEin, expectedResultZwei)
		val commonSCCs = TarjanSCC<Int>().findSCCs(graph).toSet()
		assertEquals(commonResult, commonSCCs)
	}
}
