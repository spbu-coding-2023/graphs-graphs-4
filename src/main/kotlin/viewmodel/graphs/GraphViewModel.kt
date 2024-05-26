package viewmodel.graphs

import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.graphs.Graph

class GraphViewModel<GRAPH_TYPE, T>(
	private val graph: Graph<GRAPH_TYPE, T>,
	showVerticesLabels: State<Boolean>,
	showEdgesLabels: State<Boolean>,
) {
	private val _vertices = graph.adjList.keys.associateWith { v ->
		VertexViewModel(0.dp, 0.dp, Color.Gray, v, showVerticesLabels)
	}

//	private val _edges = graph.adjList.values.associateWith { e ->
//		val fst = _vertices[e.vertices.first]
//			?: throw IllegalStateException("VertexView for ${e.vertices.first} not found")
//		val snd = _vertices[e.vertices.second]
//			?: throw IllegalStateException("VertexView for ${e.vertices.second} not found")
//		EdgeViewModel(fst, snd, e, showEdgesLabels)
//	}

	val vertices: Collection<VertexViewModel<T>>
		get() = _vertices.values

//	val edges: Collection<EdgeViewModel<E, V>>
//		get() = _edges.values
}