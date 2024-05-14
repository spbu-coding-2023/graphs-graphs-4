package graphsTest

import graphs.Graph
import graphs.Vertex
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

class GraphTest {
	private var graph = Graph<Int>()

	@Nested
	inner class AddEdgesTest {
		private val vertex1 = Vertex(1)
		private val vertex2 = Vertex(2)

		@BeforeEach
		fun init() {
			graph.adjacencyList[vertex1] = HashSet()
			graph.adjacencyList[vertex2] = HashSet()
		}

		@Test
		@DisplayName("Edge can't be added if at least one of the nodes does not exist")
		fun edgeException() {
			val graphString = Graph<String>()
			val vertex = Vertex("exists")

			graphString.adjacencyList[vertex] = HashSet()

			assertThrows(IllegalArgumentException::class.java) {graphString.addEdge(Vertex("doesn't exist"), Vertex("doesn't exist"))}
			assertThrows(IllegalArgumentException::class.java) {graphString.addEdge(vertex, Vertex("doesn't exist"))}
			assertThrows(IllegalArgumentException::class.java) {graphString.addEdge(Vertex("doesn't exist"), vertex)}
			assertDoesNotThrow { graphString.addEdge(vertex, vertex) }
		}

		@Test
		@DisplayName("When add an edge between two nodes, v1 points to v2, and v2 points to v1")
		fun edgeAdd() {
			graph.addEdge(vertex1, vertex2)

			assertEquals(true, graph.adjacencyList[vertex1]?.contains(vertex2) ?: false)
			assertEquals(true, graph.adjacencyList[vertex2]?.contains(vertex1) ?: false)
		}

		@Test
		@DisplayName("Don't create an edge if edge already exists")
		fun edgeAlreadyExists() {
			graph.adjacencyList[vertex1]?.add(vertex2)
			graph.adjacencyList[vertex2]?.add(vertex1)

			graph.addEdge(vertex1, vertex2)

			assertEquals(1, graph.adjacencyList[vertex1]?.count { it == vertex2 })
			assertEquals(1, graph.adjacencyList[vertex2]?.count { it == vertex1 })
		}
	}


	@Test
	@DisplayName("Add vertex")
	fun testAddVertex() {
		val v1: Vertex<Int> = Vertex(1)
		val v2: Vertex<Int> = Vertex(2)
		val v3: Vertex<Int> = Vertex(3)

		graph.addVertex(v1)
		graph.addVertex(v2)
		graph.addVertex(v3)

		val set = graph.adjacencyList
		val setTest: HashMap<Vertex<Int>, HashSet<Vertex<Int>>> = hashMapOf(
			v1 to hashSetOf(),
			v2 to hashSetOf(),
			v3 to hashSetOf()
		)

		assertEquals(setTest, set)
	}

	@Nested
	inner class TraverseTests {
		private var vertices: Array<Vertex<Int>> = emptyArray()

		@BeforeEach
		fun setup() {
			for (i in 0..9) {
				vertices = vertices.plus(graph.addVertex(Vertex(i)))
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

		@Test
		@DisplayName("Run dfs (connected - undirected graph)")
		fun dfsIterTest() {
			var assertSet: Set<Vertex<Int>> = emptySet()

			for (element in vertices) {
				assertSet = assertSet.plus(element)
			}

			assertEquals(assertSet, graph.dfs(vertices[0]))
		}
	}
}
