package functionalityTest

import model.graphs.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MinSpanTreeFinderTest {
    private lateinit var graphInt: WeightedGraph<Int, Int>
    private lateinit var expectedTree: MutableSet<WeightedEdge<Int, Int>>

    @BeforeEach
    fun setup() {
        graphInt = WeightedGraph()
        expectedTree = mutableSetOf()
    }

    @DisplayName("Impossible to find spanning tree (graph is not connected).")
    @Test
    fun mstTest1() {
        val vertices = Array(6) { Vertex(it) }
        val edges = arrayOf(
            WeightedEdge(0, 1, 1),
            WeightedEdge(0, 2, 2),
            WeightedEdge(0, 3, 3),
            WeightedEdge(4, 5, 4),
        )

        graphInt.addVertices(*vertices)
        graphInt.addEdges(*edges)

        assertEquals(null, graphInt.mstSearch())
    }

    @DisplayName("Spanning tree equals to initial graph.")
    @Test
    fun mstTest2() {
        val vertices = Array(5) { Vertex(it) }
        val edges = arrayOf(
            WeightedEdge(0, 1, 10),
            WeightedEdge(1, 2, 12),
            WeightedEdge(2, 3, 21),
            WeightedEdge(3, 4, 23),
        )

        graphInt.addVertices(*vertices)
        graphInt.addEdges(*edges)

        expectedTree = mutableSetOf(*edges)

        assertEquals(expectedTree, graphInt.mstSearch())
    }

    @DisplayName("Find minimal spanning tree in shamrock.")
    @Test
    fun mstTest3() {
        val vertices = Array(7) { Vertex(it) }
        val edges = arrayOf(
            WeightedEdge(0, 1, 3),
            WeightedEdge(0, 2, 2),
            WeightedEdge(0, 3, 1),
            WeightedEdge(0, 4, 3),
            WeightedEdge(0, 5, 1),
            WeightedEdge(0, 6, 2),
            WeightedEdge(1, 2, 1),
            WeightedEdge(3, 4, 2),
            WeightedEdge(5, 6, 3),
        )

        graphInt.addVertices(*vertices)
        graphInt.addEdges(*edges)

        expectedTree = mutableSetOf(
            WeightedEdge(0, 2, 2),
            WeightedEdge(0, 3, 1),
            WeightedEdge(0, 5, 1),
            WeightedEdge(0, 6, 2),
            WeightedEdge(1, 2, 1),
            WeightedEdge(3, 4, 2),

        )

        assertEquals(expectedTree.sorted(), graphInt.mstSearch()!!.sorted())
    }
}