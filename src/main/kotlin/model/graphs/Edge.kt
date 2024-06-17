package model.graphs

interface Edge<T> : Comparable<Edge<T>> {
    val to: Vertex<T>
}
