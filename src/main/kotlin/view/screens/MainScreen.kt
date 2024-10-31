package view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import model.functionality.GraphAlgorithms
import model.functionality.iograph.GraphType
import model.graphs.*
import view.graphs.GraphView
import viewmodel.screens.MainScreenViewModel

@Composable
fun <E : Edge<Int>> mainScreen(viewModel: MainScreenViewModel<E>) {
    val showMenu by remember { viewModel.showDropdownMenu }
    val showOpenDialog by remember { viewModel.showOpenExistingGraphDialog }
    val showChooseDialog by remember { viewModel.showChooseGraphTypeDialog }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GraphApp") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                navigationIcon = {
                    IconButton(onClick = viewModel::showMenu) {
                        Icon(Icons.Filled.Menu, contentDescription = "Main Menu")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = viewModel::closeMenu) {
                        DropdownMenuItem(onClick = viewModel::openOpenDialog) { Text("Open Graph") }
                        DropdownMenuItem(onClick = viewModel::saveGraph) { Text("Save Graph") }
                        DropdownMenuItem(onClick = viewModel::changeTheme) { Text("Toggle Theme") }
                        Divider()
                        DropdownMenuItem(onClick = viewModel::closeApp) { Text("Exit") }
                    }
                }
            )
        }
    ) {
        mainContent(viewModel)
    }

    when {
        showOpenDialog -> OpenExistingGraphDialog(viewModel)
        showChooseDialog -> OpenChooseGraphTypeDialog(viewModel)
    }
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
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun <E : Edge<Int>> toolPanel(modifier: Modifier, viewModel: MainScreenViewModel<E>) {
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

        toolButton(GraphAlgorithms.LAYOUT, viewModel::useForceAtlas2Layout)

        if (viewModel.graph is GraphUndirected<Int, *>) {
            val resolutionInput by remember { viewModel.resolutionInput }
            val randomnessInput by remember { viewModel.randomnessInput }

            toolButton(GraphAlgorithms.BRIDGES, viewModel::showBridges)

            Row {
                TextField(
                    modifier = Modifier.weight(2f),
                    value = randomnessInput,
                    placeholder = { Text("Enter x: Double > 0. Optimal value lies in [0.0005, 0.1]") },
                    onValueChange = viewModel::setRandomness,
                    label = { Text("Randomness") }
                )

                TextField(
                    modifier = Modifier.weight(2f),
                    value = resolutionInput,
                    placeholder = { Text("Enter y: Double > 0. Higher resolution lead to more communities and lower resolutions lead to fewer communities.") },
                    onValueChange = viewModel::setResolution,
                    label = { Text("Resolution") },
                )
            }

            toolButton(GraphAlgorithms.COMMUNITIES, viewModel::findCommunities)

            toolButton(GraphAlgorithms.MINIMAL_SPANNING_TREE, viewModel::highlightMinSpanTree)
        }

        if (viewModel.graph is GraphDirected<Int, *>) {
            toolButton(GraphAlgorithms.STRONG_CONNECTION_COMPONENTS, viewModel::highlightSCC)
        }

        if (viewModel.graph is GraphWeighted<*>) {
            toolButton(GraphAlgorithms.DIJKSTRA, viewModel::findDistanceDijkstra)
        }

        if (viewModel.graph is DirectedGraph<*>) {
            toolButton(GraphAlgorithms.DISTANCE_RANK, viewModel::distanceRank)
        }

        //toolButton(GraphAlgorithms.SHORTEST_DISTANCE, viewModel::findDistanceBellman)
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

        //Эти тоглы засунуть в ModalDrawer
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

@Composable
fun toolButton(type: GraphAlgorithms, algorithm: () -> Unit) {
    Button(
        onClick = algorithm,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSurface,
        ),
        enabled = true,
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    ) {
        Icon(Icons.Default.Search, contentDescription = type.string)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = type.string)
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

//next UI element are copy-pastes from starting screen
//it's bad, I just don't have time for it
//moreover, there's more copy-paste in main screen VM
//to make it work with these functions
@Composable
fun <E : Edge<Int>> OpenExistingGraphDialog(viewModel: MainScreenViewModel<E>) {
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

@Composable
fun <E : Edge<Int>> OpenChooseGraphTypeDialog(viewModel: MainScreenViewModel<E>) {
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
fun <E : Edge<Int>> OpenGraphButton(type: GraphType, viewModel: MainScreenViewModel<E>) {
    Button(
        onClick = { viewModel.openGraph(type) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = type.string)
    }
}
