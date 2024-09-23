package model.graphs

import kotlinx.serialization.Serializable

@Serializable
data class WeightedEdge<T>(override val from: Vertex<T>, override val to: Vertex<T>, var weight: Double) : Edge<T> {
    override var copies: Int = 1

    override fun compareTo(other: Edge<T>): Int {
        return if (other is WeightedEdge<T>) weight.compareTo(other.weight) else 1
    }

    override fun equals(other: Any?): Boolean {
        return other is WeightedEdge<*> && (weight == other.weight) &&
            ((from == other.from && to == other.to) ||
                (from == other.to && to == other.from))
    }
}
