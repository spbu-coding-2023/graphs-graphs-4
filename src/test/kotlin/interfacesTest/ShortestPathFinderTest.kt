package interfacesTest

import graphs.DirectedWeightedGraph
import graphs.Vertex
import graphs.WeightedGraph
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.Double.Companion.NEGATIVE_INFINITY
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.test.assertEquals


class ShortestPathFinderTest {
	@Nested
	inner class DirectionIndependedTest {
		private val graph = WeightedGraph<Int, Double>()
		private var nodes: List<Vertex<Int>> = emptyList()

		private fun setup(end: Int) {
			for (i in 0..end) {
				graph.addVertex(i)
			}

			nodes = graph.adjList.keys.toList().sortedBy { it.key }
		}

		@Test
		@DisplayName("Disconnected negative self-loop does not affect the rest of the graph.")
		// 0 -- 1 (weight 1)
		// 1 -- 2 (weight 2)
		// 0 -- 2 (weight 10)
		// 3 -- 3 (weight -1)
		fun disconnectedTest1() {
			setup(3)

			graph.addEdge(nodes[0], nodes[1], 1.0)
			graph.addEdge(nodes[1], nodes[2], 2.0)
			graph.addEdge(nodes[0], nodes[2], 10.0)
			graph.addEdge(nodes[3], nodes[3], -1.0)

			val answer = mapOf(
				nodes[0] to 0.0,
				nodes[1] to 1.0,
				nodes[2] to 3.0,
			)

			val actualOutput = graph.findShortestDistance(nodes[0]).minus(nodes[3])

			assertEquals(answer, actualOutput)
		}

		@Test
		@DisplayName(
			"Nodes from disconnected components of a graph" +
				" have an infinite distance to each other."
		)
		// 0 -- - (no edges)
		// 1 -- - (no edges)
		fun disconnectedTest2() {
			setup(1)

			val answer = mapOf(
				nodes[0] to POSITIVE_INFINITY,
				nodes[1] to POSITIVE_INFINITY,
			)

			val actualOutputA = graph.findShortestDistance(nodes[0]).minus(nodes[0])
			val actualOutputB = graph.findShortestDistance(nodes[1]).minus(nodes[1])

			assertEquals(answer, actualOutputA.plus(actualOutputB))
		}
	}


	@Nested
	inner class DirectedGraphTest {
		private val graph = DirectedWeightedGraph<Int, Double>()
		private var nodes: List<Vertex<Int>> = emptyList()

		private fun setup(end: Int) {
			for (i in 0..end) {
				graph.addVertex(i)
			}

			nodes = graph.adjList.keys.toList().sortedBy { it.key }
		}

		@Test
		@DisplayName("Directed graph with negative weights but with no negative cycles.")
		// 0 -> 1 (weight 1)
		// 1 -> 2 (weight -1)
		// 2 -> 3 (weight -1)
		// 3 -> 0 (weight 2)
		fun noFalseCycleTest1() {
			setup(3)

			graph.addEdge(nodes[0], nodes[1], 5.0)
			graph.addEdge(nodes[1], nodes[2], -5.0)
			graph.addEdge(nodes[2], nodes[3], -5.0)
			graph.addEdge(nodes[3], nodes[0], 10.0)

			val answer = mapOf(
				nodes[0] to 0.0,
				nodes[1] to 5.0,
				nodes[2] to 0.0,
				nodes[3] to -5.0
			)

			for (i in 0..3) {
				assertEquals(answer[nodes[i]], graph.findShortestDistance(nodes[0])[nodes[i]])
			}
		}

		@Test
		@DisplayName("Directed graph with a negative self-loop that affect entire graph.")
		// 0 -> 1 (weight 50)
		// 0 -> 2 (weight 5000)
		// 1 -> 2 (weight 500)
		// 1 -> 0 (weight 2)
		// 1 -> 1 (weight -1)
		// 2 -> 0 (weight 5000)
		fun detectLoop1() {
			setup(2)

			graph.addEdge(nodes[0], nodes[1], 50.0)
			graph.addEdge(nodes[0], nodes[2], 5000.0)
			graph.addEdge(nodes[1], nodes[2], 500.0)
			graph.addEdge(nodes[1], nodes[1], -1.0)
			graph.addEdge(nodes[1], nodes[0], 2.0)
			graph.addEdge(nodes[2], nodes[0], 5000.0)

			val answer = mapOf(
				nodes[0] to NEGATIVE_INFINITY,
				nodes[1] to NEGATIVE_INFINITY,
				nodes[2] to NEGATIVE_INFINITY,
			)

			for (i in 0..2) {
				assertEquals(answer[nodes[i]], graph.findShortestDistance(nodes[0])[nodes[i]])
			}
		}

		@Test
		@DisplayName("Directed graph with a negative self-loop that doesn't affect entire graph.")
		// 0 -> 1 (weight 50)
		// 0 -> 2 (weight 500)
		// 0 -> 3 (weight 5500)
		// 1 -> 1 (weight -1)
		// 2 -> 3 (weight 55)
		fun detectLoop2() {
			setup(3)

			graph.addEdge(nodes[0], nodes[1], 50.0)
			graph.addEdge(nodes[0], nodes[2], 500.0)
			graph.addEdge(nodes[0], nodes[3], 5500.0)
			graph.addEdge(nodes[1], nodes[1], -1.0)
			graph.addEdge(nodes[2], nodes[3], 55.0)

			val answer = mapOf(
				nodes[0] to 0.0,
				nodes[1] to NEGATIVE_INFINITY,
				nodes[2] to 500.0,
				nodes[3] to 555.0
			)

			for (i in 0..3) {
				assertEquals(answer[nodes[i]], graph.findShortestDistance(nodes[0])[nodes[i]])
			}
		}
	}

	@Nested
	inner class UndirectedGraphTest {
		private val graph = WeightedGraph<Int, Double>()
		private var nodes: List<Vertex<Int>> = emptyList()

		private fun setup(end: Int) {
			for (i in 0..end) {
				graph.addVertex(i)
			}

			nodes = graph.adjList.keys.toList().sortedBy { it.key }
		}

		@Test
		@DisplayName("Undirected graph with negative weight has a negative cycle.")
		// 0 -- 1 (weight 1)
		// 0 -- 4 (weight -1)
		// 1 -- 2 (weight 1)
		// 2 -- 3 (weight 1)
		// 3 -- 0 (weight 2)
		fun findCycleTest1() {
			setup(4)

			graph.addEdge(nodes[0], nodes[1], 1.0)
			graph.addEdge(nodes[0], nodes[4], -1.0)
			graph.addEdge(nodes[1], nodes[2], 1.0)
			graph.addEdge(nodes[2], nodes[3], 1.0)
			graph.addEdge(nodes[3], nodes[0], 2.0)

			val answer = mapOf(
				nodes[0] to NEGATIVE_INFINITY,
				nodes[1] to NEGATIVE_INFINITY,
				nodes[2] to NEGATIVE_INFINITY,
				nodes[3] to NEGATIVE_INFINITY,
				nodes[4] to NEGATIVE_INFINITY
			)

			for (i in 0..3) {
				assertEquals(answer[nodes[i]], graph.findShortestDistance(nodes[0])[nodes[i]])
			}
		}
	}

}
