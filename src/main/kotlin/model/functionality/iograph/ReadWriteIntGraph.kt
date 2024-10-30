package model.functionality.iograph

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import model.graphs.AbstractGraph
import model.graphs.DirectedGraph
import model.graphs.DirectedWeightedGraph
import model.graphs.Edge
import model.graphs.Graph
import model.graphs.UndirectedGraph
import model.graphs.UndirectedWeightedGraph
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

class ReadWriteIntGraph {
    private val format = Json {
        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    internal fun writeUGraph(file: File, graph: UndirectedGraph<Int>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    internal fun writeDGraph(file: File, graph: DirectedGraph<Int>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    internal fun writeUWGraph(file: File, graph: UndirectedWeightedGraph<Int>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    internal fun writeDWGraph(file: File, graph: DirectedWeightedGraph<Int>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    fun <E : Edge<Int>> saveGraph(graph: Graph<Int, E>) {
        val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.SAVE)
        dialog.isVisible = true

        dialog.file ?: return

        val file = File(dialog.directory, "${dialog.file}.json")

        when (graph) {
            is DirectedGraph -> writeDGraph(file, graph as DirectedGraph)
            is UndirectedGraph -> writeUGraph(file, graph as UndirectedGraph)
            is UndirectedWeightedGraph -> writeUWGraph(file, graph as UndirectedWeightedGraph)
            is DirectedWeightedGraph -> writeDWGraph(file, graph as DirectedWeightedGraph)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    internal fun readUGraph(file: File): UndirectedGraph<Int> {
        val input = file.inputStream()
        val graph = format.decodeFromStream<UndirectedGraph<Int>>(input)
        input.close()

        return graph
    }

    @OptIn(ExperimentalSerializationApi::class)
    internal fun readDGraph(file: File): DirectedGraph<Int> {
        val input = file.inputStream()
        val graph = format.decodeFromStream<DirectedGraph<Int>>(input)
        input.close()

        return graph
    }

    @OptIn(ExperimentalSerializationApi::class)
    internal fun readUWGraph(file: File): UndirectedWeightedGraph<Int> {
        val input = file.inputStream()
        val graph = format.decodeFromStream<UndirectedWeightedGraph<Int>>(input)
        input.close()

        return graph
    }

    @OptIn(ExperimentalSerializationApi::class)
    internal fun readDWGraph(file: File): DirectedWeightedGraph<Int> {
        val input = file.inputStream()
        val graph = format.decodeFromStream<DirectedWeightedGraph<Int>>(input)
        input.close()

        return graph
    }

    fun openGraph(type: GraphType): AbstractGraph<Int, out Edge<Int>>? {
        val dialog = FileDialog(Frame(), "Select Graph File", FileDialog.LOAD)
        dialog.isVisible = true

        dialog.file ?: return null

        val file = File(dialog.directory, dialog.file)

        val graph = when (type) {
            GraphType.UNDIRECTED_WEIGHTED_GRAPH -> readUWGraph(file)
            GraphType.UNDIRECTED_GRAPH -> readUGraph(file)
            GraphType.DIRECTED_WEIGHTED_GRAPH -> readDWGraph(file)
            GraphType.DIRECTED_GRAPH -> readDGraph(file)
        }

        return graph
    }
}
