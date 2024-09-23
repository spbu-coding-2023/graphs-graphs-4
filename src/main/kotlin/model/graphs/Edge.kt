package model.graphs

interface Edge<T> : Comparable<Edge<T>> {
    val from: Vertex<T>
    val to: Vertex<T>
    var copies: Int
}
