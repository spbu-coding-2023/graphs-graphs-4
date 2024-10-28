package viewmodel.placement

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import model.graphs.Edge
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.VertexViewModel
import kotlin.math.log2
import kotlin.math.sqrt

class ForceAtlas2Placement<T, E : Edge<T>>(graphVM: GraphViewModel<T, E>) {
    private val vertices = graphVM.vertices
    private val edges = graphVM.edges
    private val graph = graphVM.graph

    private val center = graphVM.graphSize.value.center

    fun place(
        amount: Int = 1,
        kGrav: Float = 1f,
        kRep: Float = 1f,
    ) {
        for (round in 1..amount) {
            val placement = mutableSetOf<ForceAtlas2VertexLayout<T>>()

            for (u in vertices) {
                val forceAtlas2Vertex = ForceAtlas2VertexLayout(u)

                for (v in vertices) {
                    if (u != v) {
                        val repulseForce = applyRepForce(u, v, kRep)
                        val attractionForce = applyAttForce(u, v)

                        forceAtlas2Vertex.addForces(repulseForce, attractionForce)
                    }
                    println()
                }

                val gravityForce = applyGravForce(u, kGrav)
                forceAtlas2Vertex.addForces(gravityForce)
                placement.add(forceAtlas2Vertex)
            }

            placement.forEach { it.applyForces() }
        }
    }

    private fun findAttForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
        isLinLog: Boolean = true,
        considerOverlapping: Boolean = true
    ): Float {
        if (!graph.areConnected(u.value, v.value)) return 0f

        val distance = findDistance(u, v, considerOverlapping)
        val force = when {
            distance < 0 -> 0f
            isLinLog -> log2(1f + distance)
            else -> distance
        }

        return force
    }

    private fun applyAttForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
        isLinLog: Boolean = true,
        considerOverlapping: Boolean = true
    ): Pair<Float, Float> {
        val force = findAttForce(u, v, isLinLog, considerOverlapping)
        val dest = Pair(v.x, v.y)

        return applyForce(u, dest, force)
    }

    private fun findGravForce(
        v: VertexViewModel<T>,
        kGrav: Float,
    ): Float {
        val force = findVertexMass(v)

        return kGrav * force
    }

    private fun applyGravForce(
        v: VertexViewModel<T>,
        kGrav: Float,
    ): Pair<Float, Float> {
        val force = findGravForce(v, kGrav)
        val xCenter = center.x.dp
        val yCenter = center.y.dp

        return applyForce(v, Pair(xCenter, yCenter), force)
    }

    private fun applyForce(
        v: VertexViewModel<T>,
        dest: Pair<Dp, Dp>,
        force: Float,
        isNegative: Boolean = false,
    ): Pair<Float, Float> {
        val xDest = dest.first
        val yDest = dest.second

        val xDlt = if (isNegative) -(xDest - v.x) * force else (xDest - v.x) * force
        val yDlt = if (isNegative) -(yDest - v.y) * force else (yDest - v.y) * force

        return Pair(xDlt.value, yDlt.value)
    }

    private fun findRepForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
        kRep: Float,
    ): Float {
        val uMass = findVertexMass(u)
        val vMass = findVertexMass(v)
        val distance = findDistance(u, v)

        val force = if (distance > 0f) uMass * vMass / distance else uMass * vMass

        return kRep * force
    }

    private fun applyRepForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
        kRep: Float,
    ): Pair<Float, Float> {
        val force = findRepForce(u, v, kRep)
        val destination = Pair(v.x, v.y)

        return applyForce(u, destination, force, isNegative = true)
    }

    private fun findDistance(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
        considerOverlapping: Boolean = true
    ): Float {
        val xDist = (v.x - u.x).value
        val yDist = (v.y - u.y).value
        val distance = sqrt(xDist * xDist + yDist * yDist)

        return if (considerOverlapping) {
            val uSize = u.radius.value
            val vSize = v.radius.value
            val distanceWithoutOverlapping = distance - uSize - vSize

            distanceWithoutOverlapping  //can be negative!
        } else {
            distance
        }
    }

    private fun findVertexMass(vertex: VertexViewModel<T>): Float {
        var mass = 1f

        for (edge in edges) {
            if (vertex == edge.u) mass++
        }

        return mass
    }
}
