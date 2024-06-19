package model.graphs

interface GraphDirected<T> : Graph<T> {
    fun findSCC(): Set<Set<Vertex<T>>>
}