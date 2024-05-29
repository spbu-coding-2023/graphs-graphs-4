package model.graphs

class Vertex<T>(val key: T) {
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
