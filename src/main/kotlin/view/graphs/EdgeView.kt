package view.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import viewmodel.graphs.EdgeViewModel

@Suppress("FunctionNaming")
@Composable
fun <T> EdgeView(
    viewModel: EdgeViewModel<T>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
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
                strokeWidth = viewModel.width,
                cap = StrokeCap.Round
            )
        }
    }
}
