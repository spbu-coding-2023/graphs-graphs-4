package model.functionality

import model.graphs.UndirectedGraph
import model.graphs.Vertex

class GraphIterator<K>(graph: UndirectedGraph<K>) : Iterator<Vertex<K>> {
    private val graphIterator = graph.adjList.keys.iterator()

    override fun hasNext() = graphIterator.hasNext()

    override fun next(): Vertex<K> {
        if (graphIterator.hasNext()) {
            return graphIterator.next()
        } else {
            throw NoSuchElementException()
        }
    }
}
