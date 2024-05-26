package model.graphs

import java.util.*

class Edge<K>(
    val from: K,
    val to: K,
    ): Comparable<Edge<K>>  {


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

    override fun compareTo(other: Edge<K>): Int {
        return when {
            from == other.from -> to.hashCode().compareTo(other.to.hashCode())
            else -> from.hashCode().compareTo(other.from.hashCode())
        }
    }
    }
