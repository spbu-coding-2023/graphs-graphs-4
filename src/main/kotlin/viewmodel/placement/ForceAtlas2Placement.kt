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
                    val repulseForce = findRepForce(u, v)
                    val attractionForce = if (edges.any { it.u == u && it.v == v }) findAttForce(u, v) else 0.0
                    val force = attractionForce - repulseForce
//                  val xDist = (u.x - v.x).value.toDouble()
//                  val yDist = (u.y - v.y).value.toDouble()
//                  val dist = sqrt(xDist * xDist + yDist * yDist)
                    val forceXProj = force //* xDist
                    val forceYProj = force //* yDist

                    forceAtlas2Vertex.addForces(forceXProj, forceYProj)
                    println()
                }

                placement.add(forceAtlas2Vertex)
            }

            println("Now we are changing graph layout")
            for (forceAtlas2Vertex in placement) {
                val vertex = forceAtlas2Vertex.vertex
                val dltX = forceAtlas2Vertex.dltX
                val dltY = forceAtlas2Vertex.dltY

                vertex.y += dltX.dp
                vertex.x += dltY.dp
            }
        }
    }

    private fun findAttForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>
    ): Double {
        val force = findDistance(u, v)
        print("att: $force ")

        return force
    }

    private fun applyAttForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
    ) {
        val force = findAttForce(u, v).dp
        val distance = findDistance(u, v).dp
        val dest = Pair(v.x, v.y)

        applyForce(u, distance, dest, force)
    }

    private fun findGravForce(
        v: VertexViewModel<T>
    ): Dp {
        val force = findVertexMass(v).dp

        return force
    }

    private fun applyGravForce(
        v: VertexViewModel<T>,
    ) {
        val force = findGravForce(v)
        val xCenter = center.first.dp
        val yCenter = center.second.dp

        val xDist = (v.x - xCenter).value
        val yDist = (v.y - yCenter).value
        val distance = sqrt(xDist * xDist + yDist * yDist).dp

        applyForce(v, distance, Pair(xCenter, yCenter), force)
    }

    private fun applyForce(
        v: VertexViewModel<T>,
        dist: Dp,
        dest: Pair<Dp, Dp>,
        force: Dp,
        isNegative: Boolean = false,
    ) {
        val coef = if (isNegative) (dist + force) / dist else (dist - force) / dist
        val xDest = dest.first
        val yDest = dest.second

        v.x = if (v.x > xDest) xDest + v.x * coef else xDest - v.x * coef
        v.y = if (v.y > yDest) yDest + v.y * coef else yDest - v.y * coef
    }

    private fun findRepForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>
    ): Double {
        val uMass = findVertexMass(u)
        val vMass = findVertexMass(v)
        val distance = findDistance(u, v)

        val force = when {
            distance > 0.0 -> uMass * vMass / distance
            distance < 0.0 -> uMass * vMass
            else -> 0.0
        }
        print("rep: $force ")

        return force
    }

    private fun applyRepForce(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
    ) {
        val force = findRepForce(u, v).dp
        val distance = findDistance(u, v).dp
        val destination = Pair(v.x, v.y)

        applyForce(u, distance, destination, force, isNegative = true)
    }

    private fun findDistance(
        u: VertexViewModel<T>,
        v: VertexViewModel<T>,
        considerOverlapping: Boolean = false
    ): Double {
        val xDist = (v.x - u.x).value.toDouble()
        val yDist = (v.y - u.y).value.toDouble()
        val distance = sqrt(xDist * xDist + yDist * yDist)

        return if (considerOverlapping) {
            val uSize = u.radius.value.toDouble()
            val vSize = v.radius.value.toDouble()
            val distanceWithoutOverlapping = distance - uSize - vSize

            distanceWithoutOverlapping
        } else {
            distance
        }
    }

    private fun findVertexMass(vertex: VertexViewModel<T>): Double {
        var mass = 1.0

        for (edge in edges) {
            if (vertex == edge.u) mass++
        }
        print("mass: $mass ")

        return mass
    }
}
