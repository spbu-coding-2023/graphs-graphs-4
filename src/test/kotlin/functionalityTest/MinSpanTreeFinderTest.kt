package functionalityTest

import model.graphs.UndirectedWeightedGraph
import model.graphs.Vertex
import model.graphs.WeightedEdge
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

class MinSpanTreeFinderTest {
    private lateinit var graphInt: UndirectedWeightedGraph<Int>
    private lateinit var expectedTree: MutableSet<WeightedEdge<Int>>

    @BeforeEach
    fun setup() {
        graphInt = UndirectedWeightedGraph()
        expectedTree = mutableSetOf()
    }

    @DisplayName("Impossible to find spanning tree (graph is not connected).")
    @Test
    fun mstTest1() {
        val vertices = Array(6) { Vertex(it) }
        val edges = arrayOf(
            WeightedEdge(vertices[0], vertices[1], 1.0),
            WeightedEdge(vertices[0], vertices[2], 2.0),
            WeightedEdge(vertices[0], vertices[3], 3.0),
            WeightedEdge(vertices[4], vertices[5], 4.0),
        )

        graphInt.addVertices(*vertices)
        graphInt.addEdges(*edges)

        assertEquals(emptySet(), graphInt.findMinSpanTree())
    }

    @DisplayName("Spanning tree equals to initial graph.")
    @Test
    fun mstTest2() {
        val vertices = Array(5) { Vertex(it) }
        val edges = arrayOf(
            WeightedEdge(vertices[0], vertices[1], 10.0),
            WeightedEdge(vertices[1], vertices[2], 12.0),
            WeightedEdge(vertices[2], vertices[3], 21.0),
            WeightedEdge(vertices[3], vertices[4], 23.0),
        )

        graphInt.addVertices(*vertices)
        graphInt.addEdges(*edges)

        expectedTree = mutableSetOf(*edges)

        //assertEquals(expectedTree.sorted(), graphInt.findMinSpanTree())
    }

    @DisplayName("Find minimal spanning tree in shamrock.")
    @Test
    fun mstTest3() {
        val vertices = Array(7) { Vertex(it) }
        val edges = arrayOf(
            WeightedEdge(vertices[0], vertices[1], 3.0),
            WeightedEdge(vertices[0], vertices[2], 2.0),
            WeightedEdge(vertices[0], vertices[3], 1.0),
            WeightedEdge(vertices[0], vertices[4], 3.0),
            WeightedEdge(vertices[0], vertices[5], 1.0),
            WeightedEdge(vertices[0], vertices[6], 2.0),
            WeightedEdge(vertices[1], vertices[2], 1.0),
            WeightedEdge(vertices[3], vertices[4], 2.0),
            WeightedEdge(vertices[5], vertices[6], 3.0),
        )

        graphInt.addVertices(*vertices)
        graphInt.addEdges(*edges)

        expectedTree = mutableSetOf(
            WeightedEdge(vertices[0], vertices[2], 2.0),
            WeightedEdge(vertices[0], vertices[3], 1.0),
            WeightedEdge(vertices[0], vertices[5], 1.0),
            WeightedEdge(vertices[0], vertices[6], 2.0),
            WeightedEdge(vertices[1], vertices[2], 1.0),
            WeightedEdge(vertices[3], vertices[4], 2.0),

            )

        //assertEquals(expectedTree.sorted(), graphInt.findMinSpanTree())
    }
}
