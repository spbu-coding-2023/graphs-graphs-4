package view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import view.graphs.GraphView
import viewmodel.MainScreenViewModel


@Composable
fun <GRAPH_TYPE, T> MainScreen(viewModel: MainScreenViewModel<GRAPH_TYPE, T>) {
	val drawerState = rememberDrawerState(DrawerValue.Closed)
	val scope = rememberCoroutineScope()
	val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
	var expanded by remember { mutableStateOf(false) }

	Column(modifier = Modifier.fillMaxSize()) {
		Scaffold(
			scaffoldState = scaffoldState,

			topBar = {
				TopAppBar(
					title = { Text("Graph Editor") },

					navigationIcon = {
						IconButton(onClick = { expanded = true }) {
							Icon(Icons.Filled.Menu, contentDescription = "Main Menu")
						}
					}
				)
			}
		) {
			Surface(
				modifier = Modifier.weight(1f),
			) {
				GraphView(viewModel.graphViewModel)
			}

			Column {
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
			}
		}
	}
}