package app

import StartingScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.graphs.Graph
import view.graphAppTheme
import view.screens.mainScreen
import viewmodel.graphs.CircularPlacementStrategy
import viewmodel.screens.MainScreenViewModel
import viewmodel.screens.StartingScreenViewModel

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GraphApp"
    ) {
        app()
    }
}

@Composable
fun app() {
    val darkTheme = remember { mutableStateOf(false) }
    val currentGraph = remember { mutableStateOf<Graph<Int, *>?>(null) }
    val mainScreenViewModel = remember(currentGraph.value) {
        currentGraph.value?.let { MainScreenViewModel(it, CircularPlacementStrategy()) { createdGraph ->
            currentGraph.value = createdGraph }
        }
    }

    graphAppTheme(darkTheme.value) {
        if (currentGraph.value == null) {
            StartingScreen(StartingScreenViewModel(currentGraph))
        } else {
            mainScreenViewModel?.let {
                mainScreen(viewModel = it, darkTheme = darkTheme)
            }
        }
    }
}
