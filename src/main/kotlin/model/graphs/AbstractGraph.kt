package model.graphs

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

	// need to test AND generalise
//	fun removeVertex(vertex: Vertex<T>) {
//		if (adjList[vertex] != null) {
//			adjList[vertex]?.forEach {
//				adjList[it]?.remove(vertex)
//			}
//
//			adjList.remove(vertex)
//		}
//
//		size -= 1
//	}

	// just converts graph to a set of vertices
	internal fun vertices(): Set<Vertex<T>> {
		return adjList.keys
	}

	//	fun dfsIterator(vertex: Vertex<T>): Iterator<Vertex<T>> {
//		return this.dfs(vertex).iterator()
//	}
//
//	// test?
//	internal fun dfs(vertex: Vertex<T>): Set<Vertex<T>> {
//		return Traversable<T>().dfsIter(this, vertex)
//	}
	// need to test
	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}
}
