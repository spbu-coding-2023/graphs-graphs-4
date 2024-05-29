package view.graphs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.graphs.interfaces.GraphEdge
import viewmodel.graphs.GraphViewModel

@Composable
fun <E: GraphEdge<T>, T> GraphView(
	viewModel: GraphViewModel<E, T>,
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
