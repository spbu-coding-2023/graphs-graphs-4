package graphs

import interfaces.BridgeFinder
import interfaces.Traversable

class Graph<T> : Iterable<Vertex<T>> {
	internal var adjList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()

	var size: Int = adjList.size
		private set

	fun addVertex(key: T): Vertex<T> {
		val vertex = Vertex(key)
		adjList.putIfAbsent(vertex, HashSet())
		return vertex
	}

	// Undirected graph -> we add both connections.
	fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		if (adjList.containsKey(vertex1) and adjList.containsKey(vertex2)) {
			adjList.getOrPut(vertex1) { HashSet() }.add(vertex2)
			adjList.getOrPut(vertex2) { HashSet() }.add(vertex1)
		} else {
			if (!adjList.containsKey(vertex1)) {
				throw IllegalArgumentException("Vertex1 does not exist")
			} else {
				throw IllegalArgumentException("Vertex2 does not exist")
			}
		}
	}

	// Get the vertices adjacent to a given vertex
	// need to test
	fun giveNeighbors(vertex: Vertex<T>): Set<Vertex<T>>? {
		return adjList[vertex]
	}

	// need to test
	fun removeEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		adjList[vertex1]?.remove(vertex2)
		adjList[vertex2]?.remove(vertex1)
	}

	// Can we reuse removeEdge?
	// need to test
	fun removeVertex(vertex: Vertex<T>) {
		if (adjList[vertex] != null) {
			adjList[vertex]?.forEach {
				adjList[it]?.remove(vertex)
			}

			adjList.remove(vertex)
		}
	}

	// test on disconnected graph?
	internal fun dfs(vertex: Vertex<T>): Set<Vertex<T>> {
		return Traversable<T>().dfsIter(this, vertex)
	}

	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjList.keys.iterator()
	}

	fun dfsIterator(vertex: Vertex<T>): Iterator<Vertex<T>> {
		return this.dfs(vertex).iterator()
	}

	fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>> {
		return BridgeFinder<T>().findBridges(this)
	}
}
