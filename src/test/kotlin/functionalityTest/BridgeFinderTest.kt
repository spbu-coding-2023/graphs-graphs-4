package functionalityTest

import model.graphs.UndirectedGraph
import model.graphs.UndirectedWeightedGraph
import model.graphs.UnweightedEdge
import model.graphs.WeightedEdge
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import kotlin.test.Test
import kotlin.test.assertEquals


class BridgeFinderTest {
    @Nested
    inner class NotWeightedGraphs {
        private var graphInt = UndirectedGraph<Int>()

        @Test
        @DisplayName("Every edge is a bridge in a Simple Linear Chain")
        // 1 - 2 - 3
        fun findBridgesInChain() {
            for (i in 1..3) {
                graphInt.addVertex(i)
            }

            val nodes = arrayListOf(graphInt.adjList.keys.toList())

            graphInt.addEdge(nodes[0][0], nodes[0][1])
            graphInt.addEdge(nodes[0][1], nodes[0][2])

            val answer = setOf(
                UnweightedEdge(nodes[0][1], nodes[0][2]),
                UnweightedEdge(nodes[0][0], nodes[0][1])
            )

            assertEquals(answer, graphInt.findBridges())
        }

        @Test
        @DisplayName("No bridges found in a cycle graph")
        //  1---2
        //  | X |
        //  4---3
        fun findNoBridgesInSquareWithDiagonal() {
            for (i in 1..4) {
                graphInt.addVertex(i)
            }

            val nodes = arrayListOf(graphInt.adjList.keys.toList())

            graphInt.addEdge(nodes[0][0], nodes[0][1])
            graphInt.addEdge(nodes[0][1], nodes[0][2])
            graphInt.addEdge(nodes[0][2], nodes[0][3])
            graphInt.addEdge(nodes[0][3], nodes[0][0])
            graphInt.addEdge(nodes[0][0], nodes[0][2])
            graphInt.addEdge(nodes[0][1], nodes[0][3])

            assertEquals(emptySet(), graphInt.findBridges())
        }

        @Test
        @DisplayName("Find bridges in a graphs with multiple components")
        // Component 1:   Component 2:
        //    1               5 - 6
        //   / \
        //  2---3
        //       \
        //        4
        fun findBridgesWithMultipleComponents() {
            for (i in 1..6) {
                graphInt.addVertex(i)
            }

            val nodes = arrayListOf(graphInt.adjList.keys.toList())

            graphInt.addEdge(nodes[0][0], nodes[0][1])
            graphInt.addEdge(nodes[0][1], nodes[0][2])
            graphInt.addEdge(nodes[0][2], nodes[0][0])
            graphInt.addEdge(nodes[0][2], nodes[0][3])
            graphInt.addEdge(nodes[0][4], nodes[0][5])

            val answer = setOf(
                UnweightedEdge(nodes[0][4], nodes[0][5]),
                UnweightedEdge(nodes[0][2], nodes[0][3])
            )

            assertEquals(answer, graphInt.findBridges())
        }

        @Test
        @DisplayName("Find bridges in a graph with nested cycle and bridges")
        //1 - 2 - 3 - 4 - 5 - 6
        //|       |       |
        //7 - 8 - 9       10
        fun findBridgesWithNestedCycle() {
            for (i in 1..10) {
                graphInt.addVertex(i)
            }

            val nodes = arrayListOf(graphInt.adjList.keys.toList())

            graphInt.addEdge(nodes[0][0], nodes[0][1])
            graphInt.addEdge(nodes[0][1], nodes[0][2])
            graphInt.addEdge(nodes[0][2], nodes[0][3])
            graphInt.addEdge(nodes[0][0], nodes[0][6])
            graphInt.addEdge(nodes[0][6], nodes[0][7])
            graphInt.addEdge(nodes[0][7], nodes[0][8])
            graphInt.addEdge(nodes[0][2], nodes[0][8])
            graphInt.addEdge(nodes[0][3], nodes[0][4])
            graphInt.addEdge(nodes[0][4], nodes[0][5])
            graphInt.addEdge(nodes[0][4], nodes[0][9])

            val answer = setOf(
                UnweightedEdge(nodes[0][4], nodes[0][9]),
                UnweightedEdge(nodes[0][4], nodes[0][5]),
                UnweightedEdge(nodes[0][3], nodes[0][4]),
                UnweightedEdge(nodes[0][2], nodes[0][3])
            )

            assertEquals(answer, graphInt.findBridges())
        }

        @Test
        @DisplayName("No bridges in an empty graph")
        //⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⢀⣴⠟⠋⠉⠉⠙⢦
        //⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡾⠁⠀⠀⠀⣀⠀⠀⢻
        //⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⡞⠁
        //⠀⠀⠀ᵐᵉᵒʷ⠀⠀⠀⠀⠀⢷⡀⠀ ⠈⣧
        //⠀⠀⠀⠀♡⠀⠀⠀⠀⠀⠀⠈⠳⣄⠀ ⠙⢷⣄
        //⠀⠀⠀⠀⠀⠀⢀⣦⠀⠀⠀⠀⠀⠈⠑⠀⣀⣭⣷⣦⣤⣀
        //⠀⠀⠀⠀⠀⠀⣘⠁⠡⠀⠀⠀⠀⠀⠠⠚⠁⠀⠀⠀⠀⠹⣧
        //⠀⠀⠀⠀⠀⠀⠛⠀⠀⠐⠒⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡇
        //⠀⠀⠀⠀⡐⠁⢴⡄⠀⠀⠀⠀⠐⠈⠉⡽⠂⠀⠀⠀⠀⠀⢸⡇
        //⠀⠀⠀⠀⡇⠀⠀⣤⠀⠶⡦⠀⠀⠴⠚⠀⠀⠀⠀⠀⠀⠀⣸⠇
        //⠀⠀⠀⠀⡼⠂⠀⠒⠀⠀⠀⠀⢠⠇⠀⠀⠀⠀⠀⢀⠀⣠⠏
        //⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⠘⠁⠀⠀⠀⠀⠀⢀⣎⠴⠋
        //⠀⠀⠀⠘⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠋
        //⠀⠀⠀⠀⠘⠋⢠⡤⠂⠐⠀⠤⠤⠴⠚⠁⠀⠀
        // the cat ate graph
        fun findNoBridgesInEmptyGraph() {
            assertEquals(emptySet(), graphInt.findBridges())
        }
    }

