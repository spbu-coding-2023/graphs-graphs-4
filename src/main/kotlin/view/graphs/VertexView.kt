package view.graphs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
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
	Box(modifier = modifier
		.size(viewModel.radius * 2, viewModel.radius * 2)
		.offset(viewModel.x, viewModel.y)
		.background(
			color = viewModel.color,
			shape = CircleShape
		)
		.pointerInput(viewModel) {
			detectDragGestures { change, dragAmount ->
				change.consume()
				viewModel.onDrag(dragAmount)
			}
		}
		.clickable(onClick = {
			viewModel.color = Color.Green
			onClick(viewModel.v)
		})
	) {
		if (viewModel.labelVisible) {
			Text(
				modifier = Modifier
					.align(Alignment.Center)
					.offset(0.dp, -viewModel.radius - 10.dp),
				text = viewModel.label,
			)
		}
		if (viewModel.distanceLabelVisible) {
			Text(
				modifier = Modifier
					.align(Alignment.Center)
					.offset(0.dp, -viewModel.radius - 10.dp),
				text = viewModel.distanceLabel,
				color = MaterialTheme.colors.primary
			)
		}
	}
}