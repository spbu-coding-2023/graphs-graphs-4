package model.graphs

import java.util.*

data class WeightedEdge<T, W : Number>(
    override val from: Vertex<T>,
    override val to: Vertex<T>,
    override val weight: W,
) : Comparable<WeightedEdge<T, W>>, GraphEdge<T> {
    operator fun Number.minus(other: Number): Number {
        return when (this) {
            is Long -> this - other.toLong()
            is Int -> this - other.toLong()
            is Short -> this - other.toLong()
            is Double -> this - other.toDouble()
            is Float -> this - other.toDouble()
            else -> throw IllegalArgumentException("Unknown numeric type")
        }
    }

    operator fun Number.compareTo(other: Number): Int {
        return when (this) {
            is Long -> this.compareTo(other.toLong())
            is Int -> this.compareTo(other.toLong())
            is Short -> this.compareTo(other.toLong())
            is Double -> this.compareTo(other.toDouble())
            is Float -> this.compareTo(other.toDouble())
            else -> throw IllegalArgumentException("Unknown numeric type")
        }
    }

    override fun toString(): String {
        return "($from,$to|$weight)"
    }

    override fun equals(other: Any?): Boolean {
        return other is WeightedEdge<*, *> && (weight == other.weight) &&
            ((from == other.from && to == other.to) ||
                (from == other.to && to == other.from))
    }

    override fun hashCode(): Int {
        return Objects.hash(from, to, weight)
    }

    override fun compareTo(other: WeightedEdge<T, W>): Int {
        return when {
            weight == other.weight && from == other.from -> to.hashCode().compareTo(other.to.hashCode())
            weight == other.weight -> from.hashCode().compareTo(other.from.hashCode())
            else -> weight.compareTo(other.weight)
        }
    }
}
