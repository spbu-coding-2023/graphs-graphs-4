package graphs

class DirectedWeightedGraph<T, NUMBER_TYPE : Number> : WeightedGraph<T, NUMBER_TYPE>() {
	// need to test?
	override fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: NUMBER_TYPE) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(Pair(vertex2, weight))
	}
}