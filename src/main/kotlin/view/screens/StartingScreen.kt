package view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.functionality.iograph.GraphType
import viewmodel.screens.StartingScreenViewModel

@Composable
fun StartingScreen(viewModel: StartingScreenViewModel) {
    val showCreateNewGraphDialog by remember { viewModel.showCreateNewGraphDialog }
    val showChooseGraphTypeDialog by remember { viewModel.showChooseGraphTypeDialog }
    val showOpenExistingGraphDialog by remember { viewModel.showOpenExistingGraphDialog }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to GraphApp", style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = viewModel::openCreateDialog) {
                Text("Create New Graph")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = viewModel::openOpenDialog) {
                Text("Open Existing Graph")
            }
        }
    }

    when {
        showCreateNewGraphDialog -> CreateNewGraphDialog(viewModel)
        showOpenExistingGraphDialog -> OpenExistingGraphDialog(viewModel)
        showChooseGraphTypeDialog -> OpenChooseGraphTypeDialog(viewModel)
    }
}

@Composable
fun OpenChooseGraphTypeDialog(viewModel: StartingScreenViewModel) {
        AlertDialog(
            onDismissRequest = { viewModel.closeChooseDialog() },
            title = { Text(text = "Choose graph type") },
            text = { Text(text = "Please select one of the options below:") },
            buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OpenGraphButton(GraphType.UNDIRECTED_GRAPH, viewModel)
                    OpenGraphButton(GraphType.DIRECTED_GRAPH, viewModel)
                    OpenGraphButton(GraphType.UNDIRECTED_WEIGHTED_GRAPH, viewModel)
                    OpenGraphButton(GraphType.DIRECTED_WEIGHTED_GRAPH, viewModel)
                }
            }
        )
}

@Composable
fun OpenGraphButton(type: GraphType, viewModel: StartingScreenViewModel) {
    Button(
        onClick = { viewModel.openGraph(type) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = type.string)
    }
}

@Composable
fun CreateNewGraphDialog(viewModel: StartingScreenViewModel) {
    AlertDialog(
        onDismissRequest = viewModel::closeCreateDialog,
        title = { Text("Create New Graph") },
        text = { Text("Would you like to create a new graph?") },
        confirmButton = {
            Button(onClick = viewModel::createGraph) { Text("Create") }
        },
        dismissButton = {
            Button(onClick = viewModel::closeCreateDialog) { Text("Cancel") }
        }
    )
}

@Composable
fun OpenExistingGraphDialog(viewModel: StartingScreenViewModel) {
    AlertDialog(
        onDismissRequest = viewModel::closeOpenDialog,
        title = { Text("Open Existing Graph") },
        text = { Text("Would you like to open an existing graph?") },
        confirmButton = {
            Button(onClick = viewModel::openChooseDialog) { Text("Open") }
        },
        dismissButton = {
            Button(onClick = viewModel::closeOpenDialog) { Text("Cancel") }
        }
    )
}
