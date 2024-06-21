package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import model.graphs.GraphUndirected
import model.graphs.GraphWeighted
import model.graphs.Vertex
import view.graphs.GraphView
import viewmodel.MainScreenViewModel

@Suppress("FunctionNaming")
@Composable
fun <T> MainScreen(viewModel: MainScreenViewModel<T>, darkTheme: MutableState<Boolean>) {
    var showMenu by remember { mutableStateOf(false) }
    var showGraph by remember { mutableStateOf(false) }
    var currentVertex by remember { mutableStateOf(viewModel.graphViewModel.currentVertex) }

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

                    AppDropdownMenu(showMenu, onDismiss = { showMenu = false }) {
                        DropdownMenuItem(onClick = { showGraph = true }) {
                            Text("New Graph")
                        }

//                            DropdownMenuItem(onClick = { viewModel.openFile() }) {
//                                Text("Open Graph")
//                            }

                        DropdownMenuItem(onClick = { /* код */ }) {
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
        MainContent(viewModel, currentVertex?.value)
    }
}


@Suppress("FunctionNaming")
@Composable
fun AppDropdownMenu(expanded: Boolean, onDismiss: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        content = content
    )
}

@Suppress("FunctionNaming")
@Composable
fun <T> MainContent(
    viewModel: MainScreenViewModel<T>,
    vertex: Vertex<T>?
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
            ToolPanel(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colors.secondary),
                viewModel = viewModel,
                selectedVertex = vertex,
            )
        }
    }
}

@Suppress("FunctionNaming")
@Composable
fun <T> ToolPanel(
    viewModel: MainScreenViewModel<T>,
    modifier: Modifier = Modifier,
    selectedVertex: Vertex<T>?,
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

        var text by remember { mutableStateOf(selectedVertex?.key.toString()) }

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )

        if (viewModel.graph is GraphUndirected<*>) {
            var needBridges by remember { mutableStateOf(false) }

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

        }

        if (viewModel.graph is GraphWeighted<*>) {
            Button(
                // (viewModel::findDistanceBellman)(selectedVertex)
                onClick = { },
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


//        Button(
//            onClick = viewModel::highlightMinSpanTree,
//            enabled = true,
//            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
//        ) {
//            Icon(Icons.Default.Search, contentDescription = "Find the shortest distance")
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(text = "Find Min Span Tree")
//        }

//        Button(
//            onClick = viewModel::highlightSCC,
//            enabled = true,
//            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
//        ) {
//            Icon(Icons.Default.Search, contentDescription = "Find the shortest distance")
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(text = "Find SCC")
//        }

//        ToggleRow(
//            label = "Show Vertices Labels",
//            checked = viewModel.showVerticesLabels.value,
//            onCheckedChange = { viewModel.showVerticesLabels.value = it }
//        )
//
//        ToggleRow(
//            label = "Show Edges Labels",
//            checked = viewModel.showEdgesLabels.value,
//            onCheckedChange = { viewModel.showEdgesLabels.value = it }
//        )
//
//        ToggleRow(
//            label = "Show Distance Labels",
//            checked = viewModel.showVerticesDistanceLabels.value,
//            onCheckedChange = { viewModel.showVerticesDistanceLabels.value = it }
//        )
//
//        Button(
//            onClick = viewModel::resetGraphView,
//            enabled = true,
//            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
//        ) {
//            Icon(Icons.Default.Refresh, contentDescription = "Reset default settings")
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(text = "Reset Default Settings")
//        }
    }
}

@Suppress("FunctionNaming")
@Composable
fun ToggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
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
