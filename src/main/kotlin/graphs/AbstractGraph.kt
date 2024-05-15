package graphs

import interfaces.Traversable

abstract class AbstractGraph<GRAPH_TYPE, T> : Iterable<Vertex<T>> {
	internal open var adjList: HashMap<Vertex<T>, HashSet<GRAPH_TYPE>> = HashMap()

	// стоит подумать а нельзя ли быстрее проверять на наличие узла
	// с каким-то ключем
	fun addVertex(key: T): Vertex<T> {
		for (v in adjList.keys) {
			if (v.key == key) {
				return v
			}
		}

		val vertex = Vertex(key)
		adjList.putIfAbsent(vertex, HashSet())

		return vertex
	}

	fun addVertex(vertex: Vertex<T>): Vertex<T> {
		adjList.putIfAbsent(vertex, HashSet())

		return vertex
	}

	// Get the vertices adjacent to a given vertex
	// need to test
	fun giveNeighbors(vertex: Vertex<T>): Set<GRAPH_TYPE>? {
		return adjList[vertex]
	}

	//just converts graph to a set of vertices
	fun convertToVerticesSet(): Set<Vertex<T>> {
		return adjList.keys
	}

	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}
}