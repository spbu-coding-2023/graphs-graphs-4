package model.graphs

import java.util.HashSet

interface GraphDirected<T> : Graph<T> {
    fun findSCC(): Set<Set<Vertex<T>>>
    fun findCycles(startNode: Vertex<T>): HashSet<List<Vertex<T>>>
}