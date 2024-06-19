package app

import StartingScreen
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import model.graphs.Graph
import view.MainScreen
import viewmodel.MainScreenViewModel
import viewmodel.graphs.CircularPlacementStrategy

@Composable
@Preview
@Suppress("FunctionNaming")
fun App() {
    val darkTheme = remember { mutableStateOf(false) }
    val currentGraph = remember { mutableStateOf<Graph<*>?>(null) }
    val mainScreenViewModel = remember(currentGraph.value) {
        currentGraph.value?.let { MainScreenViewModel(it, CircularPlacementStrategy()) }
    }

    GraphAppTheme(darkTheme.value) {
        if (currentGraph.value == null) {
            StartingScreen { createdGraph ->
                currentGraph.value = createdGraph
            }
        } else {
            mainScreenViewModel?.let {
                MainScreen(viewModel = it, darkTheme = darkTheme)
            }
        }
    }
}

@Suppress("FunctionNaming")
@Composable
fun GraphAppTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val darkThemeColors = darkColors(
        background = Color.White
    )

    MaterialTheme(
        colors = if (darkTheme) darkThemeColors else lightColors(),

        typography = Typography(
            defaultFontFamily = FontFamily.SansSerif,
            h1 = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp),
            body1 = TextStyle(fontSize = 16.sp)
        ),

        shapes = Shapes(
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(8.dp),
            large = RoundedCornerShape(16.dp)
        ),

        content = content
    )
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
    ) {
        App()
    }
}
