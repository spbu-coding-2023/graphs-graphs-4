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

//        if (viewModel.islWeightLabelVisible) {
//            Text(
//                modifier = Modifier
//                    .offset(
//                        viewModel.u.x + (viewModel.v.x - viewModel.u.x) / 2,
//                        viewModel.u.y + (viewModel.v.y - viewModel.u.y) / 2
//                    )
//                    .background(MaterialTheme.colors.surface, RoundedCornerShape(4.dp))
//                    .padding(4.dp),
//                text = viewModel.label,
//                style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onSurface)
//            )
//        }
    }
}
