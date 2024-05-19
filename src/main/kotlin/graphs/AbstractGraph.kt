package graphs

abstract class AbstractGraph<GRAPH_TYPE, T> : Iterable<Vertex<T>> {
	internal open var adjList: HashMap<Vertex<T>, HashSet<GRAPH_TYPE>> = HashMap()

    var size: Int = 0
        internal set

	fun addVertex(key: T): Vertex<T> {
		for (v in adjList.keys) {
			if (v.key == key) {
				return v
			}
		}

		val vertex = Vertex(key)
		adjList.putIfAbsent(vertex, HashSet())

		size += 1

		return vertex
	}

	// надо ли оно нам?
	// just converts graph to a set of vertices
	internal fun convertToVerticesSet(): Set<Vertex<T>> {
		return adjList.keys
	}

    // need to test
	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}
}
