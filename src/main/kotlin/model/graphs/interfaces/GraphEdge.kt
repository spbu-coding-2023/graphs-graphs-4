package model.graphs.interfaces

import model.graphs.Vertex

interface GraphEdge<T> {
	val from: Vertex<T>
	val to: Vertex<T>
}
