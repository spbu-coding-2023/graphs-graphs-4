package model.graphs

data class WeightedEdge<T>(val vertex: Vertex<T>, val weight: Long) : Comparable<WeightedEdge<T>> {
	override fun compareTo(other: WeightedEdge<T>): Int {
		return (this.weight - other.weight).toInt()
	}
}