    @Nested
    inner class WeightedGraphs {
        private val graphInt = UndirectedWeightedGraph<Int>()

        @Test
        @DisplayName("Algorithm runs on weighted graphs.")
        // 1 - 2 - 3 - 4 - 5 - 6
        // |       |       |
        // 7 - 8 - 9       10
        fun findBridgesWithNestedCycle() {
            for (i in 1..10) {
                graphInt.addVertex(i)
            }

            val nodes = arrayListOf(graphInt.adjList.keys.toList())

            graphInt.addEdge(nodes[0][0], nodes[0][1], 78.0)
            graphInt.addEdge(nodes[0][1], nodes[0][2], 78.0)
            graphInt.addEdge(nodes[0][2], nodes[0][3], 78.0)
            graphInt.addEdge(nodes[0][0], nodes[0][6], 78.0)
            graphInt.addEdge(nodes[0][6], nodes[0][7], 78.0)
            graphInt.addEdge(nodes[0][7], nodes[0][8], 78.0)
            graphInt.addEdge(nodes[0][2], nodes[0][8], 78.0)
            graphInt.addEdge(nodes[0][3], nodes[0][4], 78.0)
            graphInt.addEdge(nodes[0][4], nodes[0][5], 78.0)
            graphInt.addEdge(nodes[0][4], nodes[0][9], 78.0)

            val answer = setOf(
                WeightedEdge(nodes[0][4], nodes[0][9], 78.0),
                WeightedEdge(nodes[0][4], nodes[0][5], 78.0),
                WeightedEdge(nodes[0][3], nodes[0][4], 78.0),
                WeightedEdge(nodes[0][2], nodes[0][3], 78.0)
            )

            assertEquals(answer, graphInt.findBridges())
        }
    }
}
