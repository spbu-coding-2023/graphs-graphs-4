package model.graphs

import model.functionality.BridgeFinder

interface GraphUndirected<T, E: Edge<T>> : Graph<T, E> {
    fun findBridges(): Set<E> {
        return BridgeFinder<T, E>().findBridges(this)
    }

    fun findMinSpanTree(): Set<Edge<T>>?


    // Resolution parameter x > 0 for community detection
    // Higher resolution -> more communities
    // Higher randomness -> more random node movements
    fun runLeidenMethod(RANDOMNESS: Double, RESOLUTION: Double): HashSet<HashSet<Vertex<T>>>
}
