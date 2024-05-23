package model.graphs

import model.functionality.BridgeFinder

open class UndirectedGraph<T> : AbstractGraph<Vertex<T>, T>() {
	override var adjList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()

	open fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(vertex2)
		adjList.getOrPut(vertex2) { HashSet() }.add(vertex1)
	}

	fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>> {
		return BridgeFinder<T>().findBridges(this)
	}
}
