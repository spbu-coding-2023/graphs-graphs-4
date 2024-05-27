package viewmodel.graphs

import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.graphs.Graph

class GraphViewModel<GRAPH_TYPE, T>(
	graph: Graph<GRAPH_TYPE, T>,
	showVerticesLabels: State<Boolean>,
	showEdgesLabels: State<Boolean>,
) {
	private val _vertices = graph.vertices().associateWith { v ->
		VertexViewModel(0.dp, 0.dp, Color.DarkGray, v, showVerticesLabels)
	}

	private val _edges = graph.edges().associateWith { e ->
		val fst = _vertices[e.from]
			?: throw IllegalStateException("VertexView for ${e.from} not found")
		val snd = _vertices[e.to]
			?: throw IllegalStateException("VertexView for ${e.to} not found")

		EdgeViewModel(fst, snd, Color.Black, 3.toFloat(), e, showEdgesLabels)
	}

	val vertices: Collection<VertexViewModel<T>>
		get() = _vertices.values

	val edges: Collection<EdgeViewModel<T>>
		get() = _edges.values
}