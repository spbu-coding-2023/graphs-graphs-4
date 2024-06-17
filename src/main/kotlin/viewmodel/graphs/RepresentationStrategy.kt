package viewmodel.graphs

import androidx.compose.ui.graphics.Color
import model.graphs.Edge
import model.graphs.Vertex

interface RepresentationStrategy {
    fun <T> place(width: Double, height: Double, vertices: Collection<VertexViewModel<T>>)
    fun <T> highlight(vertices: Collection<VertexViewModel<T>>)
    fun <T> highlightBridges(edges: Collection<EdgeViewModel<T>>, bridges: Set<Pair<Vertex<T>, Vertex<T>>>)
    fun <T> highlightSCC(scc: Set<Set<Vertex<T>>>, vararg vertices: VertexViewModel<T>)
    fun <T> highlightMinSpanTree(minSpanTree: Set<Edge<T>>, vararg edges: EdgeViewModel<T>)
    fun <T> colorVertices(vararg vertices: VertexViewModel<T>, color: Color)
    fun <T> colorEdges(vararg edges: EdgeViewModel<T>, color: Color)
}
