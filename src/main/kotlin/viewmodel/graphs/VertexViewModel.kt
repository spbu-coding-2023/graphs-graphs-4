package viewmodel.graphs

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import model.graphs.Vertex

@Suppress("LongParameterList")
class VertexViewModel<V>(
    x: Dp = 0.dp,
    y: Dp = 0.dp,
    internal val value: Vertex<V>,
    private val keyLabelVisibility: State<Boolean>,
    private val distanceLabelVisibility: State<Boolean>,
    val radius: Dp = 25.dp
) {
    var isSelected by mutableStateOf(false)


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
}
