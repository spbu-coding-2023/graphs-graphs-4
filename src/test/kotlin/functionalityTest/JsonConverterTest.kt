package functionalityTest

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import model.graphs.DirectedGraph
import model.graphs.Edge
import model.graphs.UndirectedGraph
import model.graphs.Vertex
import org.junit.jupiter.api.Test
import java.io.File

class JsonConverterTest {
    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun jsonTest() {
        val vertices = Array(5) { Vertex(it) }
        val edges = arrayOf(
            Edge(Vertex(0), Vertex(1)),
            Edge(vertices[1], vertices[2]),
            Edge(vertices[2], vertices[3]),
            Edge(vertices[3], vertices[4]),
            Edge(vertices[0], vertices[3]),
            Edge(vertices[0], vertices[4]),
        )

        val graph = UndirectedGraph<Int>()
        graph.addVertices(*vertices)
        graph.addEdges(*edges)

        val format = Json {
            isLenient = true
            prettyPrint = true
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
        }

        val file = File("./output.json")

        val outputStream = file.outputStream()
        format.encodeToStream(graph, outputStream)
        outputStream.close()
        assert(file.usableSpace > 0)

        val inputStream = file.inputStream()
        val returnG = format.decodeFromStream<DirectedGraph<Int>>(inputStream)
        inputStream.close()

        println(returnG.vertices())
        println(returnG.edges())
    }
}
