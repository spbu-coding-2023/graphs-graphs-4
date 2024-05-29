package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graphs.Graph
import model.graphs.Vertex
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy

class MainScreenViewModel<GRAPH_TYPE, T>(
	val graph: Graph<GRAPH_TYPE, T>,
	private val representationStrategy: RepresentationStrategy
) {
	internal val showVerticesLabels = mutableStateOf(false)
	internal val showVerticesDistanceLabels = mutableStateOf(false)
	internal val showEdgesLabels = mutableStateOf(false)
	val graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels, showVerticesDistanceLabels)

	@Suppress("MagicNumber")
	private val width = 800.0

	@Suppress("MagicNumber")
	private val height = 600.0

	init {
		representationStrategy.place(width, height, graphViewModel.vertices)
	}

	fun resetGraphView() {
		representationStrategy.place(width, height, graphViewModel.vertices)
		graphViewModel.vertices.forEach { v -> v.color = Color.DarkGray }
		graphViewModel.edges.forEach {
			it.color = Color.Black
			it.width = 3.toFloat()
		}
	}

	fun highlightBridges() {
		val bridges = graph.findBridges()

		representationStrategy.highlightBridges(graphViewModel.edges, bridges)
	}

	private fun colorNotSelected(currV: Vertex<T>) {
		graphViewModel.vertices.forEach { v ->
			if (v.v != currV) {
				v.color = Color.DarkGray
			}
		}
	}

	fun findDistanceBellman(startVertex: Vertex<T>?) {
		if (startVertex != null) {
			colorNotSelected(startVertex)

			val labels = graph.findDistancesBellman(startVertex)

			graphViewModel.vertices.forEach {
				it.distanceLabel = (labels[it.v]).toString()
			}
		}
	}

}
