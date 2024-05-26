package model.graphs

import model.functionality.ShortestPathFinder

open class WeightedGraph<T, NUMBER_TYPE : Number> : Graph<Pair<Vertex<T>, NUMBER_TYPE>, T> {
	var adjList: HashMap<Vertex<T>, HashSet<Pair<Vertex<T>, NUMBER_TYPE>>> = HashMap()
		private set

	var size: Int = 0
		private set

	override fun addVertex(key: T): Vertex<T> {
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

	override fun addVertex(vertex: Vertex<T>): Vertex<T> {
		if (adjList.containsKey(vertex)) {
			return vertex
		}

		adjList[vertex] = HashSet()

		size += 1

		return vertex
	}

	override fun addVertices(vararg keys: T) {
		for (key in keys) {
			addVertex(key)
		}
	}

	override fun addVertices(vararg vertices: Vertex<T>) {
		for (vertex in vertices) {
			addVertex(vertex)
		}
	}

	open fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: NUMBER_TYPE) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(Pair(vertex2, weight))
		adjList.getOrPut(vertex2) { HashSet() }.add(Pair(vertex1, weight))
	}

	open fun addEdge(key1: T, key2: T, weight: NUMBER_TYPE) {
		addEdge(Vertex(key1), Vertex(key2), weight)
	}

	open fun addEdge(edge: WeightedEdge<T, NUMBER_TYPE>) {
		addEdge(edge.from, edge.to, edge. weight)
	}

	//Declaration clash error
//	open fun addEdges(vararg edges: Triple<Vertex<T>, Vertex<T>, NUMBER_TYPE>) {
//		for (edge in edges) {
//			val vertex1 = edge.first
//			val vertex2 = edge.second
//			val weight = edge.third
//			addEdge(vertex1, vertex2, weight)
//		}
//	}

	open fun addEdges(vararg edges: WeightedEdge<T, NUMBER_TYPE>) {
		for (edge in edges) {
			addEdge(edge)
		}
	}

	fun findShortestDistance(start: Vertex<T>): Map<Vertex<T>, Double> {
		val output = ShortestPathFinder(this).bellmanFord(start)
		return output
	}

	override fun vertices(): Set<Vertex<T>> {
		return adjList.keys
	}

//	// need to test
//	fun removeEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
//		adjList[vertex1]?.removeAll { it.first == vertex2 }
//		adjList[vertex2]?.removeAll { it.first == vertex1 }
//	}

	//	// Can we reuse removeEdge?
	//	// need to test
	//	fun removeVertex(vertex: Vertex<T>) {
	//		if (adjList[vertex] != null) {
	//			adjList[vertex]?.forEach {
	//				adjList[it]?.remove(vertex)
	//			}
	//
	//			adjList.remove(vertex)
	//		}
	//	}
	//
	//	// test on disconnected graph?
	//	internal fun dfs(vertex: Vertex<T>): Set<Vertex<T>> {
	//		return Traversable<T>().dfsIter(this, vertex)
	//	}
	//
	//	override fun iterator(): Iterator<Vertex<T>> {
	//		return this.adjList.keys.iterator()
	//	}
	//
	//	fun dfsIterator(vertex: Vertex<T>): Iterator<Vertex<T>> {
	//		return this.dfs(vertex).iterator()
	//	}
	//
	//	fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>> {
	//		return BridgeFinder<T>().findBridges(this)
	//	}

//	fun findMinAdjacentVertexForPrimAlgo(vertex: Vertex<T>, spanningTree: Graph<T>): WeightedEdge<T>?  {
//		val neighbors = adjList[vertex] ?: return null
//		val result = neighbors.maxBy { edge ->
//			if (spanningTree.contains(edge.vertex)) {
//				0
//			} else {
//				edge.weight
//			}
//		}
//
//		return result
//	}.

	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}

	override fun getNeighbors(vertex: Vertex<T>): HashSet<Pair<Vertex<T>, NUMBER_TYPE>> {
		return adjList[vertex] ?: throw IllegalArgumentException(
			"Can't get neighbors for vertex $vertex that is not in the graph"
		)
	}

}
