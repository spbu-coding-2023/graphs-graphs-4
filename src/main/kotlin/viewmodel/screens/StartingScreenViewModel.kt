package viewmodel.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.functionality.iograph.GraphType
import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.Graph
import model.graphs.UndirectedWeightedGraph
import model.graphs.Vertex
import model.graphs.WeightedEdge
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
        val randomGraph = UndirectedWeightedGraph<Int>()
        val amount = Random.nextInt(2, 64)
        val degree = Random.nextInt(1, amount * 2)

        for (vertex in 1..amount) {
            randomGraph.addVertex(vertex)
        }

        repeat(degree) {
            val u = Vertex(Random.nextInt(amount))
            val v = Vertex(Random.nextInt(amount))

            if (randomGraph.contains(u) && randomGraph.contains(v)) {
                randomGraph.addEdge(WeightedEdge(u, v, Random.nextDouble(64.0)))
            }
        }

        currentGraph.value = randomGraph
    }

    fun openGraph(type: GraphType) {
        val graph = ReadWriteIntGraph().openGraph(type)

        currentGraph.value = graph
    }
}
