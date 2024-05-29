package model.graphs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.functionality.BridgeFinder
import model.graphs.interfaces.Graph
import model.graphs.interfaces.UndirectedGraphAlgorithms

@Serializable
open class UndirectedGraph<T> :
	Graph<Edge<T>, T>, UndirectedGraphAlgorithms<T> {
	@SerialName("UndirectedGraph")
	open var adjList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()
		internal set

	override var size: Int = 0

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

	open fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		require(adjList.containsKey(vertex1))
		require(adjList.containsKey(vertex2))

		adjList.getOrPut(vertex1) { HashSet() }.add(vertex2)
		adjList.getOrPut(vertex2) { HashSet() }.add(vertex1)
	}

	open fun addEdge(key1: T, key2: T) {
		addEdge(Vertex(key1), Vertex(key2))
	}

	open fun addEdge(edge: Edge<T>) {
		addEdge(edge.from, edge.to)
	}

	open fun addEdges(vararg edges: Edge<T>) {
		for (edge in edges) {
			addEdge(edge)
		}
	}

	override fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>> {
		return BridgeFinder<Edge<T>, T>().findBridges(this)
	}

	override fun vertices(): Set<Vertex<T>> {
		return adjList.keys
	}

	override fun edges(): Set<Edge<T>> {
		val edges = HashSet<Edge<T>>()
		for (vertex in adjList.keys) {
			for (neighbour in adjList[vertex] ?: continue) {
				edges.add(Edge(vertex, neighbour))
			}
		}

		return edges
	}

	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}

	override fun getAdjEdges(vertex: Vertex<T>): Set<Edge<T>> {
		val neighbors = adjList[vertex] ?: throw IllegalArgumentException(
			"Can't get neighbors for vertex $vertex that is not in the graph"
		)

		val edges = HashSet<Edge<T>>()
		for (neighbour in neighbors) {
			edges.add(Edge(vertex, neighbour))
		}

		return edges
	}
}
