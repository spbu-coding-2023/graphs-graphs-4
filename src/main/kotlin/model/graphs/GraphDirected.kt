package model.graphs

interface GraphDirected<T> {
    fun findSCC(): Set<Set<Vertex<T>>>
}