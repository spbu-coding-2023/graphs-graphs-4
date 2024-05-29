package graphsTest

import model.graphs.UndirectedGraph
import model.graphs.Vertex
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GraphTest {
	private var graph = UndirectedGraph<Int>()

	@Nested
	inner class AddVertexTest {
		@Test
		@DisplayName("New vertex in an empty graph")
		fun emptyGraph() {
			val v1 = graph.addVertex(1)

			val answer = HashMap<Vertex<Int>, HashSet<Vertex<Int>>>()
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
		private val vertex1 = Vertex(1)
		private val vertex2 = Vertex(2)

		@BeforeEach
		fun init() {
			graph.adjList[vertex1] = HashSet()
			graph.adjList[vertex2] = HashSet()
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
			assertThrows(IllegalArgumentException::class.java) { graphString.addEdge(vertex, Vertex("doesn't exist")) }
			assertThrows(IllegalArgumentException::class.java) { graphString.addEdge(Vertex("doesn't exist"), vertex) }
			assertDoesNotThrow { graphString.addEdge(vertex, vertex) }
		}

		@Test
		@DisplayName("When add an edge between two nodes, v1 points to v2, and v2 points to v1")
		fun edgeAdd() {
			graph.addEdge(vertex1, vertex2)

			assertEquals(true, graph.adjList[vertex1]?.contains(vertex2) ?: false)
			assertEquals(true, graph.adjList[vertex2]?.contains(vertex1) ?: false)
		}

		@Test
		@DisplayName("Don't create an edge if edge already exists")
		fun edgeAlreadyExists() {
			graph.adjList[vertex1]?.add(vertex2)
			graph.adjList[vertex2]?.add(vertex1)

			graph.addEdge(vertex1, vertex2)

			assertEquals(1, graph.adjList[vertex1]?.count { it == vertex2 })
			assertEquals(1, graph.adjList[vertex2]?.count { it == vertex1 })
		}
	}

	// мб стоит вынести в отдельный класс
	@Nested
	inner class TraverseTests {
		private var vertices: Array<Vertex<Int>> = emptyArray()

		@BeforeEach
		fun setup() {
			for (i in 0..9) {
				vertices = vertices.plus(graph.addVertex(i))
			}
			// cool graph I saw in a video
			graph.addEdge(vertices[0], vertices[1])
			graph.addEdge(vertices[0], vertices[2])
			graph.addEdge(vertices[1], vertices[2])
			graph.addEdge(vertices[1], vertices[3])
			graph.addEdge(vertices[1], vertices[4])
			graph.addEdge(vertices[3], vertices[5])
			graph.addEdge(vertices[5], vertices[6])
			graph.addEdge(vertices[5], vertices[7])
			graph.addEdge(vertices[5], vertices[8])
			graph.addEdge(vertices[8], vertices[9])
		}
	}
}
