package model.graphs

class DirectedGraph<T> : UndirectedGraph<T>() {
	override fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(vertex2)
	}

	override fun addEdge(key1: T, key2: T) {
		addEdge(Vertex(key1), Vertex(key2))
	}

	//Declaration error clash
//	override fun addEdges(vararg edges: Pair<Vertex<T>, Vertex<T>>) {
//		for (edge in edges) {
//			addEdge(edge.first, edge.second)
//		}
//	}

	override fun addEdges(vararg edges: Pair<T, T>) {
		for (edge in edges) {
			addEdge(edge.first, edge.second)
		}
	}
}
