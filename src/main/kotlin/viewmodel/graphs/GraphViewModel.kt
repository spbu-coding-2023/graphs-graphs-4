package viewmodel.graphs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import model.graphs.Edge
import model.graphs.Graph
import model.graphs.Vertex


class GraphViewModel<T, E: Edge<T>>(
    val graph: Graph<T, E>,
    showVerticesLabels: State<Boolean>,
    showVerticesDistanceLabels: State<Boolean>,
) {
    val graphSize = mutableStateOf(IntSize.Zero)

    val onScroll: AwaitPointerEventScope.(event: PointerEvent) -> Unit = {
        val position = it.changes.last().position

        val yDlt = it.changes.first().scrollDelta.y
        vertices.forEach { v -> v.onScroll(yDlt, position) }
        edges.forEach { e -> e.onScroll(yDlt) }
    }

    fun onDrag(offset: Offset) {
        vertices.forEach { v -> v.onDrag(offset) }
    }

    var currentVertex: VertexViewModel<T>? = null
    private var biggestIndexCommunity = 0

    private val _vertices = graph.vertices().associateWith { v ->
        VertexViewModel(0.dp, 0.dp, v, showVerticesLabels, showVerticesDistanceLabels)
    }

    private val _edges = graph.edges().associateWith { e ->
        val fst = _vertices[e.from]
            ?: error("VertexView for ${e.from} not found")
        val snd = _vertices[e.to]
            ?: error("VertexView for ${e.to} not found")

        EdgeViewModel(fst, snd, Color.Black, 4.toFloat())
    }

    // Color(78, 86, 129),
    //        Color(122, 91, 148),
    //        Color(173, 91, 151),
    //        Color(219, 91, 136),
    //        Color(252, 101, 107),
    //        Color(255, 129, 68),

    fun indexCommunities(communities: HashSet<HashSet<Vertex<T>>>) {
        var count = 1

        val biggestCommunities = communities.sortedBy { it.size }.reversed()

        for (community in biggestCommunities) {
            val color = Color((0..255).random(), (0..255).random(), (0..255).random())

            for (vertex in community) {
                _vertices[vertex]?.color = color
            }

            count += 1
        }

        biggestIndexCommunity = biggestCommunities[0].size
    }

    val vertices: Collection<VertexViewModel<T>>
        get() = _vertices.values

    val edges: Collection<EdgeViewModel<T>>
        get() = _edges.values
}
