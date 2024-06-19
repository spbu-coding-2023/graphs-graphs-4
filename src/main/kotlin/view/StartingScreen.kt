package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.graphs.Graph
import model.graphs.UndirectedWeightedGraph

@Suppress("FunctionNaming")
@Composable
fun StartingScreen(): Graph<*> {
    val openExistingGraph = remember { mutableStateOf(false) }
    val createNewGraph = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Welcome to GraphApp",
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onBackground
            )

            Button(
                onClick = { openExistingGraph.value = true },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            ) {
                Text("Open Existing Graph")
            }

            Button(
                onClick = { createNewGraph.value = true },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            ) {
                Text("Create New Graph")
            }
        }
    }

    if (openExistingGraph.value) {
    }

    if (createNewGraph.value) {
    }

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

    return sampleGraph
}