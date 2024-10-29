package viewmodel.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.functionality.iograph.GraphType
import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.DirectedGraph
import model.graphs.DirectedWeightedGraph
import model.graphs.Edge
import model.graphs.Graph
import model.graphs.GraphDirected
import model.graphs.GraphUndirected
import model.graphs.GraphWeighted
import model.graphs.UndirectedGraph
import model.graphs.UndirectedWeightedGraph
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy
import viewmodel.placement.ForceAtlas2Placement
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import kotlin.system.exitProcess

const val WIDTH = 800.0
const val HEIGHT = 600.0

class MainScreenViewModel<E: Edge<Int>>(
    var graph: Graph<Int, E>,
    private val representationStrategy: RepresentationStrategy,
    private val currentGraph: MutableState<Graph<Int, *>?>,
    private val darkTheme: MutableState<Boolean>
) {
    val showVerticesLabels = mutableStateOf(false)
    val showVerticesDistanceLabels = mutableStateOf(false)
    val showEdgesLabels = mutableStateOf(false)
    var showDropdownMenu = mutableStateOf(false)
    var showChooseGraphTypeDialog = mutableStateOf(false)
    var graphViewModel = GraphViewModel(graph, showVerticesLabels, showVerticesDistanceLabels)

    init {
        representationStrategy.place(WIDTH, HEIGHT, graphViewModel.vertices)
    }

    fun showMenu() {
        showDropdownMenu.value = true
    }

    fun showChooseDialog() {
        showChooseGraphTypeDialog.value = true
    }

    fun closeMenu() {
        showDropdownMenu.value = false
    }

    fun closeChooseGraphTypeDialog() {
        showChooseGraphTypeDialog.value = false
    }

    fun changeTheme() {
        darkTheme.value = !darkTheme.value
    }

    fun openGraph(type: GraphType) {
        val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
        dialog.isVisible = true

        dialog.file ?: return

        val jsonParser = ReadWriteIntGraph()
        val file = File(dialog.directory, dialog.file)

        val graph = when (type) {
            GraphType.UNDIRECTED_WEIGHTED_GRAPH -> jsonParser.readUWGraph(file)
            GraphType.UNDIRECTED_GRAPH -> jsonParser.readUGraph(file)
            GraphType.DIRECTED_WEIGHTED_GRAPH -> jsonParser.readDWGraph(file)
            GraphType.DIRECTED_GRAPH -> jsonParser.readDGraph(file)
        }

        currentGraph.value = graph
    }

    fun saveGraph() {
        val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.SAVE)
        dialog.isVisible = true

        dialog.file ?: return

        val jsonParser = ReadWriteIntGraph()
        val file = File(dialog.directory, "${dialog.file}.json")

        when (graph) {
            is DirectedGraph -> jsonParser.writeDGraph(file, graph as DirectedGraph)
            is UndirectedGraph -> jsonParser.writeUGraph(file, graph as UndirectedGraph)
            is UndirectedWeightedGraph -> jsonParser.writeUWGraph(file, graph as UndirectedWeightedGraph)
            is DirectedWeightedGraph -> jsonParser.writeDWGraph(file, graph as DirectedWeightedGraph)
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
