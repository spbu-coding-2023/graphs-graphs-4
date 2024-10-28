package model.graphs

interface GraphDirected<T, E: Edge<T>> : Graph<T, E> {
    fun findSCC(): Set<Set<Vertex<T>>>
}
