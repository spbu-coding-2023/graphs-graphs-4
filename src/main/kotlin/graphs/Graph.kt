package graphs

import algorithms.Traversable

class Graph<T> {
	var size: Int = 0
		private set

	internal var adjacencyList: HashMap<Vertex<T>, HashSet<Vertex<T>>> = HashMap()

	// как поступать с узлами с одинаковыми ключами?
	fun addVertex(vertex: Vertex<T>) : Vertex<T> {
		adjacencyList.putIfAbsent(vertex, HashSet())
		size += 1
		return vertex
	}

	// Undirected graph -> we add both connections.
	// comments below are temp
	// okay so what's going on there? here's example:
	// https://play.kotlinlang.org/#eyJ2ZXJzaW9uIjoiMS45LjI0IiwicGxhdGZvcm0iOiJqYXZhIiwiYXJncyI6IiIsIm5vbmVNYXJrZXJzIjp0cnVlLCJ0aGVtZSI6ImlkZWEiLCJjb2RlIjoiaW1wb3J0IGtvdGxpbi50ZXN0LipcbmltcG9ydCBqYXZhLnV0aWwuKlxuXG5mdW4gbWFpbihhcmdzOiBBcnJheTxTdHJpbmc+KSB7XG5cbnZhbCBtYXAgPSBtdXRhYmxlTWFwT2Y8U3RyaW5nLCBIYXNoU2V0PEludD4+KClcblxubWFwLmdldE9yUHV0KFwieFwiKSB7IEhhc2hTZXQoKSB9LmFkZCgyKVxubWFwLmdldE9yUHV0KFwieFwiKSB7IEhhc2hTZXQoKSB9LmFkZCg0KVxucHJpbnRsbihtYXApXG5cbn0ifQ==
	fun addEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		adjacencyList.getOrPut(vertex1) { HashSet() }.add(vertex2)
		adjacencyList.getOrPut(vertex2) { HashSet() }.add(vertex1)
	}
	// Get the vertices adjacent to a given vertex
	fun giveNeighbors(vertex: Vertex<T>): Set<Vertex<T>>? {
		return adjacencyList[vertex]
	}

	fun removeEdge(vertex1: Vertex<T>, vertex2: Vertex<T>) {
		adjacencyList[vertex1]?.remove(vertex2)
		adjacencyList[vertex2]?.remove(vertex1)
	}

	// скорее всего можно как-то переиспользовать removeEdge
	// надо избавиться от !!
	fun removeVertex(vertex: Vertex<T>) {
		if (adjacencyList[vertex] != null) {
			for (element in adjacencyList[vertex]!!) {
				adjacencyList[element]?.remove(vertex)
			}
			adjacencyList.remove(vertex)
			size -= 1
		}
	}


	internal fun dfs(vertex: Vertex<T>) {
		Traversable<T>().dfsIter(this, vertex)
	}

	// Display the graph; for now for debug purposes mostly
	private fun printGraph() {
		for (key in adjacencyList.keys) {
			println("$key is connected to ${adjacencyList[key]}")
		}
		adjacencyList.entries
	}
}
