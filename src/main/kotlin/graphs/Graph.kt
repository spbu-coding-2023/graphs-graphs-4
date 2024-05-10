package graphs

class Graph<T> {
	// methods are all private for now

	private var adjacencyList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()

	private fun addVertex(vertex: Vertex<T>) {
		adjacencyList.putIfAbsent(vertex, HashSet())
	}

	// Undirected graph -> we add both connections.
	// comments below are temp
	// okay so what's going on there? here's example:
	// https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMS45LjI0IiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiaW1wb3J0IGtvdGxpbi50ZXN0LipcbmltcG9ydCBqYXZhLnV0aWwuKlxuXG5mdW4gbWFpbihhcmdzOiBBcnJheTxTdHJpbmc+KSB7XG5cbnZhbCBtYXAgPSBtdXRhYmxlTWFwT2Y8U3RyaW5nLCBIYXNoU2V0PEludD4+KClcblxubWFwLmdldE9yUHV0KFwieFwiKSB7IEhhc2hTZXQoKSB9LmFkZCgyKVxubWFwLmdldE9yUHV0KFwieFwiKSB7IEhhc2hTZXQoKSB9LmFkZCg0KVxucHJpbnRsbihtYXApXG5cbn0ifQ==
	private fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		adjacencyList.getOrPut(vertex1) { HashSet() }.add(vertex2)
		adjacencyList.getOrPut(vertex2) { HashSet() }.add(vertex1)
	}
	// Get the vertices adjacent to a given vertex
	private fun giveNeighbors(vertex: Vertex<T>): Set<Vertex<T>>? {
		return adjacencyList[vertex]
	}

	// Display the graph; for now for debug purposes mostly
	private fun printGraph() {
		for (key in adjacencyList.keys) {
			println("$key is connected to ${adjacencyList[key]}")
		}
	}
}
