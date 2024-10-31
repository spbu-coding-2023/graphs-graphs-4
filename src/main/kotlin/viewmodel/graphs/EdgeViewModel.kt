package viewmodel.graphs

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class EdgeSize(val size: Float) {
    MIN(VertexSize.MIN.size / 4.dp),        //0.25
    START(VertexSize.START.size / 4.dp),    //6
    MAX(VertexSize.MAX.size / 4.dp),        //24
    WIDTH_SCALE(VertexSize.SIZE_SCALE.size.value)
}

class EdgeViewModel<T>(
    val u: VertexViewModel<T>,
    val v: VertexViewModel<T>,
    color: Color,
    width: Float = EdgeSize.START.size,
) {
    private var _width = mutableStateOf(width)
    var width: Float
        get() = _width.value
        set(value) {
            if (value in EdgeSize.MIN.size..EdgeSize.MAX.size) _width.value = value
        }

    private var _color = mutableStateOf(color)
    var color: Color
        get() = _color.value
        set(value) {
            _color.value = value
        }

    fun onScroll(scale: Float = 1f) {
        width = EdgeSize.START.size * scale
    }
}
