package interfacesTest

import graphs.Graph
import graphs.Vertex
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals


// запускать этот тест только если прошли тесты на графы?
class BridgeFinderTest {
	private var graphInt = Graph<Int>()

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
			Pair(nodes[0][2], nodes[0][1]),
			Pair(nodes[0][1], nodes[0][0])
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
			Pair(nodes[0][5], nodes[0][4]),
			Pair(nodes[0][3], nodes[0][2])
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
			Pair(nodes[0][9], nodes[0][4]),
			Pair(nodes[0][5], nodes[0][4]),
			Pair(nodes[0][4], nodes[0][3]),
			Pair(nodes[0][3], nodes[0][2])
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