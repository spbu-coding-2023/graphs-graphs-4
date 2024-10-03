package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import model.functionality.iograph.GraphType
import model.graphs.Edge
import model.graphs.GraphDirected
import model.graphs.GraphUndirected
import model.graphs.GraphWeighted
import view.graphs.GraphView
import viewmodel.MainScreenViewModel

@Composable
fun <E: Edge<Int>> mainScreen(viewModel: MainScreenViewModel<E>, darkTheme: MutableState<Boolean>) {
    var showMenu by remember { mutableStateOf(false) }
    var showChooseGraphTypeDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Graph the Graph") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                navigationIcon = {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Main Menu")
                    }

                    appDropdownMenu(showMenu, onDismiss = { showMenu = false }) {
                        DropdownMenuItem(onClick = { showChooseGraphTypeDialog = true }) {
                            Text("Open Graph")
                        }

                        DropdownMenuItem(onClick = { viewModel.saveGraph() }) {
                            Text("Save Graph")
                        }

                        DropdownMenuItem(onClick = { darkTheme.value = !darkTheme.value }) {
                            Text("Toggle Theme")
                        }

                        Divider()

                        DropdownMenuItem(onClick = { viewModel.closeApp() }) {
                            Text("Exit")
                        }
                    }
                }
            )
        }
    ) {
        mainContent(viewModel)
    }

    if (showChooseGraphTypeDialog) {
        openChooseGraphTypeDialog(onDismiss = { showChooseGraphTypeDialog = false }, viewModel)
    }
}

@Composable
fun <E: Edge<Int>>openChooseGraphTypeDialog(onDismiss: () -> Unit, viewModel: MainScreenViewModel<E>) {
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
                    onClick = {
                        viewModel.openGraph(GraphType.UNDIRECTED_GRAPH)
                        onDismiss()
                              },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Undirected Graph")
                }
                Button(
                    onClick = {
                        viewModel.openGraph(GraphType.DIRECTED_GRAPH)
                        onDismiss()
                              },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Directed Graph")
                }
                Button(
                    onClick = {
                        viewModel.openGraph(GraphType.UNDIRECTED_WEIGHTED_GRAPH)
                        onDismiss()
                              },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Weighted Undirected Graph")
                }
                Button(
                    onClick = {
                        viewModel.openGraph(GraphType.DIRECTED_WEIGHTED_GRAPH)
                        onDismiss()
                              },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Weighted Directed Graph")
                }
            }
        }
    )
}

@Composable
fun appDropdownMenu(expanded: Boolean, onDismiss: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        content = content
    )
}

@Composable
fun <E: Edge<Int>> mainContent(
    viewModel: MainScreenViewModel<E>,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            color = MaterialTheme.colors.surface
        ) {
            GraphView(viewModel.graphViewModel)
        }

        Column(
            modifier = Modifier.width(370.dp),
        ) {
            toolPanel(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colors.secondary),
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun <E: Edge<Int>> toolPanel(
    viewModel: MainScreenViewModel<E>,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.surface)
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Tools",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colors.onSurface
        )

        Button(
            onClick = { viewModel.useForceAtlas2Layout() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSurface,
            ),
            enabled = true,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Icon(Icons.Default.Search, contentDescription = "ForceAtlas2")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "ForceAtlas2")
        }

        if (viewModel.graph is GraphUndirected<Int, *>) {
            var needBridges by remember { mutableStateOf(false) }
            var resolution by remember { mutableStateOf("") }
            var randomness by remember { mutableStateOf("") }

            Button(
                onClick = { needBridges = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSurface,
                ),
                enabled = true,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Find bridges")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Find Bridges")
            }

            if (needBridges) {
                needBridges = false

                viewModel.showBridges()
            }

            Row {
                TextField(
                    modifier = Modifier.weight(2f),
                    value = randomness,
                    placeholder = { Text("Enter x: Double > 0. Optimal value lies in [0.0005, 0.1]") },
                    onValueChange = { randomness = it },
                    label = { Text("Randomness") }
                )

                TextField(
                    modifier = Modifier.weight(2f),
                    value = resolution,
                    placeholder = { Text("Enter y: Double > 0. Higher resolution lead to more communities and lower resolutions lead to fewer communities.") },
                    onValueChange = { resolution = it },
                    label = { Text("Resolution") },
                )
            }



            Button(
                onClick = { viewModel.findCommunities(randomness, resolution) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSurface,
                ),
                enabled = true,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Find Communities")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Find Communities")
            }

            Button(
                onClick = { viewModel.highlightMinSpanTree() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSurface,
                ),
                enabled = true,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Find Minimal Spanning Tree")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Find Minimal Spanning Tree")
            }
        }

        if (viewModel.graph is GraphDirected<Int, *>) {
            Button(
                onClick = { viewModel.highlightSCC() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSurface,
                ),
                enabled = true,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Find Strong Connection Components")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Find Strong Connection Components")
            }
        }

        if (viewModel.graph is GraphWeighted<*>) {
            Button(
                onClick = {
                    viewModel.findDistanceBellman()
                    viewModel.showVerticesDistanceLabels.value = !viewModel.showVerticesDistanceLabels.value
                    viewModel.showVerticesDistanceLabels.value = !viewModel.showVerticesDistanceLabels.value
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = MaterialTheme.colors.onSurface,
                ),
                enabled = true,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Find the shortest distance")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Find Shortest Distance")
            }
        }

        toggleRow(
            label = "Show Vertices Labels",
            checked = viewModel.showVerticesLabels.value,
            onCheckedChange = { viewModel.showVerticesLabels.value = it }
        )

        toggleRow(
            label = "Show Edges Labels",
            checked = viewModel.showEdgesLabels.value,
            onCheckedChange = { viewModel.showEdgesLabels.value = it }
        )

        toggleRow(
            label = "Show Distance Labels",
            checked = viewModel.showVerticesDistanceLabels.value,
            onCheckedChange = { viewModel.showVerticesDistanceLabels.value = it }
        )
    }
}

@Suppress("FunctionNaming")
@Composable
fun toggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp),
            color = MaterialTheme.colors.onSurface
        )
    }
}
