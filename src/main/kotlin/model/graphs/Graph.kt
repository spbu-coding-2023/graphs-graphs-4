package model.graphs

/*import kotlinx.serialization.Serializable
import model.functionality.iograph.GraphSerializer

@Serializable(with = GraphSerializer::class)*/
interface Graph<GRAPH_TYPE, T> : Iterable<Vertex<T>> {
	fun addVertex(key: T): Vertex<T>

	fun addVertex(vertex: Vertex<T>): Vertex<T>

	fun addVertices(vararg keys: T)

	fun addVertices(vararg vertices: Vertex<T>)

	fun vertices(): Set<Vertex<T>>

	fun edges(): Set<GraphEdge<T>>

	fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>>

	override fun iterator(): Iterator<Vertex<T>>

	fun getNeighbors(vertex: Vertex<T>): HashSet<GRAPH_TYPE>
}
