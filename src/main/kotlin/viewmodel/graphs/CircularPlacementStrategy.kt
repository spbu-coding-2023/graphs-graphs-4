package viewmodel.graphs

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.graphs.Vertex
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

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
		first.color = Color.Gray

		sorted
			.drop(1)
			.onEach {
				point = point.rotate(center, angle)
				it.x = point.first.dp
				it.y = point.second.dp
			}
	}

	override fun <V> highlight(vertices: Collection<VertexViewModel<V>>) {
		vertices
			.onEach {
				it.color = if (Random.nextBoolean()) Color.Green else Color.Blue
			}
	}

	override fun <T> highlightBridges(edges: Collection<EdgeViewModel<T>>, bridges: Set<Pair<Vertex<T>, Vertex<T>>>) {
		for (edge in edges) {
			if (bridges.contains(Pair(edge.u.v, edge.v.v)) || bridges.contains(Pair(edge.v.v, edge.u.v))) {
				edge.color = Color.Red
				edge.width = 6.toFloat()
			}
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
