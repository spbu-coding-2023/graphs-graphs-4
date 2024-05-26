package viewmodel.graphs

import androidx.compose.runtime.State
import model.graphs.GraphEdge

class EdgeViewModel<T>(
	val u: VertexViewModel<T>,
	val v: VertexViewModel<T>,
	private val e: GraphEdge<T>,
	private val _labelVisible: State<Boolean>,
) {
	val label
		get() = e.weight.toString()

	val labelVisible
		get() = _labelVisible.value
}