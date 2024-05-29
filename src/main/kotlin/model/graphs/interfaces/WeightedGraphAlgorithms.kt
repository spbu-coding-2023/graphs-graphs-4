package model.graphs.interfaces

import model.graphs.Vertex
import model.graphs.WeightedEdge

interface WeightedGraphAlgorithms<T, W: Comparable<W>> {
    fun findMinSpanTree(): Set<WeightedEdge<T, W>>?

    fun findShortestDistance(vertex: Vertex<T>): Map<Vertex<T>, Double>
}
