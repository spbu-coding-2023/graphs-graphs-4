package viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graphs.Graph
import model.graphs.GraphUndirected
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy
import java.io.File
import kotlin.system.exitProcess

class MainScreenViewModel<T>(
    var graph: Graph<T>,
    private val representationStrategy: RepresentationStrategy
) {
    private val showVerticesLabels = mutableStateOf(false)
    private val showVerticesDistanceLabels = mutableStateOf(false)
    private val showEdgesLabels = mutableStateOf(false)
    var graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels, showVerticesDistanceLabels)
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
        graphViewModel.edges.forEach {
            it.color = Color.Black
            it.width = 3.toFloat()
        }
    }

    fun setVerticesColor() {
        representationStrategy.highlight(graphViewModel.vertices)
    }

//    fun openFile() {
//        val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
//        dialog.isVisible = true
//        if (dialog.file != null) {
//            file = File("${dialog.directory}${dialog.file}")
//            val graphType = ReadWriteGraph().findType(file!!) ?: return
//            graph = ReadWriteGraph().read(file!!)
//            graphViewModel = GraphViewModel(graph, showVerticesLabels, showEdgesLabels, showVerticesDistanceLabels)
//        }
//
//    }

//    fun highlightSCC() {
//        val scc = graph.findSCC()
//        representationStrategy.highlightSCC(scc, *graphViewModel.vertices.toTypedArray())
//    }

//    fun highlightMinSpanTree() {
//        val minSpanTree = graph.findMinSpanTree()
//        if (minSpanTree == null) {
//            return
//        } else {
//            representationStrategy.highlightMinSpanTree(minSpanTree, *graphViewModel.edges.toTypedArray())
//        }
//    }

    fun closeApp() {
        exitProcess(0)
    }

    @Composable
    fun showBridges() {
        if (graph is GraphUndirected) {
            val bridges = (graph as GraphUndirected<T>).findBridges()

            representationStrategy.highlightBridges(graphViewModel.edges, bridges)

        } else throw IllegalArgumentException("graph is directed!")
    }

//    private fun colorNotSelected(currV: Vertex<T>) {
//        graphViewModel.vertices.forEach { v ->
//            if (v.v != currV) {
//                v.color = Color.DarkGray
//            }
//        }
//    }

}
