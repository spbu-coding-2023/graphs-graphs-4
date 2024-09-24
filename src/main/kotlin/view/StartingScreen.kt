
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.Graph
import model.graphs.UndirectedGraph
import java.awt.FileDialog
import java.awt.Frame
import java.io.File


val sampleGraph = UndirectedGraph<Int>().apply {
    for (i in 1..34) {
        addVertex(i)
    }

    val nodes = arrayListOf(adjList.keys.toList())

    addEdge(nodes[0][1], nodes[0][0])
    addEdge(nodes[0][0], nodes[0][2])
    addEdge(nodes[0][2], nodes[0][1])
    addEdge(nodes[0][3], nodes[0][0])
    addEdge(nodes[0][3], nodes[0][1])
    addEdge(nodes[0][3], nodes[0][2])
    addEdge(nodes[0][4], nodes[0][0])
    addEdge(nodes[0][5], nodes[0][0])
    addEdge(nodes[0][6], nodes[0][0])
    addEdge(nodes[0][6], nodes[0][4])
    addEdge(nodes[0][6], nodes[0][5])
    addEdge(nodes[0][7], nodes[0][0])
    addEdge(nodes[0][7], nodes[0][1])
    addEdge(nodes[0][7], nodes[0][2])
    addEdge(nodes[0][7], nodes[0][3])
    addEdge(nodes[0][8], nodes[0][1])
    addEdge(nodes[0][8], nodes[0][2])
    addEdge(nodes[0][9], nodes[0][2])
    addEdge(nodes[0][10], nodes[0][0])
    addEdge(nodes[0][10], nodes[0][4])
    addEdge(nodes[0][10], nodes[0][5])
    addEdge(nodes[0][11], nodes[0][0])
    addEdge(nodes[0][12], nodes[0][0])
    addEdge(nodes[0][12], nodes[0][3])
    addEdge(nodes[0][13], nodes[0][0])
    addEdge(nodes[0][13], nodes[0][1])
    addEdge(nodes[0][13], nodes[0][2])
    addEdge(nodes[0][13], nodes[0][3])
    addEdge(nodes[0][16], nodes[0][5])
    addEdge(nodes[0][16], nodes[0][6])
    addEdge(nodes[0][17], nodes[0][0])
    addEdge(nodes[0][17], nodes[0][1])
    addEdge(nodes[0][19], nodes[0][0])
    addEdge(nodes[0][19], nodes[0][1])
    addEdge(nodes[0][21], nodes[0][0])
    addEdge(nodes[0][21], nodes[0][1])
    addEdge(nodes[0][25], nodes[0][23])
    addEdge(nodes[0][25], nodes[0][24])
    addEdge(nodes[0][27], nodes[0][2])
    addEdge(nodes[0][27], nodes[0][23])
    addEdge(nodes[0][27], nodes[0][24])
    addEdge(nodes[0][28], nodes[0][2])
    addEdge(nodes[0][29], nodes[0][23])
    addEdge(nodes[0][29], nodes[0][26])
    addEdge(nodes[0][30], nodes[0][1])
    addEdge(nodes[0][30], nodes[0][8])
    addEdge(nodes[0][31], nodes[0][0])
    addEdge(nodes[0][31], nodes[0][24])
    addEdge(nodes[0][31], nodes[0][25])
    addEdge(nodes[0][31], nodes[0][28])
    addEdge(nodes[0][32], nodes[0][2])
    addEdge(nodes[0][32], nodes[0][8])
    addEdge(nodes[0][32], nodes[0][14])
    addEdge(nodes[0][32], nodes[0][15])
    addEdge(nodes[0][32], nodes[0][18])
    addEdge(nodes[0][32], nodes[0][20])
    addEdge(nodes[0][32], nodes[0][22])
    addEdge(nodes[0][32], nodes[0][23])
    addEdge(nodes[0][32], nodes[0][29])
    addEdge(nodes[0][32], nodes[0][30])
    addEdge(nodes[0][32], nodes[0][31])
    addEdge(nodes[0][33], nodes[0][8])
    addEdge(nodes[0][33], nodes[0][9])
    addEdge(nodes[0][33], nodes[0][13])
    addEdge(nodes[0][33], nodes[0][14])
    addEdge(nodes[0][33], nodes[0][15])
    addEdge(nodes[0][33], nodes[0][18])
    addEdge(nodes[0][33], nodes[0][19])
    addEdge(nodes[0][33], nodes[0][20])
    addEdge(nodes[0][33], nodes[0][22])
    addEdge(nodes[0][33], nodes[0][23])
    addEdge(nodes[0][33], nodes[0][26])
    addEdge(nodes[0][33], nodes[0][27])
    addEdge(nodes[0][33], nodes[0][28])
    addEdge(nodes[0][33], nodes[0][29])
    addEdge(nodes[0][33], nodes[0][30])
    addEdge(nodes[0][33], nodes[0][31])
    addEdge(nodes[0][33], nodes[0][32])
}


@Composable
fun StartingScreen(onGraphCreated: (Graph<Int, *>) -> Unit) {
    var showCreateNewGraphDialog by remember { mutableStateOf(false) }
    var showChooseGraphTypeDialog by remember { mutableStateOf(false) }
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
            showChooseGraphTypeDialog = true
        })
    }

    if (showChooseGraphTypeDialog) {
        OpenChooseGraphTypeDialog(
            onDismiss =  { showChooseGraphTypeDialog = false },
            onButton1Click = {
                    val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
                    dialog.isVisible = true
                    if (dialog.file != null) {
                        val graph = ReadWriteIntGraph().readUGraph(File(dialog.directory, dialog.file))
                        onGraphCreated(graph)
                    }

                    showChooseGraphTypeDialog = false
            },
            onButton2Click = {
                val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
                dialog.isVisible = true
                if (dialog.file != null) {
                    val graph = ReadWriteIntGraph().readDGraph(File(dialog.directory, dialog.file))
                    onGraphCreated(graph)
                }

                showChooseGraphTypeDialog = false
            },
            onButton3Click = {
                val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
                dialog.isVisible = true
                if (dialog.file != null) {
                    val graph = ReadWriteIntGraph().readUWGraph(File(dialog.directory, dialog.file))
                    onGraphCreated(graph)
                }

                showChooseGraphTypeDialog = false
            },
            onButton4Click = {
                val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
                dialog.isVisible = true
                if (dialog.file != null) {
                    val graph = ReadWriteIntGraph().readDWGraph(File(dialog.directory, dialog.file))
                    onGraphCreated(graph)
                }

                showChooseGraphTypeDialog = false
            }
        )
    }
}

@Composable
fun OpenChooseGraphTypeDialog(
    onDismiss: () -> Unit,
    onButton1Click: () -> Unit,
    onButton2Click: () -> Unit,
    onButton3Click: () -> Unit,
    onButton4Click: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Choose graph type") },
            text = { Text(text = "Please select one of the options below:") },
            buttons = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = onButton1Click,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Undirected Graph")
                    }
                    Button(
                        onClick = onButton2Click,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Directed Graph")
                    }
                    Button(
                        onClick = onButton3Click,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Weighted Undirected Graph")
                    }
                    Button(
                        onClick = onButton4Click,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Weighted Directed Graph")
                    }
                }
            }
        )
    }

@Composable
fun CreateNewGraphDialog(onDismiss: () -> Unit, onCreate: (Graph<Int, *>) -> Unit) {
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
fun OpenExistingGraphDialog(onDismiss: () -> Unit, onOpen: (Graph<*, *>) -> Unit) {
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