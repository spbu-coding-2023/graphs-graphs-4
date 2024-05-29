package model.graphs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.functionality.MinSpanTreeFinder
import model.functionality.ShortestPathFinder
import model.functionality.StrConCompFinder
import model.graphs.interfaces.DirectedGraphAlgorithms
import model.graphs.interfaces.Graph
import model.graphs.interfaces.WeightedGraphAlgorithms

@Serializable
class DirectedWeightedGraph<T, W : Comparable<W>> :
	Graph<WeightedEdge<T, W>, T>, WeightedGraphAlgorithms<T, W>, DirectedGraphAlgorithms<T> {
	@SerialName("DirectedWeightedGraph")
	var adjList: HashMap<Vertex<T>, HashSet<Pair<Vertex<T>, W>>> = HashMap()
		internal set

	override var size: Int = 0

	override fun findSCC(): Set<Set<Vertex<T>>> {
		return StrConCompFinder(this).sccSearch()
	}

	override fun findMinSpanTree(): Set<WeightedEdge<T, W>>? {
		return MinSpanTreeFinder(this).mstSearch()
	}

	fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: W) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(Pair(vertex2, weight))
	}

	fun addEdge(key1: T, key2: T, weight: W) {
		addEdge(Vertex(key1), Vertex(key2), weight)
	}

	fun addEdge(edge: WeightedEdge<T, W>) {
		addEdge(edge.from, edge.to, edge.weight)
	}

	fun addEdges(vararg edges: WeightedEdge<T, W>) {
		for (edge in edges) {
			addEdge(edge)
		}
	}

	override fun addVertex(key: T): Vertex<T> {
		return addVertex(Vertex(key))
		/*for (v in adjList.keys) {
			if (v.key == key) {
				return v
			}
		}

		val vertex = Vertex(key)
		adjList[vertex] = HashSet()

		size += 1

		return vertex*/
	}

	override fun addVertex(vertex: Vertex<T>): Vertex<T> {
		if (adjList.containsKey(vertex)) {
			return vertex
		}

		adjList[vertex] = HashSet()

		size += 1

		return vertex
	}

	override fun addVertices(vararg keys: T) {
		for (key in keys) {
			addVertex(key)
		}
	}

	override fun addVertices(vararg vertices: Vertex<T>) {
		for (vertex in vertices) {
			addVertex(vertex)
		}
	}

	override fun findShortestDistance(vertex: Vertex<T>): Map<Vertex<T>, Double> {
		return ShortestPathFinder<T, W>(this).bellmanFord(vertex)
	}

	override fun vertices(): Set<Vertex<T>> {
		return adjList.keys
	}

	override fun edges(): Set<WeightedEdge<T, W>> {
		val edges = HashSet<WeightedEdge<T, W>>()
		for (vertex in adjList.keys) {
			for (pairVertexWeight in adjList[vertex] ?: continue) {
				val neighbor = pairVertexWeight.first
				val weight = pairVertexWeight.second
				edges.add(WeightedEdge(vertex, neighbor, weight))
			}
		}

		return edges
	}

	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}

	override fun getAdjEdges(vertex: Vertex<T>): Set<WeightedEdge<T, W>> {
		val adjEdges = adjList[vertex] ?: throw IllegalArgumentException(
			"Can't get neighbors for vertex $vertex that is not in the graph"
		)

		val edges = HashSet<WeightedEdge<T, W>>()
		for (pairVertexWeight in adjEdges) {
			val neighbor = pairVertexWeight.first
			val weight = pairVertexWeight.second
			edges.add(WeightedEdge(vertex, neighbor, weight))
		}

		return edges
	}
}
