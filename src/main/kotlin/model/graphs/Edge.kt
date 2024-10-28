package model.graphs

interface Edge<T> : Comparable<Edge<T>> {
    val from: Vertex<T>
    val to: Vertex<T>
    var copies: Int

    fun reverse(): Edge<T>

    fun contains(v: Vertex<T>): Boolean {
        return from == v || to == v
    }
}
