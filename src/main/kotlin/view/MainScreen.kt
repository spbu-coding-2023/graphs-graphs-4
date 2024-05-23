package view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import view.graphs.GraphView
import viewmodel.MainScreenViewModel

@Composable
fun <GRAPH_TYPE, T> MainScreen(viewModel: MainScreenViewModel<GRAPH_TYPE, T>) {
	var showMenu by remember { mutableStateOf(false) }

	MaterialTheme {
		Scaffold(
			topBar = {
				TopAppBar(
					title = { Text("Graph Application") },
					navigationIcon = {
						IconButton(onClick = { showMenu = true }) {
							Icon(Icons.Filled.Menu, contentDescription = "Menu")
						}
						DropdownMenu(
							expanded = showMenu,
							onDismissRequest = { showMenu = false }
						) {
							DropdownMenuItem(onClick = { /* код */ }) {
								Text("New Graph")
							}
							DropdownMenuItem(onClick = { /* код */ }) {
								Text("Open Graph")
							}
							DropdownMenuItem(onClick = { /* код */ }) {
								Text("Save Graph")
							}
							Divider()
							DropdownMenuItem(onClick = { /* код */ }) {
								Text("Exit")
							}
						}
					}
				)
			}
		) {
			Column(modifier = Modifier.fillMaxSize()) {
				Surface(
					modifier = Modifier.weight(1f),
				) {
					GraphView(viewModel.graphViewModel)
				}
			}

//			Column {
//				Row {
//					Checkbox(checked = viewModel.showVerticesLabels.value, onCheckedChange = {
//						viewModel.showVerticesLabels.value = it
//					})
//					Text("Show vertices labels", fontSize = 28.sp, modifier = Modifier.padding(4.dp))
//				}
//
//				Row {
//					Checkbox(checked = viewModel.showEdgesLabels.value, onCheckedChange = {
//						viewModel.showEdgesLabels.value = it
//					})
//					Text("Show edges labels", fontSize = 28.sp, modifier = Modifier.padding(4.dp))
//				}
//			}
		}
	}
}


@Composable
fun <GRAPH_TYPE, T> MainRow(
	viewModel: MainScreenViewModel<GRAPH_TYPE, T>,
	modifier: Modifier
) {
	Row(
		modifier = modifier.padding(16.dp),
		verticalAlignment = Alignment.CenterVertically
	) {

	}

}