package view.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import model.graphs.Vertex
import viewmodel.graphs.VertexViewModel


@Composable
fun <V> VertexView(
	viewModel: VertexViewModel<V>,
	modifier: Modifier = Modifier,
	onClick: (Vertex<V>) -> Unit
) {
	Box(
		contentAlignment = Alignment.Center,
		modifier = modifier
			.offset(viewModel.x, viewModel.y)
			.size(viewModel.radius * 2, viewModel.radius * 2)
			.background(viewModel.color, CircleShape)
			.border(2.dp, MaterialTheme.colors.onPrimary, CircleShape)
			.clickable {
				viewModel.color = Color.Red
				onClick(viewModel.v)
			}
			.pointerInput(viewModel) {
				detectDragGestures { change, dragAmount ->
					change.consume()
					viewModel.onDrag(dragAmount)
				}
			}

	) {
		if (viewModel.labelVisible) {
			Text(
				text = viewModel.label,
				style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onPrimary),
				modifier = Modifier.padding(8.dp)
			)
		}

		if (viewModel.distanceLabelVisible) {
			Text(
				text = viewModel.distanceLabel,
				style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onBackground),
				modifier = Modifier.align(Alignment.TopCenter).padding(top = 6.dp)
			)
		}
	}
}