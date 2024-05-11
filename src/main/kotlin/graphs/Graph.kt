package graphs

import algorithms.Traversable

class Graph<T> {
	internal var adjacencyList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()

	// Need to test if add(remove)Vertex updates size
	var size: Int = 0
		private set

	// What should we do if vertex with given key already exists?
	// need to test?
	fun addVertex(vertex: Vertex<T>): Vertex<T> {
		adjacencyList.putIfAbsent(vertex, HashSet())
		size += 1

		return vertex
	}

	// Undirected graph -> we add both connections.
	// need to test
	fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		adjacencyList.getOrPut(vertex1) { HashSet() }.add(vertex2)
		adjacencyList.getOrPut(vertex2) { HashSet() }.add(vertex1)
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

			size -= 1
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
}
