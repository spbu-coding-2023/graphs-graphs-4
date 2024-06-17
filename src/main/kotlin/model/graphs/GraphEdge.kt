package model.graphs

interface GraphEdge<T> {
    val from: Vertex<T>
    val to: Vertex<T>
    val weight: Number?
}
