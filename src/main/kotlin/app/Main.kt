package app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import model.graphs.UndirectedWeightedGraph
import view.MainScreen
import view.StartingScreen
import viewmodel.MainScreenViewModel
import viewmodel.graphs.CircularPlacementStrategy

@Suppress("MagicNumber")
val sampleGraph = UndirectedWeightedGraph<Int>().apply {
    for (i in 1..25) {
        addVertex(i)
    }

    val nodes = arrayListOf(adjList.keys.toList())

    for (i in 0..24) {
        val v1 = (0..24).random()
        val v2 = (0..24).random()
        val weight = (1..50).random()

        addEdge(nodes[0][v1], nodes[0][v2], weight.toDouble())
    }
}

@Composable
@Preview
@Suppress("FunctionNaming")
fun App() {
    val darkTheme = remember { mutableStateOf(false) }

    GraphAppTheme(darkTheme.value) {
        StartingScreen()
        MainScreen(MainScreenViewModel(sampleGraph, CircularPlacementStrategy()), darkTheme)
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
