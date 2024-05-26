package model.graphs

interface GraphEdge<K> {
	val from: K
	val to: K
}