package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.functionality.iograph.ReadWriteGraph
import model.graphs.Graph
import model.graphs.Vertex
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

class MainScreenViewModel<GRAPH_TYPE, T>(
	val graph: Graph<GRAPH_TYPE, T>,
	private val representationStrategy: RepresentationStrategy
) {
	internal val showVerticesLabels = mutableStateOf(false)
	internal val showVerticesDistanceLabels = mutableStateOf(false)
	internal val showEdgesLabels = mutableStateOf(false)
	val graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels, showVerticesDistanceLabels)
    var file: File? = null

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

	fun setVerticesColor() {
		representationStrategy.highlight(graphViewModel.vertices)
	}

	fun openFile() {
		val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
		dialog.isVisible = true
		if (dialog.file != null) {
			file = File("${dialog.directory}${dialog.file}")
			val graphType = ReadWriteGraph().findType(file!!) ?: return
			graph = ReadWriteGraph().read(file!!)
			graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels)
		}

	}

	fun highlightSCC() {
		val scc = graph.findSCC()
		representationStrategy.highlightSCC(scc, *graphViewModel.vertices.toTypedArray())
	}

	fun highlightMinSpanTree() {
		val minSpanTree = graph.findMinSpanTree()
		if (minSpanTree == null) {
			return
		} else {
			representationStrategy.highlightMinSpanTree(minSpanTree, *graphViewModel.edges.toTypedArray())
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
