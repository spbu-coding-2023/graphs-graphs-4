package graphs

import functionality.ShortestPathFinder

open class WeightedGraph<T, NUMBER_TYPE : Number> : AbstractGraph<Pair<Vertex<T>, NUMBER_TYPE>, T>() {

	override var adjList: HashMap<Vertex<T>, HashSet<Pair<Vertex<T>, NUMBER_TYPE>>> = HashMap()

	open fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: NUMBER_TYPE) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(Pair(vertex2, weight))
		adjList.getOrPut(vertex2) { HashSet() }.add(Pair(vertex1, weight))
	}

	fun findShortestDistance(start: Vertex<T>): Map<Vertex<T>, Double> {
		val output = ShortestPathFinder(this).bellmanFord(start)
		return output
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

	fun findMinAdjacentVertexForPrimAlgo(vertex: Vertex<T>, spanningTree: Graph<T>): Pair<Vertex<T>, Long?>?  {
		val neighbors = adjList[vertex] ?: return null
		val result = neighbors.maxBy { pair ->
			val neighbor = pair.first
			val weight = pair.second

			if (spanningTree.contains(neighbor)) {
				0
			} else {
				weight ?: throw Exception("I except nothing")
			}
		}

		return result
	}

	override fun iterator(): Iterator<Vertex<T>> {
		TODO("Not yet implemented")
	}
}
