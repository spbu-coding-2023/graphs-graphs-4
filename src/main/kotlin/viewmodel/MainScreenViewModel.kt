package viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import model.functionality.iograph.GraphType
import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.*
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy
import viewmodel.placement.ForceAtlas2Placement
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import kotlin.system.exitProcess

class MainScreenViewModel<E: Edge<Int>>(
    var graph: Graph<Int, E>,
    private val representationStrategy: RepresentationStrategy,
    val onGraphCreated: (Graph<Int, *>) -> Unit
) {
    val showVerticesLabels = mutableStateOf(false)
    val showVerticesDistanceLabels = mutableStateOf(false)
    val showEdgesLabels = mutableStateOf(false)
    var graphViewModel = GraphViewModel(graph, showVerticesLabels, showVerticesDistanceLabels)

    @Suppress("MagicNumber")
    private val width = 800.0

    @Suppress("MagicNumber")
    private val height = 600.0

    init {
        representationStrategy.place(width, height, graphViewModel.vertices)
    }

    fun openGraph(type: GraphType) {
        val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
        dialog.isVisible = true

        val fileName = dialog.file
        if (fileName != null) {
            val file = File(dialog.directory, fileName)

            val graph = when (type) {
                GraphType.UNDIRECTED_GRAPH -> ReadWriteIntGraph().readUGraph(file)
                GraphType.DIRECTED_GRAPH -> ReadWriteIntGraph().readDGraph(file)
                GraphType.UNDIRECTED_WEIGHTED_GRAPH -> ReadWriteIntGraph().readUWGraph(file)
                GraphType.DIRECTED_WEIGHTED_GRAPH -> ReadWriteIntGraph().readDWGraph(file)
            }

            onGraphCreated(graph)
        }
    }

    fun saveGraph() {
        var file = File("./graph.json")

        var num = 0
        while (file.exists()) {
            file = File("./${++num}graph.json")
        }

        when (graph) {
            is DirectedGraph -> ReadWriteIntGraph().writeDGraph(file, graph as DirectedGraph)
            is UndirectedGraph -> ReadWriteIntGraph().writeUGraph(file, graph as UndirectedGraph)
            is UndirectedWeightedGraph -> ReadWriteIntGraph().writeUWGraph(file, graph as UndirectedWeightedGraph)
            is DirectedWeightedGraph -> ReadWriteIntGraph().writeDWGraph(file, graph as DirectedWeightedGraph)
        }
    }

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
            val bridges = (graph as GraphUndirected<Int, E>).findBridges()

            representationStrategy.highlightBridges(graphViewModel.edges, bridges)

        } else throw IllegalArgumentException("graph is directed!")
    }

    fun findCommunities(randomness: String, resolution: String) {
        if (graph is GraphUndirected) {
            val communities =
                (graph as GraphUndirected<Int, E>).runLeidenMethod(randomness.toDouble(), resolution.toDouble())
            println(communities)
            graphViewModel.indexCommunities(communities)

        } else throw IllegalArgumentException("leiden method does not support directed graphs")
    }

    fun useForceAtlas2Layout() {
        ForceAtlas2Placement(graphViewModel).place()
    }

    fun findDistanceBellman() {
        if (graph is GraphWeighted) {
            val labels =
                graphViewModel.currentVertex?.let { (graph as GraphWeighted<Int>).findDistancesBellman(it.value) }

            graphViewModel.vertices.forEach {
                it.distanceLabel = (labels?.get(it.value)).toString()
            }
        }
    }
}
