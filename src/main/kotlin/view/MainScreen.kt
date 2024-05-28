package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.graphs.Vertex
import view.graphs.GraphView
import viewmodel.MainScreenViewModel

@Composable
fun <GRAPH_TYPE, T> MainScreen(viewModel: MainScreenViewModel<GRAPH_TYPE, T>) {
	var showMenu by remember { mutableStateOf(false) }
	var showGraph by remember { mutableStateOf(false) }
	var currentVertex: Vertex<T>? by remember { mutableStateOf(null) }

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
							DropdownMenuItem(onClick = { showGraph = true }) {
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
			Row(
				horizontalArrangement = Arrangement.spacedBy(20.dp)
			) {
				Column(modifier = Modifier.width(370.dp)) {
					ToolsPanel(
						modifier = Modifier.weight(1f).background(MaterialTheme.colors.secondary),
						viewModel = viewModel,
						selectedVertex = currentVertex
					)
				}

				Surface(modifier = Modifier.weight(1f)) {
					if (showGraph) {
						GraphView(viewModel.graphViewModel, onVertexClick = { currentVertex = it })
					}
				}
			}
		}
	}
}

@Composable
fun <GRAPH_TYPE, T> ToolsPanel(
	viewModel: MainScreenViewModel<GRAPH_TYPE, T>,
	modifier: Modifier = Modifier,
	selectedVertex: Vertex<T>?
) {
	Column(
		modifier = modifier
			.fillMaxHeight()
			.padding(16.dp)
	) {
		Button(
			onClick = viewModel::highlightBridges,
			enabled = true,
		) {
			Text(text = "Find bridges")
		}

		Button(
			onClick = { (viewModel::findDistanceBellman)(selectedVertex) },
			enabled = true,
		) {
			Text(text = "Find the shortest distance")
		}

		Row {
			Checkbox(checked = viewModel.showVerticesLabels.value, onCheckedChange = {
				viewModel.showVerticesLabels.value = it
			})
			Text("Show vertices labels", fontSize = 28.sp, modifier = Modifier.padding(4.dp))
		}
		Row {
			Checkbox(checked = viewModel.showEdgesLabels.value, onCheckedChange = {
				viewModel.showEdgesLabels.value = it
			})
			Text("Show edges labels", fontSize = 28.sp, modifier = Modifier.padding(4.dp))
		}
		Row {
			Checkbox(checked = viewModel.showVerticesDistanceLabels.value, onCheckedChange = {
				viewModel.showVerticesDistanceLabels.value = it
			})
			Text("Show distance labels", fontSize = 28.sp, modifier = Modifier.padding(4.dp))
		}

		Button(
			onClick = viewModel::resetGraphView,
			enabled = true,
		) {
			Text(text = "Reset default settings")
		}
	}
}