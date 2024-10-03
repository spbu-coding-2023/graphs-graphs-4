package viewmodel.graphs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class EdgeViewModel<T>(
    val u: VertexViewModel<T>,
    val v: VertexViewModel<T>,
    color: Color,
    width: Float,
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
