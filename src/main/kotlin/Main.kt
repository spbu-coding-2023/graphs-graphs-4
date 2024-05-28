import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.graphs.Graph
import model.graphs.UndirectedGraph
import model.graphs.Vertex
import view.MainScreen
import viewmodel.MainScreenViewModel
import viewmodel.graphs.CircularPlacementStrategy


val sampleGraph: Graph<Vertex<Int>, Int> = UndirectedGraph<Int>().apply {
	for (i in 1..10) {
		addVertex(i)
	}

	val nodes = arrayListOf(adjList.keys.toList())

	addEdge(nodes[0][0], nodes[0][1])
	addEdge(nodes[0][1], nodes[0][2])
	addEdge(nodes[0][2], nodes[0][3])
	addEdge(nodes[0][0], nodes[0][6])
	addEdge(nodes[0][6], nodes[0][7])
	addEdge(nodes[0][7], nodes[0][8])
	addEdge(nodes[0][2], nodes[0][8])
	addEdge(nodes[0][3], nodes[0][4])
	addEdge(nodes[0][4], nodes[0][5])
	addEdge(nodes[0][4], nodes[0][9])
}

@Composable
@Preview
fun App() {
	MainScreen(MainScreenViewModel(sampleGraph, CircularPlacementStrategy()))
}

fun main() = application {
	Window(onCloseRequest = ::exitApplication) {
		App()
	}
}
