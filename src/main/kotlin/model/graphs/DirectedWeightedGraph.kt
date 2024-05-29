package model.graphs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.functionality.MinSpanTreeFinder

@Serializable
class DirectedWeightedGraph<T, NUMBER_TYPE : Number> : UndirectedWeightedGraph<T, NUMBER_TYPE>() {
	@SerialName("DirectedWeightedGraph")
	override var adjList: HashMap<Vertex<T>, HashSet<Pair<Vertex<T>, NUMBER_TYPE>>> = HashMap()
		internal set

	override fun findSCC(): Set<Set<Vertex<T>>> {
		return emptySet()//StrConCompFinder(this as DirectedGraph<T>).sccSearch()
	}

	override fun findMinSpanTree(): Set<GraphEdge<T>>? {
		return MinSpanTreeFinder(this).mstSearch()
	}

	override fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: NUMBER_TYPE) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(Pair(vertex2, weight))
	}

	override fun addEdge(key1: T, key2: T, weight: NUMBER_TYPE) {
		addEdge(Vertex(key1), Vertex(key2), weight)
	}

	override fun addEdge(edge: WeightedEdge<T, NUMBER_TYPE>) {
		addEdge(edge.from, edge.to, edge.weight)
	}

	override fun addEdges(vararg edges: WeightedEdge<T, NUMBER_TYPE>) {
		for (edge in edges) {
			addEdge(edge)
		}
	}
}
