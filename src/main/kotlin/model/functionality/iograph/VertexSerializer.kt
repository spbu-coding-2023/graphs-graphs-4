package model.functionality.iograph

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import model.graphs.Vertex

class VertexSerializer<T> : KSerializer<Vertex<T>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Vertex", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Vertex<T>) {
        when (val key = value.key) {
            is String -> encoder.encodeString(key)
            is Int -> encoder.encodeInt(key)
            is Float -> encoder.encodeFloat(key)
            is Double -> encoder.encodeDouble(key)
            is Long -> encoder.encodeLong(key)
            is Short -> encoder.encodeShort(key)
            is Boolean -> encoder.encodeBoolean(key)
            is Byte -> encoder.encodeByte(key)
            else -> throw SerializationException("Unknown key $key")
        }
    }

    override fun deserialize(decoder: Decoder): Vertex<T> {
        val key = decoder.decodeString()
        return when {
            null != key.toLongOrNull() -> Vertex(key.toLong() as T)
            null != key.toDoubleOrNull() -> Vertex(key.toDouble() as T)
            else -> Vertex(key.toString() as T)
        }
    }

    /*inline fun <reified T> test(decoder: Decoder) : T {
        return when (T::class) {
            String::class -> decoder.decodeString() as T
            Int::class -> decoder.decodeInt() as T
            Float::class -> decoder.decodeFloat() as T
            Double::class -> decoder.decodeDouble() as T
            Long::class -> decoder.decodeLong() as T
            Short::class -> decoder.decodeShort() as T
            Boolean::class -> decoder.decodeBoolean() as T
            Byte::class -> decoder.decodeByte() as T
            else -> throw SerializationException("Unsupported type ${T::class}.")
        }
    }*/
}
