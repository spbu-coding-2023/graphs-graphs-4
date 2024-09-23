package view.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.graphs.Edge
import model.graphs.Vertex
import viewmodel.graphs.GraphViewModel

@Suppress("FunctionNaming")
@Composable
fun <T, E: Edge<T>> GraphView(
    viewModel: GraphViewModel<T, E>,
) {
    var currentVertex: Vertex<T>? by remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        viewModel.edges.forEach { edge ->
            EdgeView(edge)
        }

        viewModel.vertices.forEach { vertex ->
            VertexView(
                viewModel = vertex,
                onClick = {
                    currentVertex = vertex.value
                    viewModel.currentVertex?.isSelected = false
                    viewModel.currentVertex = vertex
                    vertex.isSelected = true
                }
            )
        }
    }
}
