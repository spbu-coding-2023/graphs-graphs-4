package model.graphs.interfaces

import model.graphs.Vertex

interface Graph<E: GraphEdge<T>, T> : Iterable<Vertex<T>> {
	var size: Int

	fun addVertex(key: T): Vertex<T>

	fun addVertex(vertex: Vertex<T>): Vertex<T>

	fun addVertices(vararg keys: T)

	fun addVertices(vararg vertices: Vertex<T>)

	fun vertices(): Set<Vertex<T>>

	fun edges(): Set<E>

	override fun iterator(): Iterator<Vertex<T>>

	fun getAdjEdges(vertex: Vertex<T>): Set<E>
}
