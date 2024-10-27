package viewmodel.graphs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.math.max

class EdgeViewModel<T>(
    val u: VertexViewModel<T>,
    val v: VertexViewModel<T>,
    color: Color,
    width: Float = 5f,
) {
    private var _width = mutableStateOf(width)
    var width: Float
        get() = _width.value
        set(value) {
            _width.value = max(2f, value)
        }

    private var _color = mutableStateOf(color)
    var color: Color
        get() = _color.value
        set(value) {
            _color.value = value
        }

    fun onScroll(scale: Dp) {
        width += scale.value
    }
}
