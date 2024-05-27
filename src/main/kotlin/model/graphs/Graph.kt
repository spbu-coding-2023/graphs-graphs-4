package model.graphs

interface Graph<GRAPH_TYPE, T> : Iterable<Vertex<T>> {
	fun addVertex(key: T): Vertex<T>

	fun addVertex(vertex: Vertex<T>): Vertex<T>

	fun addVertices(vararg keys: T)

	fun addVertices(vararg vertices: Vertex<T>)

	fun vertices(): Set<Vertex<T>>

	fun edges(): Set<GraphEdge<T>>

	override fun iterator(): Iterator<Vertex<T>>

	fun getNeighbors(vertex: Vertex<T>): HashSet<GRAPH_TYPE>
}
