package viewmodel.graphs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

private const val DEFAULT_WIDTH = 6f

class EdgeViewModel<T>(
    val u: VertexViewModel<T>,
    val v: VertexViewModel<T>,
    color: Color,
    width: Float = DEFAULT_WIDTH,
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

    fun onScroll(scale: Float = 1f) {
        width = DEFAULT_WIDTH * scale
    }
}
