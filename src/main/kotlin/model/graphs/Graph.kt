package model.graphs

interface Graph<GRAPH_TYPE, T> : Iterable<Vertex<T>> {

	var adjList: HashMap<Vertex<T>, HashSet<GRAPH_TYPE>>

	var size: Int

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

	fun addVertices(vararg keys: T): Unit {
		for (key in keys) {
			addVertex(key)
		}
	}

	fun addVertices(vararg vertices: Vertex<T>): Unit {
		for (vertex in vertices) {
			addVertex(vertex)
		}
	}

	// need to test
	//Странно, что добавлять узлы в абстрактном графе нельзя, а удалять - можно.
	//Причём он неверно будет работать для ориентированного графа.
	fun removeEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList[vertex1]?.remove<Any?>(vertex2)
		adjList[vertex2]?.remove<Any?>(vertex1)
	}

	fun <E: Edge<T>>removeEdge(edge: E ) {
		val vertex1 = Vertex(edge.from)
		val vertex2 = Vertex(edge.to)

		return removeEdge(vertex1, vertex2)
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
	fun vertices(): Set<Vertex<T>> {
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

	fun getNeighbors(vertex: Vertex<T>): HashSet<GRAPH_TYPE> {
		return adjList[vertex] ?: throw IllegalArgumentException(
			"Can't get neighbors for vertex $vertex that is not in the graph"
		)
	}

	fun getNeighbors(key: T): HashSet<GRAPH_TYPE> {
		return getNeighbors(Vertex(key))
	}
}
