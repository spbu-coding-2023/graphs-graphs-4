package viewmodel.graphs

import model.graphs.Vertex

interface RepresentationStrategy {
	fun <T> place(width: Double, height: Double, vertices: Collection<VertexViewModel<T>>)
	fun <T> highlight(vertices: Collection<VertexViewModel<T>>)
	fun <T> highlightBridges(edges: Collection<EdgeViewModel<T>>, bridges: Set<Pair<Vertex<T>, Vertex<T>>>)
}