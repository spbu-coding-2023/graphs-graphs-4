package graphs

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GraphTest {
	private var graph = Graph<Int>()

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
		val setTest : HashMap<Vertex<Int>, HashSet<Vertex<Int>>> = hashMapOf(
			v1 to hashSetOf(),
			v2 to hashSetOf(),
			v3 to hashSetOf()
		)
		assertEquals(setTest, set)
	}

}