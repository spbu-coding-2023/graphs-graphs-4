package algorithms

import graphs.Graph
import graphs.Vertex
import java.util.*

class Traversable<T> {
	// preorder
	fun dfsIter(graph: Graph<T>, v: Vertex<T>) {
		val marked : HashMap<Vertex<T>, Boolean> = hashMapOf()
		for (element in graph.adjacencyList.keys) {
			marked[element] = false
		}
		val stack: Stack<Vertex<T>> = Stack()
		stack.push(v)
		while (!stack.isEmpty()) {
			val vertex = stack.pop()
			if (marked[vertex] == false) {
				println(vertex.key)
				marked[vertex] = true
				graph.adjacencyList[vertex]?.forEach {
					if (marked[it] == false) {
						stack.push(it)
					}
				}
			}
		}
	}
}