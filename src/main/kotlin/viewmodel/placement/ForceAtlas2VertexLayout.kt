package viewmodel.placement

import viewmodel.graphs.VertexViewModel

data class ForceAtlas2VertexLayout<T>(
    val vertex: VertexViewModel<T>,
    var dltX: Double = 0.0,
    var dltY: Double = 0.0,
) {
    fun addForces(xF: Double, yF: Double) {
        dltX += xF
        dltY += yF
    }
}
