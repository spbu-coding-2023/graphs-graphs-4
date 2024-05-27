package model.graphs

import kotlinx.serialization.Serializable
import model.functionality.iograph.VertexSerializer

@Serializable(with = VertexSerializer::class)
data class Vertex<T>(val key: T) {
    override fun hashCode(): Int {
        return key.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Vertex<*> && other.key == key
    }

    override fun toString(): String {
        return "Vertex($key)"
    }
}
