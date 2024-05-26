package model.graphs

import model.functionality.StrConCompFinder

class DirectedGraph<T> : UndirectedGraph<T>() {

	override fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(vertex2)
	}

	override fun addEdge(key1: T, key2: T) {
		addEdge(Vertex(key1), Vertex(key2))
	}

	override fun addEdge(edge: Edge<T>) {
		addEdge(edge.from, edge.to)
	}

	override fun addEdges(vararg edges: Edge<T>) {
		for (edge in edges) {
			addEdge(edge)
		}
	}

	fun findSCC(): Set<Set<Vertex<T>>> {
		return StrConCompFinder(this).sccSearch()
	}
}
