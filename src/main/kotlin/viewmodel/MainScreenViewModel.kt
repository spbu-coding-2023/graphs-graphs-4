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
	private val showVerticesLabels = mutableStateOf(false)
	private val showEdgesLabels = mutableStateOf(false)
	val graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels)

	init {
		representationStrategy.place(800.0, 600.0, graphViewModel.vertices)
	}

	fun resetGraphView() {
		representationStrategy.place(800.0, 600.0, graphViewModel.vertices)
		graphViewModel.vertices.forEach { v -> v.color = Color.DarkGray }
		graphViewModel.edges.forEach {
			it.color = Color.Black
			it.width = 3.toFloat()
		}
	}

	fun setVerticesColor() {
		representationStrategy.highlight(graphViewModel.vertices)
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

	fun displayDistanceBellman(startVertex: Vertex<T>?) {
		if (startVertex != null) {
			colorNotSelected(startVertex)

			val labels = graph.findDistancesBellman(startVertex)
		}
	}
}