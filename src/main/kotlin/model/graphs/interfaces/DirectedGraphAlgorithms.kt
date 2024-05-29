package model.graphs.interfaces

import model.graphs.Vertex

interface DirectedGraphAlgorithms<T> {
    fun findSCC(): Set<Set<Vertex<T>>>
}
