package graphs

import interfaces.Traversable
import interfaces.BridgeFinder

class Graph<T>: Iterable<Vertex<T>> {
	internal var adjacencyList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()

	var size: Int = adjacencyList.size
		private set

	// What should we do if vertex with given key already exists?
	// need to test?
	fun addVertex(vertex: Vertex<T>): Vertex<T> {
		adjacencyList.putIfAbsent(vertex, HashSet())
		return vertex
	}

	// Undirected graph -> we add both connections.
	// need to test
	fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		if (adjacencyList.containsKey(vertex1) and adjacencyList.containsKey(vertex2)) {
			adjacencyList.getOrPut(vertex1) { HashSet() }.add(vertex2)
			adjacencyList.getOrPut(vertex2) { HashSet() }.add(vertex1)
		} else {
			if (!adjacencyList.containsKey(vertex1)) {
				throw IllegalArgumentException("Vertex1 does not exist")
			} else {
				throw IllegalArgumentException("Vertex2 does not exist")
			}
		}
	}

	// Get the vertices adjacent to a given vertex
	// need to test
	fun giveNeighbors(vertex: Vertex<T>): Set<Vertex<T>>? {
		return adjacencyList[vertex]
	}

	// need to test
	fun removeEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		adjacencyList[vertex1]?.remove(vertex2)
		adjacencyList[vertex2]?.remove(vertex1)
	}

	// Can we reuse removeEdge?
	// need to test
	fun removeVertex(vertex: Vertex<T>) {
		if (adjacencyList[vertex] != null) {
			adjacencyList[vertex]?.forEach {
				adjacencyList[it]?.remove(vertex)
			}

			adjacencyList.remove(vertex)
		}
	}

	// test on disconnected graph?
	internal fun dfs(vertex: Vertex<T>): Set<Vertex<T>> {
		return Traversable<T>().dfsIter(this, vertex)
	}

	// Display the graph; for now for debug purposes mostly
	private fun printGraph() {
		for (key in adjacencyList.keys) {
			println("$key is connected to ${adjacencyList[key]}")
		}
	}

	override fun iterator(): Iterator<Vertex<T>> {
		return this.adjacencyList.keys.iterator()
	}

	fun dfsIterator(vertex: Vertex<T>): Iterator<Vertex<T>> {
		return this.dfs(vertex).iterator()
	}

	fun findBridges() {
		BridgeFinder<T>().findBridges(this)
	}
}
