package viewmodel.placement

import viewmodel.graphs.VertexViewModel

data class ForceAtlas2VertexLayout<T>(
    val vertex: VertexViewModel<T>,
    var dltX: Float = 0f,
    var dltY: Float = 0f,
) {
    fun addForces(xF: Float, yF: Float) {
        dltX += xF
        dltY += yF
    }
}
