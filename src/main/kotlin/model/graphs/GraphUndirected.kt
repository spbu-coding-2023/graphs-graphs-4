package model.graphs

import model.functionality.BridgeFinder

interface GraphUndirected<T> : Graph<T> {
    fun findBridges(): Set<Edge<T>> {
        return BridgeFinder<T>().findBridges(this)
    }

    fun findMinSpanTree(): Set<Edge<T>>?

    fun runLeidenMethod()
}