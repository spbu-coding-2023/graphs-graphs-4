package viewmodel.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.functionality.iograph.GraphType
import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.Edge
import model.graphs.Graph
import model.graphs.GraphDirected
import model.graphs.GraphUndirected
import model.graphs.GraphWeighted
import viewmodel.graphs.GraphViewModel
import viewmodel.graphs.RepresentationStrategy
import viewmodel.placement.ForceAtlas2Placement
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
    val showChooseGraphTypeDialog = mutableStateOf(false)
    val showOpenExistingGraphDialog = mutableStateOf(false)
    val toStartingScreen = mutableStateOf(false)
    var graphViewModel = GraphViewModel(graph, showVerticesLabels, showVerticesDistanceLabels)
    var resolutionInput = mutableStateOf("")
    var randomnessInput = mutableStateOf("")

    init {
        representationStrategy.place(WIDTH, HEIGHT, graphViewModel.vertices)
    }

    fun setResolution(value: String) {
        if (value.toDoubleOrNull() != null || value.isEmpty()) {
            resolutionInput.value = value
        }
    }

    fun setRandomness(value: String) {
        if (value.toDoubleOrNull() != null || value.isEmpty()) {
            randomnessInput.value = value
        }
    }

    fun openChooseDialog() {
        showChooseGraphTypeDialog.value = true
        closeOpenDialog()   //openDialog -> chooseDialog
    }

    fun openOpenDialog() {
        showOpenExistingGraphDialog.value = true
    }

    fun closeChooseDialog() {
        showChooseGraphTypeDialog.value = false
    }

    fun closeOpenDialog() {
        showOpenExistingGraphDialog.value = false
    }

    fun showMenu() {
        showDropdownMenu.value = true
    }

    fun closeMenu() {
        showDropdownMenu.value = false
    }

    fun changeTheme() {
        darkTheme.value = !darkTheme.value
    }

    fun openToStartingScreenDialog() {
        toStartingScreen.value = true
    }

    fun closeToStartingScreenDialog() {
        toStartingScreen.value = false
    }

    fun openGraph(type: GraphType) {
        val graph = ReadWriteIntGraph().openGraph(type)

        currentGraph.value = graph
    }

    fun saveGraph() {
        ReadWriteIntGraph().saveGraph(graph)
    }

    fun highlightSCC() {
        require(graph is GraphDirected) { "Unexpected graph type: ${graph::class.simpleName}." }
        val scc = (graph as GraphDirected).findSCC()

        representationStrategy.highlightSCC(scc, *graphViewModel.vertices.toTypedArray())
    }

    fun highlightMinSpanTree() {
        require(graph is GraphUndirected) { "Unexpected graph type: ${graph::class.simpleName}." }
        val minSpanTree = (graph as GraphUndirected).findMinSpanTree() ?: return

        representationStrategy.highlightMinSpanTree(minSpanTree, *graphViewModel.edges.toTypedArray())
    }

    fun closeApp() {
        exitProcess(0)
    }

    fun showBridges() {
        require(graph is GraphUndirected) { "Unexpected graph type: ${graph::class.simpleName}." }
        val bridges = (graph as GraphUndirected<Int, E>).findBridges()

        representationStrategy.highlightBridges(graphViewModel.edges, bridges)
    }

    fun findCommunities() {
        require(graph is GraphUndirected) { "leiden method does not support directed graphs." }
        //need to make it more informative for user
        val randomness = randomnessInput.value.toDoubleOrNull() ?: return
        val resolution = resolutionInput.value.toDoubleOrNull() ?: return
        val communities = (graph as GraphUndirected<Int, E>).runLeidenMethod(randomness, resolution)

        graphViewModel.indexCommunities(communities)
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

    fun toStartingScreen() {
        currentGraph.value = null
    }
}
