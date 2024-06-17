package functionalityTest

import model.graphs.DirectedGraph
import model.graphs.Edge
import model.graphs.Vertex
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

class StrConCompFinderTest {
    private lateinit var graphInt: DirectedGraph<Int>
    private lateinit var expectedSCC: MutableSet<Set<Vertex<Int>>>

    @BeforeEach
    fun clear() {
        graphInt = DirectedGraph()
        expectedSCC = mutableSetOf()
    }

    @Test
    @DisplayName("Max-edged graph.")
    fun sccTest1() {
        val vertices = Array(6) { Vertex(it) }

        graphInt.addVertices(*vertices)

        for (vertex1 in 0..5) {
            for (vertex2 in 0..5) {
                if (vertex1 != vertex2) {
                    graphInt.addEdge(vertex1, vertex2)
                }
            }
        }

        val component = vertices.toSet()
        expectedSCC.add(component)

        assertEquals(expectedSCC, graphInt.findSCC())
    }

    @Test
    @DisplayName("Zero-edged graph.")
    fun sccTest2() {
        val vertices = Array(6) { Vertex(it) }

        graphInt.addVertices(*vertices)

        for (vertex in vertices) {
            val component = setOf(vertex)
            expectedSCC.add(component)
        }

        assertEquals(expectedSCC, graphInt.findSCC())
    }

    @Test
    @DisplayName("3 strong connected components without edges between them.")
    fun sccTest3() {
        val vertices = Array(8) { Vertex(it) }
        val edges = arrayOf(
            Edge(vertices[0], vertices[1]),
            Edge(vertices[1], vertices[0]),
            Edge(vertices[2], vertices[3]),
            Edge(vertices[3], vertices[4]),
            Edge(vertices[4], vertices[2]),
            Edge(vertices[5], vertices[6]),
            Edge(vertices[6], vertices[7]),
            Edge(vertices[7], vertices[6]),
            Edge(vertices[7], vertices[5]),
        )

        graphInt.addVertices(*vertices)
        graphInt.addEdges(*edges)
        expectedSCC = mutableSetOf(
            setOf(Vertex(0), Vertex(1)),
            setOf(Vertex(2), Vertex(3), Vertex(4)),
            setOf(Vertex(5), Vertex(6), Vertex(7)),
        )

        assertEquals(expectedSCC, graphInt.findSCC())
    }
}
