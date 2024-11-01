package app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.graphs.Graph
import view.AppTheme
import view.screens.StartingScreen
import view.screens.mainScreen
import viewmodel.graphs.CircularPlacementStrategy
import viewmodel.screens.MainScreenViewModel
import viewmodel.screens.StartingScreenViewModel
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "GraphApp"
    ) {
        window.minimumSize = Dimension(800, 600)

        app()
    }
}

@Composable
fun app() {
    val darkTheme = remember { mutableStateOf(false) }
    val currentGraph = remember { mutableStateOf<Graph<Int, *>?>(null) }
    val mainScreenViewModel = remember(currentGraph.value) {
        currentGraph.value?.let { MainScreenViewModel(it, CircularPlacementStrategy(), currentGraph, darkTheme) }
    }

    AppTheme(darkTheme.value) {
        if (currentGraph.value == null) {
            StartingScreen(StartingScreenViewModel(currentGraph))
        } else {
            mainScreenViewModel?.let {
                mainScreen(viewModel = it)
            }
        }
    }
}
