package model.graphs.interfaces

import model.graphs.Vertex

interface UndirectedGraphAlgorithms<T> {
    fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>>
}
