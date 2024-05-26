package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graphs.Graph
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy

class MainScreenViewModel<GRAPH_TYPE, T> (
	graph: Graph<GRAPH_TYPE, T>,
	private val representationStrategy: RepresentationStrategy)
{
	val showVerticesLabels = mutableStateOf(false)
	val showEdgesLabels = mutableStateOf(false)
	val graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels)

	init {
		representationStrategy.place(800.0, 600.0, graphViewModel.vertices)
	}

	fun resetGraphView() {
		representationStrategy.place(800.0, 600.0, graphViewModel.vertices)
		graphViewModel.vertices.forEach { v -> v.color = Color.Gray }
	}

	fun setVerticesColor() {
		representationStrategy.highlight(graphViewModel.vertices)
	}
}