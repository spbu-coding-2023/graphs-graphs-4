package interfaces

import graphs.UndirectedGraph
import graphs.Vertex

class GraphIterator<K>(graph: UndirectedGraph<K>) : Iterator<Vertex<K>> {
	private val graphIterator = graph.adjList.keys.iterator()

	override fun hasNext() = graphIterator.hasNext()

	override fun next() = graphIterator.next()
}