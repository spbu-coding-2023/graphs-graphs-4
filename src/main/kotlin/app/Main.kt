package app

import StartingScreen
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
import model.graphs.Graph
import view.mainScreen
import viewmodel.MainScreenViewModel
import viewmodel.graphs.CircularPlacementStrategy

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
            StartingScreen { createdGraph ->
                currentGraph.value = createdGraph
            }
        } else {
            mainScreenViewModel?.let {
                mainScreen(viewModel = it, darkTheme = darkTheme)
            }
        }
    }
}

@Preview
@Suppress("FunctionNaming")
@Composable
fun graphAppTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val darkThemeColors = darkColors(
        primary = Color(80, 60, 60),
        secondary = Color(126, 99, 99),
        secondaryVariant = Color(175, 143, 111),
        background = Color(62, 50, 50),
        surface = Color(84, 72, 72),
        onBackground = Color(174, 93, 62),
        onError = Color(255, 166, 0)
    )

    val lightThemeColors = lightColors(
        primary = Color(23, 107, 135),
        secondary = Color(180, 212, 255),
        secondaryVariant = Color(134, 182, 246),
        background = Color(238, 245, 255),
        surface = Color.White,
        onBackground = Color(52, 76, 100),
    )

    MaterialTheme(
        colors = if (darkTheme) darkThemeColors else lightThemeColors,

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
        app()
    }
}
