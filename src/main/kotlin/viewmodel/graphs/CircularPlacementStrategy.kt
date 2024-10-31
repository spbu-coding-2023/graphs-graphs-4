package viewmodel.graphs

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.graphs.Edge
import model.graphs.Vertex
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CircularPlacementStrategy : RepresentationStrategy {
    override fun <T> place(width: Double, height: Double, vertices: Collection<VertexViewModel<T>>) {
        if (vertices.isEmpty()) {
            println("viewmodel.graphs.CircularPlacementStrategy.place: there is nothing to place 👐🏻")
            return
        }

        val center = Pair(width / 2, height / 2)
        val angle = 2 * Math.PI / vertices.size
        val sorted = vertices.sortedBy { it.label }
        val first = sorted.first()
        var point = Pair(center.first, center.second - min(width, height) / 2)

        first.x = point.first.dp
        first.y = point.second.dp

        sorted
            .drop(1)
            .onEach {
                point = point.rotate(center, angle)
                it.x = point.first.dp
                it.y = point.second.dp
            }
    }

    override fun <T> highlight(vertices: Collection<VertexViewModel<T>>) {
        TODO("Not yet implemented")
    }

    override fun <T> highlightBridges(edges: Collection<EdgeViewModel<T>>, bridges: Set<Edge<T>>) {
        for (bridge in bridges) {
            val toColor = edges.find { ((it.v.value == bridge.to) && (it.u.value == bridge.from)) }
            val toColorSecond = edges.find { ((it.u.value == bridge.to) && (it.v.value == bridge.from)) }

            if (toColor != null) {
                //earlier there was MaterialTheme.colors.secondaryVariant
                //but then this class needs to be Composable
                //I think to myself that it is strange
                //because of that, I replace it with this color
                val color = Color(134, 182, 246)

                toColor.color = color
                toColor.width = 10f

                toColorSecond?.color = color
                toColorSecond?.width = 10f
            } else throw NoSuchElementException("WE LOST AN EDGE!!!")
        }
    }

    override fun <T> colorEdges(vararg edges: EdgeViewModel<T>, color: Color) {
        for (edge in edges) {
            edge.color = color
        }
    }

    override fun <T> distanceRank(vertices: Collection<VertexViewModel<T>>, max: Double, min: Double) {
        for(vertex in vertices){
            val vImp = vertex.importance
            if(vImp <= ( min + ( (max - min) / 4) ) ) vertex.color = Color(0xFFFF0000) // КРАСНЫЙ / ROT
            else if(vImp <= ( min + 2 * ( (max - min) / 4) ) ) vertex.color = Color(0xFF0000FF) // СИНИЙ / BLAU
            else if(vImp <= ( min + 3 * ( (max - min) / 4) ) ) vertex.color = Color(0xFF800080) //ФИОЛЕТОВЫЙ
            else vertex.color = Color(0xFF00FF00) // ЗЕЛЁНЫЙ / GRÜN
        }
    }

    override fun <T> findCycles(vertices: Collection<VertexViewModel<T>>, cycle: List<Vertex<Int>>, color: Color) {
        for(vertex in cycle) {
            val ver = vertices.find { it.value == vertex }
            ver?.color = color
        }
    }

    override fun <T> highlightSCC(scc: Set<Set<Vertex<T>>>, vararg vertices: VertexViewModel<T>) {
        for (component in scc) {
            println(component)
            val array = Array(256) { it }
            val color = Color(array.random(), array.random(), array.random())

            for (vertex in vertices) {
                if (vertex.value in component) vertex.color = color
            }
        }
    }

    override fun <T> highlightMinSpanTree(minSpanTree: Set<Edge<T>>, vararg edges: EdgeViewModel<T>) {
        val color = Color.Blue
        for (edge in minSpanTree) {
            val u1 = edge.from
            val v1 = edge.to

            for (edgeVM in edges) {
                val u2 = edgeVM.u.value
                val v2 = edgeVM.v.value

                if ((u1 == u2) && (v1 == v2) || ((u1 == v2) && (v1 == u2))) {
                    edgeVM.color = color
                    edgeVM.width = 6f
                }
            }
        }
    }

    override fun <T> colorVertices(vararg vertices: VertexViewModel<T>, color: Color) {
        for(vertex in vertices){
            vertex.color = color
        }
    }

    private fun Pair<Double, Double>.rotate(pivot: Pair<Double, Double>, angle: Double): Pair<Double, Double> {
        val sin = sin(angle)
        val cos = cos(angle)

        val diff = first - pivot.first to second - pivot.second
        val rotated = Pair(
            diff.first * cos - diff.second * sin,
            diff.first * sin + diff.second * cos,
        )
        return rotated.first + pivot.first to rotated.second + pivot.second
    }
}
