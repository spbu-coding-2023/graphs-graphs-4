package viewmodel.placement

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import model.graphs.Edge
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.VertexViewModel
import kotlin.math.sqrt

class ForceAtlas2Placement<T, E : Edge<T>>(graph: GraphViewModel<T, E>, width: Float = 800f, height: Float = 600f) {
    private val vertices = graph.vertices
    private val edges = graph.edges

    private val center = Pair(width / 2, height / 2)

    fun place(
        amount: Int) {
        for (round in 1..amount) {
            val placement = mutableSetOf<ForceAtlas2VertexLayout<T>>()

            for (u in vertices) {
                val forceAtlas2Vertex = ForceAtlas2VertexLayout(u)

                for (v in vertices) {
                    val repulseForce = applyRepForce(u, v)
                    val attractionForce =
                        if (edges.any { it.u == u && it.v == v }) applyAttForce(u, v) else Pair(0f, 0f)
                    val gravityForce = applyGravForce(u)

                    forceAtlas2Vertex.addForces(repulseForce, attractionForce)
                    println()
                }

                forceAtlas2Vertex.addForces(applyGravForce(u))
                placement.add(forceAtlas2Vertex)
            }

            println("Now we are changing graph layout")
            placement.forEach { it.applyForces() }
        }
    }

    private fun findAttForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>
    ): Float {
        val force = findDistance(u, v)
        print("att: $force ")

        return force
    }

    private fun applyAttForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
    ): Pair<Float, Float> {
        val force = findAttForce(u, v)
        val dest = Pair(v.x, v.y)

        return applyForce(u, dest, force)
    }

    private fun findGravForce(
        v: VertexViewModel<T>
    ): Float {
        val force = findVertexMass(v)

        return force
    }

    private fun applyGravForce(
        v: VertexViewModel<T>,
    ): Pair<Float, Float> {
        val force = findGravForce(v)
        val xCenter = center.first.dp
        val yCenter = center.second.dp

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

        val xDlt = if (isNegative) if (v.x > xDest) (v.x - xDest) * force else -(v.x - xDest) * force
        else if (v.x > xDest) -(v.x - xDest) * force else (v.x - xDest) * force
        val yDlt = if (isNegative) if (v.y > yDest) v.y + (v.y - xDest) * force else v.y - (v.y - xDest) * force
        else if (v.y > yDest) v.y - (v.y - xDest) * force else v.y + (v.y - xDest) * force

        return Pair(xDlt.value, yDlt.value)
    }

    private fun findRepForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>
    ): Float {
        val uMass = findVertexMass(u)
        val vMass = findVertexMass(v)
        val distance = findDistance(u, v)

        val force = when {
            distance > 0f -> uMass * vMass / distance
            distance < 0f -> uMass * vMass
            else -> 0f
        }
        print("rep: $force ")

        return force
    }

    private fun applyRepForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
    ): Pair<Float, Float> {
        val force = findRepForce(u, v)
        val destination = Pair(v.x, v.y)

        return applyForce(u, destination, force, isNegative = true)
    }

    private fun findDistance(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
        considerOverlapping: Boolean = false
    ): Float {
        val xDist = (v.x - u.x).value
        val yDist = (v.y - u.y).value
        val distance = sqrt(xDist * xDist + yDist * yDist)

        return if (considerOverlapping) {
            val uSize = u.radius.value
            val vSize = v.radius.value
            val distanceWithoutOverlapping = distance - uSize - vSize

            distanceWithoutOverlapping
        } else {
            distance
        }
    }

    private fun findVertexMass(vertex: VertexViewModel<T>): Float {
        var mass = 1f

        for (edge in edges) {
            if (vertex == edge.u) mass++
        }
        print("mass: $mass ")

        return mass
    }
}
