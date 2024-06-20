package functionalityTest

import model.graphs.DirectedWeightedGraph
import model.graphs.UndirectedWeightedGraph
import model.graphs.Vertex
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.Double.Companion.NEGATIVE_INFINITY
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.test.assertEquals


class ShortestPathFinderTest {
    @Nested
    inner class DisconnectedPartsTest {
        private val graph = UndirectedWeightedGraph<Int>()
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
        fun disconnectedSelfLoopCheck() {
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

            val actualOutput = graph.findDistancesBellman(nodes[0]).minus(nodes[3])

            assertEquals(answer, actualOutput)
        }

        @Test
        @DisplayName(
            "Nodes from disconnected components of a graph" +
                " have an infinite distance to each other."
        )
        // 0 -- - (no edges)
        // 1 -- - (no edges)
        fun disconnectedNodesCheck() {
            setup(1)

            val answer = mapOf(
                nodes[0] to POSITIVE_INFINITY,
                nodes[1] to POSITIVE_INFINITY,
            )

            val actualOutputA = graph.findDistancesBellman(nodes[0]).minus(nodes[0])
            val actualOutputB = graph.findDistancesBellman(nodes[1]).minus(nodes[1])

            assertEquals(answer, actualOutputA.plus(actualOutputB))
        }
    }

    @Nested
    inner class DirectedGraphTest {
        private val graph = DirectedWeightedGraph<Int>()
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
        fun noFalseCycleCheck() {
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

            val actualAnswer = graph.findDistancesBellman(nodes[0])

            for (i in 0..3) {
                assertEquals(answer[nodes[i]], actualAnswer[nodes[i]])
            }
        }

        @DisplayName("Directed graph with a negative self-loop that affect entire graph.")
        // 0 -> 1 (weight 50)
        // 0 -> 2 (weight 5000)
        // 1 -> 2 (weight 500)
        // 1 -> 0 (weight 2)
        // 1 -> 1 (weight -1)
        // 2 -> 0 (weight 5000)
        fun detectSelfLoopCheck1() {
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

            val actualAnswer = graph.findDistancesBellman(nodes[0])

            for (i in 0..2) {
                assertEquals(answer[nodes[i]], actualAnswer[nodes[i]])
            }
        }

        @Test
        @DisplayName("Directed graph with a negative self-loop that doesn't affect entire graph.")
        // 0 -> 1 (weight 50)
        // 0 -> 2 (weight 500)
        // 0 -> 3 (weight 5500)
        // 1 -> 1 (weight -1)
        // 2 -> 3 (weight 55)
        fun detectSelfLoopCheck2() {
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

            val actualAnswer = graph.findDistancesBellman(nodes[0])

            for (i in 0..3) {
                assertEquals(answer[nodes[i]], actualAnswer[nodes[i]])
            }
        }

        @Test
        @DisplayName(
            "Find the shortest distance correctly " +
                "in a directed graph without negative weights."
        )
        // 0 -> 2 (weight 9)
        // 0 -> 6 (weight 14)
        // 0 -> 1 (weight 15)
        // 1 -> 5 (weight 20)
        // 1 -> 7 (weight 44)
        // 2 -> 3 (weight 24)
        // 3 -> 5 (weight 2)
        // 3 -> 7 (weight 19)
        // 4 -> 3 (weight 6)
        // 4 -> 7 (weight 6)
        // 5 -> 4 (weight 11)
        // 5 -> 7 (weight 16)
        // 6 -> 3 (weight 18)
        // 6 -> 5 (weight 30)
        // 6 -> 1 (weight 5)
        fun correctDisanceCheck() {
            setup(7)

            graph.addEdge(nodes[0], nodes[2], 9.0)
            graph.addEdge(nodes[0], nodes[6], 14.0)
            graph.addEdge(nodes[0], nodes[1], 15.0)
            graph.addEdge(nodes[1], nodes[5], 20.0)
            graph.addEdge(nodes[1], nodes[7], 44.0)
            graph.addEdge(nodes[2], nodes[3], 24.0)
            graph.addEdge(nodes[3], nodes[5], 2.0)
            graph.addEdge(nodes[3], nodes[7], 19.0)
            graph.addEdge(nodes[4], nodes[3], 6.0)
            graph.addEdge(nodes[4], nodes[7], 6.0)
            graph.addEdge(nodes[5], nodes[4], 11.0)
            graph.addEdge(nodes[5], nodes[7], 16.0)
            graph.addEdge(nodes[6], nodes[3], 18.0)
            graph.addEdge(nodes[6], nodes[5], 30.0)
            graph.addEdge(nodes[6], nodes[1], 5.0)

            val answer = mapOf(
                nodes[0] to 0.0,
                nodes[1] to 15.0,
                nodes[2] to 9.0,
                nodes[3] to 32.0,
                nodes[4] to 45.0,
                nodes[5] to 34.0,
                nodes[6] to 14.0,
                nodes[7] to 50.0
            )

            val actualAnswer = graph.findDistancesBellman(nodes[0])

            for (i in 0..7) {
                assertEquals(answer[nodes[i]], actualAnswer[nodes[i]])
            }
        }
    }

    @Nested
    inner class UndirectedGraphTest {
        private val graph = UndirectedWeightedGraph<Int>()
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
        fun findNegativeCycleCheck() {
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

            val actualAnswer = graph.findDistancesBellman(nodes[0])

            for (i in 0..3) {
                assertEquals(answer[nodes[i]], actualAnswer[nodes[i]])
            }
        }

        @Test
        @DisplayName(
            "Find the shortest distance correctly " +
                "in an undirected graph without negative weights."
        )
        // 0 -> 1 (weight 2)
        // 0 -> 3 (weight 8)
        // 1 -> 3 (weight 5)
        // 1 -> 4 (weight 6)
        // 2 -> 4 (weight 9)
        // 2 -> 5 (weight 3)
        // 3 -> 5 (weight 2)
        // 3 -> 4 (weight 3)
        // 4 -> 5 (weight 1)
        fun correctDistanceCheck() {
            setup(5)

            graph.addEdge(nodes[0], nodes[1], 2.0)
            graph.addEdge(nodes[0], nodes[3], 8.0)
            graph.addEdge(nodes[1], nodes[3], 5.0)
            graph.addEdge(nodes[1], nodes[4], 6.0)
            graph.addEdge(nodes[2], nodes[4], 9.0)
            graph.addEdge(nodes[2], nodes[5], 3.0)
            graph.addEdge(nodes[3], nodes[5], 2.0)
            graph.addEdge(nodes[3], nodes[4], 3.0)
            graph.addEdge(nodes[4], nodes[5], 1.0)

            val answer = mapOf(
                nodes[0] to 0.0,
                nodes[1] to 2.0,
                nodes[2] to 12.0,
                nodes[3] to 7.0,
                nodes[4] to 8.0,
                nodes[5] to 9.0,
            )

            val actualAnswer = graph.findDistancesBellman(nodes[0])

            for (i in 0..5) {
                assertEquals(answer[nodes[i]], actualAnswer[nodes[i]])
            }
        }
    }
}
