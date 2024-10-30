package model.graphs

import model.functionality.TarjanSCC


interface GraphDirected<T, E: Edge<T>> : Graph<T, E> {
    fun findSCC(): Set<Set<Vertex<T>>>
    fun findCycles(startNode: Vertex<T>): HashSet<List<Vertex<T>>>
    fun Tarjan(startNode: Vertex<T>): HashSet<Vertex<T>> {
        return TarjanSCC<T, E>().findSCC(startNode, this)
    }
}
