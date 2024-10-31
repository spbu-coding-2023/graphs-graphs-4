package model.functionality.iograph

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import model.graphs.Vertex

class VertexSerializer<T> : KSerializer<Vertex<T>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Vertex", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Vertex<T>) {
        encoder.encodeString(value.key.toString())
    }

    override fun deserialize(decoder: Decoder): Vertex<T> {
        val key = decoder.decodeString()
        return Vertex(key.toInt() as T)
    }
}
