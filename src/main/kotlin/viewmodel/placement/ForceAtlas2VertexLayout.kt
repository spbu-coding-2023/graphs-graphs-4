package viewmodel.placement

import androidx.compose.ui.unit.dp
import viewmodel.graphs.VertexViewModel

data class ForceAtlas2VertexLayout<T>(
    val vertex: VertexViewModel<T>,
    var dltX: Float = 0f,
    var dltY: Float = 0f,
) {
    fun addForces(vararg forces: Pair<Float, Float>) {
        for (force in forces) {
            dltX += force.first
            dltY += force.second
        }
    }

    fun applyForces() {
        vertex.x += dltX.dp
        vertex.y += dltY.dp
    }
}
