package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.iograph.VertexSerializer

@Serializable(with = VertexSerializer::class)
data class Vertex<T>(val key: T) {
    override fun equals(other: Any?): Boolean {
        return when(other) {
            is Vertex<*> -> key == other.key
            else -> false
        }
    }

    override fun toString(): String {
        return "Vertex($key)"
    }
}
