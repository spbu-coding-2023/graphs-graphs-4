import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.graphs.Graph
import model.graphs.UndirectedWeightedGraph

val sampleGraph = UndirectedWeightedGraph<Int>().apply {
    for (i in 1..25) {
        addVertex(i)
    }

    val nodes = arrayListOf(adjList.keys.toList())

    for (i in 0..24) {
        val v1 = (0..24).random()
        val v2 = (0..24).random()
        val weight = (1..50).random()

        addEdge(nodes[0][v1], nodes[0][v2], weight.toDouble())
    }
}


@Composable
fun StartingScreen(onGraphCreated: (Graph<*>) -> Unit) {
    var showCreateNewGraphDialog by remember { mutableStateOf(false) }
    var showOpenExistingGraphDialog by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to GraphApp", style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showCreateNewGraphDialog = true }) {
                Text("Create New Graph")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { showOpenExistingGraphDialog = true }) {
                Text("Open Existing Graph")
            }
        }
    }

    if (showCreateNewGraphDialog) {
        CreateNewGraphDialog(onDismiss = { showCreateNewGraphDialog = false }, onCreate = { graph ->
            showCreateNewGraphDialog = false
            onGraphCreated(graph)
        })
    }

    if (showOpenExistingGraphDialog) {
        OpenExistingGraphDialog(onDismiss = { showOpenExistingGraphDialog = false }, onOpen = { graph ->
            showOpenExistingGraphDialog = false
            onGraphCreated(graph)
        })
    }
}

@Composable
fun CreateNewGraphDialog(onDismiss: () -> Unit, onCreate: (Graph<*>) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Graph") },
        text = { Text("Would you like to create a new graph?") },
        confirmButton = {
            Button(onClick = {
                val newGraph = sampleGraph
                onCreate(newGraph)
            }) {
                Text("Create")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun OpenExistingGraphDialog(onDismiss: () -> Unit, onOpen: (Graph<*>) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Open Existing Graph") },
        text = { Text("Would you like to open an existing graph?") },
        confirmButton = {
            Button(onClick = {
                val existingGraph = sampleGraph
                onOpen(existingGraph)
            }) {
                Text("Open")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}