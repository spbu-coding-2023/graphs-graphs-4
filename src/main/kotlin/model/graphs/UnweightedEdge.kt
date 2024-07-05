package model.graphs

import kotlinx.serialization.Serializable

@Serializable
data class UnweightedEdge<T>(override val from: Vertex<T>, override val to: Vertex<T>) : Edge<T> {

    override fun compareTo(other: Edge<T>): Int {
        TODO("Not yet implemented")
    }

    override var copies: Int = 1

    // override fun toString(): String {
    //        return "($from, $to)"
    //    }
    //
    //    override fun equals(other: Any?): Boolean {
    //        return other is UnweightedEdge<*> &&
    //            ((from == other.from && to == other.to) ||
    //                (from == other.to && to == other.from))
    //    }
    //
    //    override fun hashCode(): Int {
    //        return Objects.hash(from, to)
    //    }
    //
    //    override fun compareTo(other: UnweightedEdge<T>): Int {
    //        return when {
    //            from == other.from -> to.hashCode().compareTo(other.to.hashCode())
    //            else -> from.hashCode().compareTo(other.from.hashCode())
    //        }
    //    }
}
