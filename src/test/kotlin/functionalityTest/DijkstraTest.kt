package functionalityTest

import model.functionality.ShortestPathFinder
import model.graphs.UndirectedWeightedGraph
import model.graphs.Vertex
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DijkstraTest {
    private val graph = UndirectedWeightedGraph<Int>()
    private var nodes: List<Vertex<Int>> = emptyList()

    @Test
    @DisplayName("All vertices are connected to each other")
    fun graphEin() {
        for (i in 0..5) {
            graph.addVertex(i)
        }

        nodes = graph.adjList.keys.toList().sortedBy { it.key }

        graph.addEdge(nodes[0], nodes[1], 3.0)
        graph.addEdge(nodes[0], nodes[2], 4.0)
        graph.addEdge(nodes[0], nodes[3], 8.0)
        graph.addEdge(nodes[0], nodes[4], 2.0)
        graph.addEdge(nodes[0], nodes[5], 5.0)

        graph.addEdge(nodes[1], nodes[2], 6.0)
        graph.addEdge(nodes[1], nodes[3], 10.0)
        graph.addEdge(nodes[1], nodes[4], 7.0)
        graph.addEdge(nodes[1], nodes[5], 9.0)

        graph.addEdge(nodes[2], nodes[3], 5.0)
        graph.addEdge(nodes[2], nodes[4], 3.0)
        graph.addEdge(nodes[2], nodes[5], 2.0)

        graph.addEdge(nodes[3], nodes[4], 4.0)
        graph.addEdge(nodes[3], nodes[5], 6.0)

        graph.addEdge(nodes[4], nodes[5], 1.0)

        val result_ein = graph.findDistancesDijkstra(nodes[1])
        assertEquals(9.0, result_ein[nodes[3]])
        assertEquals(5.0, result_ein[nodes[4]])

        val result_zwei = graph.findDistancesDijkstra(nodes[2])
        assertEquals(4.0, result_zwei[nodes[0]])
        assertEquals(2.0, result_zwei[nodes[5]])
        assertEquals(6.0, result_zwei[nodes[1]])
    }

    @Test
    @DisplayName("Only one edge for every vertex")
    fun graphZwei() {
        for (i in 1..10) {
            graph.addVertex(i)
        }

        nodes = graph.adjList.keys.toList().sortedBy { it.key }

        graph.addEdge(nodes[1-1], nodes[2-1], 3.0)
        graph.addEdge(nodes[3-1], nodes[4-1], 7.0)
        graph.addEdge(nodes[5-1], nodes[6-1], 1.0)
        graph.addEdge(nodes[7-1], nodes[8-1], 2.0)
        graph.addEdge(nodes[9-1], nodes[1-1], 4.0)

        val result = graph.findDistancesDijkstra(nodes[0])
        assertEquals(Double.POSITIVE_INFINITY, result[nodes[5]])
        assertEquals(Double.POSITIVE_INFINITY, result[nodes[7]])
        assertEquals(Double.POSITIVE_INFINITY, result[nodes[3]])
        assertEquals(3.0, result[nodes[1]])
    }

    @Test
    @DisplayName("Just usual graph but also checking if vertex isn't connected to the whole graph")
    fun graphDrei() {
        for (i in 0..7) {
            graph.addVertex(i)
        }

        nodes = graph.adjList.keys.toList().sortedBy { it.key }

        graph.addEdge(nodes[1], nodes[2], 3.0)
        graph.addEdge(nodes[1], nodes[3], 6.0)
        graph.addEdge(nodes[2], nodes[3], 2.0)
        graph.addEdge(nodes[2], nodes[4], 1.0)
        graph.addEdge(nodes[3], nodes[5], 5.0)
        graph.addEdge(nodes[4], nodes[5], 4.0)
        graph.addEdge(nodes[4], nodes[6], 2.0)
        graph.addEdge(nodes[5], nodes[7], 3.0)
        graph.addEdge(nodes[6], nodes[7], 1.0)
        graph.addEdge(nodes[6], nodes[3], 7.0)

        val result_ein = graph.findDistancesDijkstra(nodes[1])

        assertEquals(4.0, result_ein[nodes[4]])
        assertEquals(6.0, result_ein[nodes[6]])
        assertEquals(8.0, result_ein[nodes[5]])

        val result_sieben = graph.findDistancesDijkstra(nodes[7])

        assertEquals(4.0, result_sieben[nodes[2]])
        assertEquals(6.0, result_sieben[nodes[3]])
        assertEquals(3.0, result_sieben[nodes[4]])

        val result_null = graph.findDistancesDijkstra(nodes[0])

        assertEquals(Double.POSITIVE_INFINITY, result_null[nodes[4]])
        assertEquals(Double.POSITIVE_INFINITY, result_null[nodes[7]])
        assertEquals(Double.POSITIVE_INFINITY, result_null[nodes[5]])
    }

    @Test
    @DisplayName("Zero edges")
    fun graphVier() {
        for (i in 0..10) {
            graph.addVertex(i)
        }

        nodes = graph.adjList.keys.toList().sortedBy { it.key }

        val result_ein = graph.findDistancesDijkstra(nodes[0])
        assertEquals(Double.POSITIVE_INFINITY, result_ein[nodes[5]])
        assertEquals(Double.POSITIVE_INFINITY, result_ein[nodes[7]])
        assertEquals(Double.POSITIVE_INFINITY, result_ein[nodes[3]])

        val result_zwei = graph.findDistancesDijkstra(nodes[4])
        assertEquals(Double.POSITIVE_INFINITY, result_ein[nodes[2]])
        assertEquals(Double.POSITIVE_INFINITY, result_ein[nodes[9]])
        assertEquals(Double.POSITIVE_INFINITY, result_ein[nodes[10]])
    }
}
