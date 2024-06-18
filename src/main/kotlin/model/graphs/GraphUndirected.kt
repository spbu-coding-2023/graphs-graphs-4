package model.graphs

internal interface GraphUndirected<T> {
    fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>>

    fun findMinSpanTree(): Set<Edge<T>>?
}