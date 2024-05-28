package model.graphs

import model.functionality.ShortestPathFinder

interface Graph<GRAPH_TYPE, T> : Iterable<Vertex<T>> {
	val size: Int

	fun addVertex(key: T): Vertex<T>

	fun addVertex(vertex: Vertex<T>): Vertex<T>

	fun addVertices(vararg keys: T)

	fun addVertices(vararg vertices: Vertex<T>)

	fun vertices(): Set<Vertex<T>>

	fun edges(): Set<GraphEdge<T>>

	fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>>

	fun findDistancesBellman(start: Vertex<T>): Map<Vertex<T>, Double> {
		val output = ShortestPathFinder(this).bellmanFord(start)
		return output
	}

	override fun iterator(): Iterator<Vertex<T>>

	fun getNeighbors(vertex: Vertex<T>): HashSet<GRAPH_TYPE>
}
