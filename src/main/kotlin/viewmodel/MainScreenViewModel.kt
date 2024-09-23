package viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import model.graphs.*
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy
import java.io.File
import kotlin.system.exitProcess

class MainScreenViewModel<T, E: Edge<T>>(
    var graph: Graph<T, E>,
    private val representationStrategy: RepresentationStrategy
) {
    val showVerticesLabels = mutableStateOf(false)
    val showVerticesDistanceLabels = mutableStateOf(false)
    val showEdgesLabels = mutableStateOf(false)
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

    fun highlightSCC() {
        if (graph is GraphDirected) {
            val scc = (graph as GraphDirected).findSCC()
            representationStrategy.highlightSCC(scc, *graphViewModel.vertices.toTypedArray())
        }
    }

    fun highlightMinSpanTree() {
        if (graph is GraphUndirected) {
            val minSpanTree = (graph as GraphUndirected).findMinSpanTree()
            if (minSpanTree == null) {
                return
            } else {
                representationStrategy.highlightMinSpanTree(minSpanTree, *graphViewModel.edges.toTypedArray())
            }
        }
    }

    fun closeApp() {
        exitProcess(0)
    }

    @Composable
    fun showBridges() {
        if (graph is GraphUndirected) {
            val bridges = (graph as GraphUndirected<T, E>).findBridges()

            representationStrategy.highlightBridges(graphViewModel.edges, bridges)

        } else throw IllegalArgumentException("graph is directed!")
    }

    fun findCommunities() {
        if (graph is GraphUndirected) {
            val communities = (graph as GraphUndirected<T, E>).runLeidenMethod()
            println(communities)
            graphViewModel.indexCommunities(communities)

        } else throw IllegalArgumentException("leiden method does not support directed graphs")
    }

    fun findDistanceBellman() {
        if (graph is GraphWeighted) {
            val labels =
                graphViewModel.currentVertex?.let { (graph as GraphWeighted<T>).findDistancesBellman(it.value) }

            graphViewModel.vertices.forEach {
                it.distanceLabel = (labels?.get(it.value)).toString()
            }
        }
    }

}
