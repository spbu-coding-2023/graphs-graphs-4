package graphs

class WeightedGraph<T> : AbstractGraph<Pair<Vertex<T>, Double>, T>() {
	override var adjList: HashMap<Vertex<T>, HashSet<Pair<Vertex<T>, Double>>> = HashMap()

	fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: Double) {
		if (adjList.containsKey(vertex1) and adjList.containsKey(vertex2)) {
			adjList.getOrPut(vertex1) { HashSet() }.add(Pair(vertex2, weight))
			adjList.getOrPut(vertex2) { HashSet() }.add(Pair(vertex1, weight))
		} else {
			if (!adjList.containsKey(vertex1)) {
				throw IllegalArgumentException("Vertex1 does not exist")
			} else {
				throw IllegalArgumentException("Vertex2 does not exist")
			}
		}
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

	fun findMinAdjacentVertex(vertex: Vertex<T>): Vertex<T>?  {
		val neighbors = adjList[vertex] ?: return null
		val result = neighbors.maxBy { it.second ?: TODO() }.first

		return result
	}

	override fun iterator(): Iterator<Vertex<T>> {
		TODO("Not yet implemented")
	}
}