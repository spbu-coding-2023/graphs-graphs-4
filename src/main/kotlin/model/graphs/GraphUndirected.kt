package model.graphs

import model.functionality.BridgeFinder

interface GraphUndirected<T, E: Edge<T>> : Graph<T, E> {
    fun findBridges(): Set<E> {
        return BridgeFinder<T, E>().findBridges(this)
    }

    fun findMinSpanTree(): Set<Edge<T>>?

    fun runLeidenMethod(): HashSet<HashSet<Vertex<T>>>
}