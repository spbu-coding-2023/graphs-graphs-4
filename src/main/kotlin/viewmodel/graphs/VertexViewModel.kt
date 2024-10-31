package viewmodel.graphs

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import model.graphs.Vertex

private const val DEFAULT_RADIUS = 24f

@Suppress("LongParameterList")
class VertexViewModel<V>(
    x: Dp = 0.dp,
    y: Dp = 0.dp,
    internal val value: Vertex<V>,
    private val keyLabelVisibility: State<Boolean>,
    private val distanceLabelVisibility: State<Boolean>,
    radius: Dp = DEFAULT_RADIUS.dp
) {
    var isSelected by mutableStateOf(false)
    var color by mutableStateOf(Color.Unspecified)

    private var _radius = mutableStateOf(radius)
    var radius: Dp
        get() = _radius.value
        set(value) {
            _radius.value = value
        }

    private var _x = mutableStateOf(x)
    var x: Dp
        get() = _x.value
        set(value) {
            _x.value = value
        }

    private var _y = mutableStateOf(y)
    var y: Dp
        get() = _y.value
        set(value) {
            _y.value = value
        }

    val label
        get() = value.key.toString()

    val isKeyLabelVisible
        get() = keyLabelVisibility.value

    var distanceLabel: String = ""

    val isDistLabelVisible
        get() = distanceLabelVisibility.value

    fun onDrag(offset: Offset) {
        _x.value += offset.x.dp
        _y.value += offset.y.dp
    }

    //есть некоторая проблема с тем, что Dlt расстояния
    //обладает степенной зависимостью от дистанции
    //между вершиной и центром,
    //в то время как radius вершин
    //растёт пропорционально относительно scale.
    //короче говоря, это выглядит не эстетично :(
    fun onScroll(scaleDlt: Float, center: Offset, scale: Float = 1f) {
        radius = DEFAULT_RADIUS.dp * scale
        x += (center.x.dp - x) * scaleDlt
        y += (center.y.dp - y) * scaleDlt
    }
}
