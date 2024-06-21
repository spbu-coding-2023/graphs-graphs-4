//package functionalityTest
//
//import model.functionality.ShortestPathFinder
//import model.graphs.UndirectedWeightedGraph
//import model.graphs.Vertex
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//
//class DijkstraTest {
//    private val graph = UndirectedWeightedGraph<Int, Double>()
//    private var nodes: List<Vertex<Int>> = emptyList()
//
//    @Test
//    fun graphEin() {
//        for (i in 1..12) {
//            graph.addVertex(i)
//        }
//
//        nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        graph.addEdge(1, 2, 3.0)
//        graph.addEdge(1, 3, 6.0)
//        graph.addEdge(1, 4, 1.0)
//        graph.addEdge(2, 3, 2.0)
//        graph.addEdge(2, 5, 8.0)
//        graph.addEdge(3, 6, 4.0)
//        graph.addEdge(4, 7, 5.0)
//        graph.addEdge(5, 8, 3.0)
//        graph.addEdge(6, 9, 2.0)
//        graph.addEdge(7, 10, 7.0)
//        graph.addEdge(8, 11, 1.0)
//        graph.addEdge(9, 12, 6.0)
//        graph.addEdge(10, 11, 2.0)
//        graph.addEdge(11, 12, 4.0)
//        graph.addEdge(3, 5, 3.0)
//        graph.addEdge(6, 8, 2.0)
//
//        val result = ShortestPathFinder(graph).dijkstra(nodes[0])
//        assertEquals(8.0, result[nodes[4]])
//        assertEquals(1.0, result[nodes[3]])
//        assertEquals(13.0, result[nodes[9]])
//        assertEquals(16.0, result[nodes[11]])
//        assertEquals(12.0, result[nodes[10]])
//        assertEquals(11.0, result[nodes[8]])
//    }
//
//    @Test
//    fun graphZwei() {
//        for (i in 1..15) {
//            graph.addVertex(i)
//        }
//
//        nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        graph.addEdge(1, 2, 4.0)
//        graph.addEdge(1, 3, 2.0)
//        graph.addEdge(1, 4, 7.0)
//        graph.addEdge(2, 3, 1.0)
//        graph.addEdge(2, 5, 5.0)
//        graph.addEdge(3, 6, 3.0)
//        graph.addEdge(4, 7, 6.0)
//        graph.addEdge(5, 8, 2.0)
//        graph.addEdge(6, 9, 4.0)
//        graph.addEdge(7, 10, 1.0)
//        graph.addEdge(8, 11, 5.0)
//        graph.addEdge(9, 12, 7.0)
//        graph.addEdge(10, 13, 3.0)
//        graph.addEdge(11, 14, 8.0)
//        graph.addEdge(12, 15, 6.0)
//        graph.addEdge(5, 6, 2.0)
//        graph.addEdge(8, 9, 1.0)
//        graph.addEdge(10, 11, 4.0)
//        graph.addEdge(13, 14, 2.0)
//        graph.addEdge(14, 15, 3.0)
//
//        val result = ShortestPathFinder(graph).dijkstra(nodes[0])
//        assertEquals(3.0, result[nodes[1]])
//        assertEquals(7.0, result[nodes[4]])
//        assertEquals(9.0, result[nodes[8]])
//        assertEquals(17.0, result[nodes[12]])
//        assertEquals(16.0, result[nodes[11]])
//        assertEquals(14.0, result[nodes[9]])
//        assertEquals(19.0, result[nodes[13]])
//    }
//
//    @Test
//    fun graphDrei() {
//        for (i in 1..10) {
//            graph.addVertex(i)
//        }
//
//        nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        graph.addEdge(1, 2, 3.0)
//        graph.addEdge(3, 4, 7.0)
//        graph.addEdge(5, 6, 1.0)
//        graph.addEdge(7, 8, 2.0)
//        graph.addEdge(9, 10, 4.0)
//
//        val result = ShortestPathFinder(graph).dijkstra(nodes[0])
//        assertEquals(Double.POSITIVE_INFINITY, result[nodes[5]])
//        assertEquals(Double.POSITIVE_INFINITY, result[nodes[7]])
//        assertEquals(Double.POSITIVE_INFINITY, result[nodes[3]])
//        assertEquals(3.0, result[nodes[1]])
//    }
//
//    @Test
//    fun graphViel() {
//        for (i in 1..7) {
//            graph.addVertex(i)
//        }
//
//        nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        graph.addEdge(1, 2, 3.0)
//        graph.addEdge(1, 3, 6.0)
//        graph.addEdge(2, 3, 2.0)
//        graph.addEdge(2, 4, 1.0)
//        graph.addEdge(3, 5, 5.0)
//        graph.addEdge(4, 5, 4.0)
//        graph.addEdge(4, 6, 2.0)
//        graph.addEdge(5, 7, 3.0)
//        graph.addEdge(6, 7, 1.0)
//        graph.addEdge(6, 3, 7.0)
//
//        val result = ShortestPathFinder(graph).dijkstra(nodes[0])
//
//        assertEquals(8.0, result[nodes[4]])
//        assertEquals(7.0, result[nodes[6]])
//        assertEquals(6.0, result[nodes[5]])
//    }
//}
