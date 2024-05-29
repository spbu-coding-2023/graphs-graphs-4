import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.graphs.Graph
import model.graphs.UndirectedWeightedGraph
import model.graphs.Vertex
import view.MainScreen
import viewmodel.MainScreenViewModel
import viewmodel.graphs.CircularPlacementStrategy

@Suppress("MagicNumber")
val sampleGraph: Graph<Pair<Vertex<Int>, Int>, Int> = UndirectedWeightedGraph<Int, Int>().apply {
	for (i in 1..25) {
		addVertex(i)
	}

	val nodes = arrayListOf(adjList.keys.toList())

	for (i in 0..24) {
		val v1 = (0..24).random()
		val v2 = (0..24).random()
		val weight = (1..50).random()

		addEdge(nodes[0][v1], nodes[0][v2], weight)
	}
}

@Composable
@Preview
@Suppress("FunctionNaming")
fun App() {
	MainScreen(MainScreenViewModel(sampleGraph, CircularPlacementStrategy()))
}

fun main() = application {
	Window(
		onCloseRequest = ::exitApplication,
	) {
		App()
	}
}
