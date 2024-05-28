package model.graphs

import model.functionality.BridgeFinder

open class UndirectedGraph<T> : Graph<Vertex<T>, T> {
	var adjList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()
		private set

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
		return BridgeFinder<Vertex<T>, T>().findBridges(this)
	}

	override fun vertices(): Set<Vertex<T>> {
		return adjList.keys
	}

	override fun edges(): Set<Edge<T>> {
		val edges = HashSet<Edge<T>>()
		for (vertex in adjList.keys) {
			for (neighbour in adjList[vertex] ?: continue) {
				edges.add(Edge(vertex, neighbour, null))
			}
		}

		return edges
	}

	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}

	override fun getNeighbors(vertex: Vertex<T>): HashSet<Vertex<T>> {
		return adjList[vertex] ?: throw IllegalArgumentException(
			"Can't get neighbors for vertex $vertex that is not in the graph"
		)
	}

}
