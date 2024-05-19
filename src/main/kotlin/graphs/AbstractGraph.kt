package graphs

abstract class AbstractGraph<GRAPH_TYPE, T> : Iterable<Vertex<T>> {
	open var adjList: HashMap<Vertex<T>, HashSet<GRAPH_TYPE>> = HashMap()
		protected set

	var size: Int = 0
        protected set

	fun addVertex(key: T): Vertex<T> {
		for (v in adjList.keys) {
			if (v.key == key) {
				return v
			}
		}

		val vertex = Vertex(key)
		adjList[vertex] = HashSet()

		size += 1

		return vertex
	}

	fun addVertex(vertex: Vertex<T>): Vertex<T> {
		if (adjList.containsKey(vertex)) {
			return vertex
		}

		adjList[vertex] = HashSet()

		size += 1

		return vertex
	}

	// need to test
	fun removeEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList[vertex1]?.remove<Any?>(vertex2)
		adjList[vertex2]?.remove<Any?>(vertex1)
	}

	// just converts graph to a set of vertices
	internal fun convertToVerticesSet(): Set<Vertex<T>> {
		return adjList.keys
	}

    // need to test
	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}
}