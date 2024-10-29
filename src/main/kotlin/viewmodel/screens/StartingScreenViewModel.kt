package viewmodel.screens

import androidx.compose.runtime.mutableStateOf
import model.functionality.iograph.GraphType
import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.Graph
import model.graphs.UndirectedGraph
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

val SAMPLE_GRAPH = UndirectedGraph<Int>().apply {
    for (i in 1..34) {
        addVertex(i)
    }

    val nodes = arrayListOf(adjList.keys.toList())

    addEdge(nodes[0][1], nodes[0][0])
    addEdge(nodes[0][0], nodes[0][2])
    addEdge(nodes[0][2], nodes[0][1])
    addEdge(nodes[0][3], nodes[0][0])
    addEdge(nodes[0][3], nodes[0][1])
    addEdge(nodes[0][3], nodes[0][2])
    addEdge(nodes[0][4], nodes[0][0])
    addEdge(nodes[0][5], nodes[0][0])
    addEdge(nodes[0][6], nodes[0][0])
    addEdge(nodes[0][6], nodes[0][4])
    addEdge(nodes[0][6], nodes[0][5])
    addEdge(nodes[0][7], nodes[0][0])
    addEdge(nodes[0][7], nodes[0][1])
    addEdge(nodes[0][7], nodes[0][2])
    addEdge(nodes[0][7], nodes[0][3])
    addEdge(nodes[0][8], nodes[0][1])
    addEdge(nodes[0][8], nodes[0][2])
    addEdge(nodes[0][9], nodes[0][2])
    addEdge(nodes[0][10], nodes[0][0])
    addEdge(nodes[0][10], nodes[0][4])
    addEdge(nodes[0][10], nodes[0][5])
    addEdge(nodes[0][11], nodes[0][0])
    addEdge(nodes[0][12], nodes[0][0])
    addEdge(nodes[0][12], nodes[0][3])
    addEdge(nodes[0][13], nodes[0][0])
    addEdge(nodes[0][13], nodes[0][1])
    addEdge(nodes[0][13], nodes[0][2])
    addEdge(nodes[0][13], nodes[0][3])
    addEdge(nodes[0][16], nodes[0][5])
    addEdge(nodes[0][16], nodes[0][6])
    addEdge(nodes[0][17], nodes[0][0])
    addEdge(nodes[0][17], nodes[0][1])
    addEdge(nodes[0][19], nodes[0][0])
    addEdge(nodes[0][19], nodes[0][1])
    addEdge(nodes[0][21], nodes[0][0])
    addEdge(nodes[0][21], nodes[0][1])
    addEdge(nodes[0][25], nodes[0][23])
    addEdge(nodes[0][25], nodes[0][24])
    addEdge(nodes[0][27], nodes[0][2])
    addEdge(nodes[0][27], nodes[0][23])
    addEdge(nodes[0][27], nodes[0][24])
    addEdge(nodes[0][28], nodes[0][2])
    addEdge(nodes[0][29], nodes[0][23])
    addEdge(nodes[0][29], nodes[0][26])
    addEdge(nodes[0][30], nodes[0][1])
    addEdge(nodes[0][30], nodes[0][8])
    addEdge(nodes[0][31], nodes[0][0])
    addEdge(nodes[0][31], nodes[0][24])
    addEdge(nodes[0][31], nodes[0][25])
    addEdge(nodes[0][31], nodes[0][28])
    addEdge(nodes[0][32], nodes[0][2])
    addEdge(nodes[0][32], nodes[0][8])
    addEdge(nodes[0][32], nodes[0][14])
    addEdge(nodes[0][32], nodes[0][15])
    addEdge(nodes[0][32], nodes[0][18])
    addEdge(nodes[0][32], nodes[0][20])
    addEdge(nodes[0][32], nodes[0][22])
    addEdge(nodes[0][32], nodes[0][23])
    addEdge(nodes[0][32], nodes[0][29])
    addEdge(nodes[0][32], nodes[0][30])
    addEdge(nodes[0][32], nodes[0][31])
    addEdge(nodes[0][33], nodes[0][8])
    addEdge(nodes[0][33], nodes[0][9])
    addEdge(nodes[0][33], nodes[0][13])
    addEdge(nodes[0][33], nodes[0][14])
    addEdge(nodes[0][33], nodes[0][15])
    addEdge(nodes[0][33], nodes[0][18])
    addEdge(nodes[0][33], nodes[0][19])
    addEdge(nodes[0][33], nodes[0][20])
    addEdge(nodes[0][33], nodes[0][22])
    addEdge(nodes[0][33], nodes[0][23])
    addEdge(nodes[0][33], nodes[0][26])
    addEdge(nodes[0][33], nodes[0][27])
    addEdge(nodes[0][33], nodes[0][28])
    addEdge(nodes[0][33], nodes[0][29])
    addEdge(nodes[0][33], nodes[0][30])
    addEdge(nodes[0][33], nodes[0][31])
    addEdge(nodes[0][33], nodes[0][32])
}

class StartingScreenViewModel(val onGraphCreated: (Graph<Int, *>) -> Unit) {
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
        val newGraph = SAMPLE_GRAPH
        onGraphCreated(newGraph)
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

        showChooseGraphTypeDialog.value = false
        onGraphCreated(graph)
    }
}