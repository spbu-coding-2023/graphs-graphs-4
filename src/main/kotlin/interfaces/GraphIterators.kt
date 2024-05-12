package interfaces

import graphs.Graph
import graphs.Vertex

class GraphIterator<K>(graph: Graph<K>) : Iterator<Vertex<K>> {
    private val graphIterator = graph.adjacencyList.keys.iterator()

    override fun hasNext() = graphIterator.hasNext()

    override fun next() = graphIterator.next()
}