package model.graphs

class DirectedWeightedGraph<T, NUMBER_TYPE : Number> : WeightedGraph<T, NUMBER_TYPE>() {
	// need to test?
	override fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: NUMBER_TYPE) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(Pair(vertex2, weight))
	}

	override fun addEdge(key1: T, key2: T, weight: NUMBER_TYPE) {
		addEdge(Vertex(key1), Vertex(key2), weight)
	}

	//Declaration clash error
//	override fun addEdges(vararg edges: Triple<Vertex<T>, Vertex<T>, NUMBER_TYPE>) {
//		for (edge in edges) {
//			val vertex1 = edge.first
//			val vertex2 = edge.second
//			val weight = edge.third
//			addEdge(vertex1, vertex2, weight)
//		}
//	}

	override fun addEdges(vararg edges: Triple<T, T, NUMBER_TYPE>) {
		for (edge in edges) {
			val key1 = edge.first
			val key2 = edge.second
			val weight = edge.third
			addEdge(key1, key2, weight)
		}
	}
}
