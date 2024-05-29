package model.functionality.iograph

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import model.graphs.interfaces.Graph
import model.graphs.interfaces.GraphEdge
import java.io.File

class JsonGraphParser {
    private val format = Json {
        isLenient = true
        prettyPrint = true
        allowStructuredMapKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun <E: GraphEdge<T>, T>write(file: File, graph: Graph<E, T>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    fun findType(file: File): GraphType? {
        val type = file.useLines { it.elementAtOrNull(1) } ?: return null
        println("file($file) type finded: $type")
        return when {
            GraphType.UNDIRECTED_GRAPH.type in type -> GraphType.UNDIRECTED_GRAPH
            GraphType.DIRECTED_GRAPH.type in type -> GraphType.DIRECTED_GRAPH
            GraphType.UNDIRECTED_WEIGHTED_GRAPH.type in type -> GraphType.UNDIRECTED_WEIGHTED_GRAPH
            GraphType.DIRECTED_WEIGHTED_GRAPH.type in type -> GraphType.DIRECTED_WEIGHTED_GRAPH
            else -> throw Exception("God Damn The Sun.")
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun <E: GraphEdge<T>, T>read(file: File): Graph<E, T> {
        val input =  file.inputStream()
        println("stream: ${input}")
        val graph = format.decodeFromStream<Graph<E, T>>(input)
        input.close()

        return graph
    }
}
