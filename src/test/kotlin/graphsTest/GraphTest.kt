package graphsTest

import model.graphs.UndirectedGraph
import model.graphs.UnweightedEdge
import model.graphs.Vertex
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class GraphTest {
    private var graph = UndirectedGraph<Int>()

    @Nested
    inner class AddVertexTest {
        @Test
        @DisplayName("New vertex in an empty graph")
        fun emptyGraph() {
            val v1 = graph.addVertex(1)

            val answer = HashMap<Vertex<Int>, HashSet<UnweightedEdge<Int>>>()
            answer[v1] = HashSet()

            assertEquals(answer, graph.adjList)
        }

        @Test
        @DisplayName("New vertex in a non-empty graph")
        fun addVertex() {
            val v1 = graph.addVertex(1)
            val v2 = graph.addVertex(2)

            val answer = HashMap<Vertex<Int>, HashSet<Vertex<Int>>>()
            answer[v1] = HashSet()
            answer[v2] = HashSet()

            assertEquals(answer, graph.adjList)
        }

        @Test
        @DisplayName("Return existing node if it has the same key as given key")
        fun noDoubles() {
            val v1 = graph.addVertex(1)
            val testVertex = graph.addVertex(1)

            val answer = HashMap<Vertex<Int>, HashSet<Vertex<Int>>>()
            answer[v1] = HashSet()

            assertEquals(answer, graph.adjList)
            assertEquals(v1, testVertex)
        }
    }

    @Nested
    inner class AddEdgesTest {
        private val v1 = Vertex(1)
        private val v2 = Vertex(2)

        @BeforeEach
        fun init() {
            graph.adjList[v1] = HashSet()
            graph.adjList[v2] = HashSet()
        }

        @Test
        @DisplayName("Edge can't be added if at least one of the nodes does not exist")
        fun edgeException() {
            val graphString = UndirectedGraph<String>()
            val vertex = Vertex("exists")

            graphString.adjList[vertex] = HashSet()

            assertThrows(IllegalArgumentException::class.java) {
                graphString.addEdge(
                    Vertex("doesn't exist"),
                    Vertex("doesn't exist")
                )
            }
            assertThrows(IllegalArgumentException::class.java) {
                graphString.addEdge(vertex, Vertex("doesn't exist"))
            }
            assertThrows(IllegalArgumentException::class.java) {
                graphString.addEdge(Vertex("doesn't exist"), vertex)
            }
            assertDoesNotThrow {
                graphString.addEdge(vertex, vertex)
            }
        }

        @Test
        @DisplayName("When add an edge between two nodes, v1 points to v2, and v2 points to v1")
        fun edgeAdd() {
            graph.addEdge(v1, v2)

            val edge1 = UnweightedEdge(v1, v2)
            val edge2 = UnweightedEdge(v2, v1)
            assertTrue {
                graph.adjList[v1]!!.contains(edge1)
                    && graph.adjList[v2]!!.contains(edge2)
            }
        }

        @Test
        @DisplayName("Don't create an edge if edge already exists; throws exception")
        fun edgeAlreadyExists() {
            graph.adjList[v1]?.add(UnweightedEdge(v1, v2))
            graph.adjList[v2]?.add(UnweightedEdge(v1, v2))

            assertFails { graph.addEdge(v1, v2) }
        }
    }
}
