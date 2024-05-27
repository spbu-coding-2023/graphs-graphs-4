package model.graphs

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class Edge<T>(
    override val from: Vertex<T>, override val to: Vertex<T>, override val weight: Nothing? = null
) : Comparable<Edge<T>>, GraphEdge<T> {

    override fun toString(): String {
        return "($from, $to)"
    }

    override fun equals(other: Any?): Boolean {
        return other is Edge<*> &&
            ((from == other.from && to == other.to) ||
                (from == other.to && to == other.from))
    }

    override fun hashCode(): Int {
        return Objects.hash(from, to)
    }

    override fun compareTo(other: Edge<T>): Int {
        return when {
            from == other.from -> to.hashCode().compareTo(other.to.hashCode())
            else -> from.hashCode().compareTo(other.from.hashCode())
        }
    }
}
