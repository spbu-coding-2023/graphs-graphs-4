package view.graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import viewmodel.graphs.GraphViewModel

@Composable
fun <V, E> GraphView(
	viewModel: GraphViewModel<V, E>,
) {
	Box(
		modifier = Modifier
			.fillMaxSize()

	) {
		viewModel.vertices.forEach { v ->
			VertexView(v, Modifier)
		}

		viewModel.edges.forEach { e ->
			EdgeView(e, Modifier)
		}
	}
}