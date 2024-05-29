package viewmodel.graphs

import androidx.compose.ui.graphics.Color
import model.graphs.Vertex
import model.graphs.WeightedEdge

interface RepresentationStrategy {
	fun <T> place(width: Double, height: Double, vertices: Collection<VertexViewModel<T>>)

	fun <T> highlight(vertices: Collection<VertexViewModel<T>>)

	fun <T> colorVertices(vararg vertices: VertexViewModel<T>, color: Color)

	fun <T> colorEdges(vararg edges: EdgeViewModel<T>, color: Color)

	fun highlightSCCStrType(scc: Set<Set<Vertex<String>>>, vertices: Collection<VertexViewModel<String>>?)

	fun <T> highlightBridges(edges: Collection<EdgeViewModel<T>>, bridges: Set<Pair<Vertex<T>, Vertex<T>>>)

	fun <T> highlightSCC(scc: Set<Set<Vertex<T>>>, vararg vertices: VertexViewModel<T>)

	fun <T, W: Comparable<W>> highlightMinSpanTree(minSpanTree: Set<WeightedEdge<T, W>>, vararg edges: EdgeViewModel<T>)
}
