package viewmodel.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.functionality.iograph.GraphType
import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.Graph
import model.graphs.UndirectedGraph
import model.graphs.UnweightedEdge
import model.graphs.Vertex
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import kotlin.random.Random

class StartingScreenViewModel(
    private val currentGraph: MutableState<Graph<Int, *>?>
) {
    val showCreateNewGraphDialog = mutableStateOf(false)
    val showChooseGraphTypeDialog = mutableStateOf(false)
    val showOpenExistingGraphDialog = mutableStateOf(false)

    fun openCreateDialog() {
        showCreateNewGraphDialog.value = true
    }

    fun openChooseDialog() {
        showChooseGraphTypeDialog.value = true
        closeOpenDialog()   //openDialog -> chooseDialog
    }

    fun openOpenDialog() {
        showOpenExistingGraphDialog.value = true
    }

    fun closeCreateDialog() {
        showCreateNewGraphDialog.value = false
    }

    fun closeChooseDialog() {
        showChooseGraphTypeDialog.value = false
    }

    fun closeOpenDialog() {
        showOpenExistingGraphDialog.value = false
    }

    fun createGraph() {
        val randomGraph = UndirectedGraph<Int>()
        val amount = Random.nextInt(2, 64)
        val degree = Random.nextInt(1, amount * 2)

        for (vertex in 1..amount) {
            randomGraph.addVertex(vertex)
        }

        for (edge in 0..degree) {
            val u = Vertex(Random.nextInt(amount))
            val v = Vertex(Random.nextInt(amount))

            if (randomGraph.contains(u) && randomGraph.contains(v)) {
                randomGraph.addEdge(UnweightedEdge(u, v))
            }
        }

        currentGraph.value = randomGraph
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
}
