package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.functionality.iograph.JsonGraphParser
import model.graphs.interfaces.Graph
import model.graphs.interfaces.GraphEdge
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

class MainScreenViewModel<E: GraphEdge<T>, T>(
	var graph: Graph<E, T>,
	private val representationStrategy: RepresentationStrategy
) {
	private val showVerticesLabels = mutableStateOf(false)
	private val showEdgesLabels = mutableStateOf(false)
	var graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels)
	var file: File? = null

	init {
		representationStrategy.place(800.0, 600.0, graphViewModel.vertices)
	}

	fun resetGraphView() {
		representationStrategy.place(800.0, 600.0, graphViewModel.vertices)
		graphViewModel.vertices.forEach { v -> v.color = Color.Gray }
		graphViewModel.edges.forEach {
			it.color = Color.Black
			it.width = 3.toFloat()
		}
	}

	fun setVerticesColor() {
		representationStrategy.highlight(graphViewModel.vertices)
	}

	fun openFile() {
		val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
		dialog.isVisible = true
		if (dialog.file != null) {
			file = File("${dialog.directory}${dialog.file}")
			val graphType = JsonGraphParser().findType(file!!) ?: return
			graph = JsonGraphParser().read(file!!)
			graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels)
		}

	}

	fun highlightSCC() {
		/*val scc = graph.findSCC()
		representationStrategy.highlightSCC(scc, *graphViewModel.vertices.toTypedArray())*/
	}

	fun highlightMinSpanTree() {
		/*val minSpanTree = graph.findMinSpanTree()
		if (minSpanTree == null) {
			return
		} else {
			representationStrategy.highlightMinSpanTree(minSpanTree, *graphViewModel.edges.toTypedArray())
		}*/
	}

	fun highlightBridges() {
		/*val bridges = graph.findBridges()

		representationStrategy.highlightBridges(graphViewModel.edges, bridges)*/
	}
}
