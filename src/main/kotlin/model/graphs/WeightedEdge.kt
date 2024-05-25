package model.graphs

data class WeightedEdge<K, W: Number>(
	override val from: K,
	override val to: K,
	val weight: W,
	) : Comparable<WeightedEdge<K, W>>, Edge<K>(from, to) {
	operator fun Number.minus(other: Number): Number {
		return when (this) {
			is Long -> this + other.toLong()
			is Int -> this + other.toLong()
			is Short -> this + other.toLong()
			is Double -> this + other.toDouble()
			is Float -> this + other.toDouble()
			else -> throw IllegalArgumentException("Unknown numeric type")
		}
	}

	override fun compareTo(other: WeightedEdge<K, W>): Int {
		return (weight - other.weight).toInt()
	}
}
