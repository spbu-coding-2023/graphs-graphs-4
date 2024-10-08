package model.functionality.iograph

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import model.graphs.DirectedGraph
import model.graphs.DirectedWeightedGraph
import model.graphs.UndirectedGraph
import model.graphs.UndirectedWeightedGraph
import java.io.File

class ReadWriteIntGraph {
    private val format = Json {
        isLenient = true
        prettyPrint = true
        ignoreUnknownKeys = true
        allowStructuredMapKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun writeUGraph(file: File, graph: UndirectedGraph<Int>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun writeDGraph(file: File, graph: DirectedGraph<Int>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun writeUWGraph(file: File, graph: UndirectedWeightedGraph<Int>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun writeDWGraph(file: File, graph: DirectedWeightedGraph<Int>) {
        val output = file.outputStream()
        format.encodeToStream(graph, output)
        output.close()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun readUGraph(file: File): UndirectedGraph<Int> {
        val input = file.inputStream()
        val graph = format.decodeFromStream<UndirectedGraph<Int>>(input)
        input.close()

        return graph
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun readDGraph(file: File): DirectedGraph<Int> {
        val input = file.inputStream()
        val graph = format.decodeFromStream<DirectedGraph<Int>>(input)
        input.close()

        return graph
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun readUWGraph(file: File): UndirectedWeightedGraph<Int> {
        val input = file.inputStream()
        val graph = format.decodeFromStream<UndirectedWeightedGraph<Int>>(input)
        input.close()

        return graph
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun readDWGraph(file: File): DirectedWeightedGraph<Int> {
        val input = file.inputStream()
        val graph = format.decodeFromStream<DirectedWeightedGraph<Int>>(input)
        input.close()

        return graph
    }
}
