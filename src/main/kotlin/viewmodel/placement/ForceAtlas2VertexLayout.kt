package viewmodel.placement

import viewmodel.graphs.VertexViewModel

data class ForceAtlas2VertexLayout<T>(
    val vertex: VertexViewModel<T>,
    var dltX: Double = 0.0,
    var dltY: Double = 0.0,
) {
    fun addForces(Fx: Double, Fy: Double) {
        dltX += Fx
        dltY += Fy
    }
}
