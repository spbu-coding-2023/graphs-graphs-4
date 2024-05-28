package model.functionality.iograph

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import model.graphs.Graph
import java.io.File

class ReadWriteGraph {
    private val format = Json { isLenient = true; prettyPrint = true; allowStructuredMapKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    fun <GRAPH_T, K>write(file: File, graph: Graph<GRAPH_T, K>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    fun findType(file: File): GraphType? {
        val type = file.useLines { it.elementAtOrNull(2) } ?: return null
        return when {
            GraphType.UNDIRECTED_GRAPH.type in type -> GraphType.UNDIRECTED_GRAPH
            GraphType.DIRECTED_GRAPH.type in type -> GraphType.DIRECTED_GRAPH
            GraphType.UNDIRECTED_WEIGHTED_GRAPH.type in type -> GraphType.UNDIRECTED_WEIGHTED_GRAPH
            GraphType.DIRECTED_WEIGHTED_GRAPH.type in type -> GraphType.DIRECTED_WEIGHTED_GRAPH
            else -> throw Exception("God Damn The Sun.")
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun <GRAPH_T, K>read(file: File): Graph<GRAPH_T, K> {
        val input =  file.inputStream()
        val graph = format.decodeFromStream<Graph<GRAPH_T, K>>(input)
        input.close()

        return graph
    }
}
