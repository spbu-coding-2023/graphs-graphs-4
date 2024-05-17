package interfaces

import graphs.UndirectedGraph
import graphs.Vertex
import java.util.*

class Traversable<T> {
	// tested on connected undirected graph only
	fun dfsIter(graph: UndirectedGraph<T>, v: Vertex<T>): Set<Vertex<T>> {
		var dfsSet: Set<Vertex<T>> = emptySet()
		val stack: Stack<Vertex<T>> = Stack()
		val marked: HashMap<Vertex<T>, Boolean> = hashMapOf()

		stack.push(v)
		for (element in graph.adjList.keys) {
			marked[element] = false
		}

		while (!stack.isEmpty()) {
			val vertex = stack.pop()

			if (marked[vertex] == false) {
				dfsSet = dfsSet.plus(vertex)

				marked[vertex] = true

				graph.adjList[vertex]?.forEach {
					if (marked[it] == false) {
						stack.push(it)
					}
				}
			}
		}

		return dfsSet
	}
}
