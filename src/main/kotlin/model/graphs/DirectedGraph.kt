package model.graphs

class DirectedGraph<T> : UndirectedGraph<T>() {
	override fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(vertex2)
	}
}