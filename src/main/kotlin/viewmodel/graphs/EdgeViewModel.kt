package viewmodel.graphs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graphs.interfaces.GraphEdge

class EdgeViewModel<T>(
	val u: VertexViewModel<T>,
	val v: VertexViewModel<T>,
	color: Color,
	width: Float,
	private val e: GraphEdge<T>,
) {
	private var _width = mutableStateOf(width)
	var width: Float
		get() = _width.value
		set(value) {
			_width.value = value
		}


	private var _color = mutableStateOf(color)
	var color: Color
		get() = _color.value
		set(value) {
			_color.value = value
		}
}
