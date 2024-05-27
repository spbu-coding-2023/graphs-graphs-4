package view.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import viewmodel.graphs.EdgeViewModel

@Composable
fun <T> EdgeView(
	viewModel: EdgeViewModel<T>,
	modifier: Modifier = Modifier,
) {
	Canvas(modifier = modifier.fillMaxSize()) {
		drawLine(
			start = Offset(
				viewModel.u.x.toPx() + viewModel.u.radius.toPx(),
				viewModel.u.y.toPx() + viewModel.u.radius.toPx(),
			),

			end = Offset(
				viewModel.v.x.toPx() + viewModel.v.radius.toPx(),
				viewModel.v.y.toPx() + viewModel.v.radius.toPx(),
			),

			color = viewModel.color,

			strokeWidth = viewModel.width
		)
	}

	if (viewModel.labelVisible) {
		Text(
			modifier = Modifier
				.offset(
					viewModel.u.x + (viewModel.v.x - viewModel.u.x) / 2,
					viewModel.u.y + (viewModel.v.y - viewModel.u.y) / 2
				),
			text = viewModel.label,
		)
	}
}