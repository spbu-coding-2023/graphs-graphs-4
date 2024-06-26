package model.graphs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.functionality.BridgeFinder
import model.functionality.MinSpanTreeFinder
import model.functionality.ShortestPathFinder

@Serializable
open class UndirectedWeightedGraph<T, NUMBER_TYPE : Number> : Graph<Pair<Vertex<T>, NUMBER_TYPE>, T> {
	@SerialName("UndirectedWeightedGraph")
	open var adjList: HashMap<Vertex<T>, HashSet<Pair<Vertex<T>, NUMBER_TYPE>>> = HashMap()
		internal set

	private var _size: Int = 0
	override val size: Int
		get() = _size

	@Suppress("DuplicatedCode")
	override fun addVertex(key: T): Vertex<T> {
		for (v in adjList.keys) {
			if (v.key == key) {
				return v
			}
		}

		val vertex = Vertex(key)
		adjList[vertex] = HashSet()

		_size += 1

		return vertex
	}

	override fun addVertex(vertex: Vertex<T>): Vertex<T> {
		if (adjList.containsKey(vertex)) {
			return vertex
		}

		adjList[vertex] = HashSet()

		_size += 1

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

	open fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>, weight: NUMBER_TYPE) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(Pair(vertex2, weight))
		adjList.getOrPut(vertex2) { HashSet() }.add(Pair(vertex1, weight))
	}

	open fun addEdge(key1: T, key2: T, weight: NUMBER_TYPE) {
		addEdge(Vertex(key1), Vertex(key2), weight)
	}

	open fun addEdge(edge: WeightedEdge<T, NUMBER_TYPE>) {
		addEdge(edge.from, edge.to, edge.weight)
	}

	open fun addEdges(vararg edges: WeightedEdge<T, NUMBER_TYPE>) {
		for (edge in edges) {
			addEdge(edge)
		}
	}

	fun findShortestDistance(start: Vertex<T>): Map<Vertex<T>, Double> {
		val output = ShortestPathFinder(this).bellmanFord(start)
		return output
	}

	override fun vertices(): Set<Vertex<T>> {
		return adjList.keys
	}

	override fun edges(): Set<WeightedEdge<T, NUMBER_TYPE>> {
		val edges = HashSet<WeightedEdge<T, NUMBER_TYPE>>()
		for (vertex in adjList.keys) {
			for (neighbour in adjList[vertex] ?: continue) {
				edges.add(WeightedEdge(vertex, neighbour.first, neighbour.second))
			}
		}

		return edges
	}

	override fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>> {
		return BridgeFinder<Pair<Vertex<T>, NUMBER_TYPE>, T>().findBridges(this)
	}

	override fun findSCC(): Set<Set<Vertex<T>>> {
		return emptySet()//StrConCompFinder(this as UndirectedGraph<T>).sccSearch()
	}

	override fun findMinSpanTree(): Set<GraphEdge<T>>? {
		return MinSpanTreeFinder(this).mstSearch()
	}

	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}

	override fun getNeighbors(vertex: Vertex<T>): HashSet<Pair<Vertex<T>, NUMBER_TYPE>> {
		return adjList[vertex] ?: throw IllegalArgumentException(
			"Can't get neighbors for vertex $vertex that is not in the graph"
		)
	}
}
