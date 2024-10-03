package model.graphs

import kotlinx.serialization.Serializable

@Serializable
data class UnweightedEdge<T>(override val from: Vertex<T>, override val to: Vertex<T>) : Edge<T> {
    override var copies: Int = 1

    override fun compareTo(other: Edge<T>): Int {
        return if (this == other) 0 else -1
    }

    override fun equals(other: Any?): Boolean {
        return other is UnweightedEdge<*> &&
            ((from == other.from && to == other.to) ||
                (from == other.to && to == other.from))
    }

    override fun toString(): String {
        return "($from, $to)"
    }
}
