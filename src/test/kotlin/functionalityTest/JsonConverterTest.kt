package functionalityTest

import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals

class JsonConverterTest {

    @Test
    fun jsonUndirectedReadWriteTest() {
        val vertices = Array(5) { Vertex(it) }
        val edges = arrayOf(
            UnweightedEdge(Vertex(0), Vertex(1)),
            UnweightedEdge(vertices[1], vertices[2]),
            UnweightedEdge(vertices[2], vertices[3]),
            UnweightedEdge(vertices[3], vertices[4]),
            UnweightedEdge(vertices[0], vertices[3]),
            UnweightedEdge(vertices[0], vertices[4]),
        )

        val graph = UndirectedGraph<Int>()
        graph.addVertices(*vertices)
        graph.addEdges(*edges)

        val otherGraph = UndirectedGraph<Int>()
        otherGraph.addVertices(*vertices)
        otherGraph.addEdges(*edges)

        val file = File("./testGraphs/graphU.json")

        ReadWriteIntGraph().writeUGraph(file, graph)
        val graphRead = ReadWriteIntGraph().readUGraph(file)
        //using toString, because without them test won't pass (though graph are really identical)
        assertEquals(graph.vertices().toString(), graphRead.vertices().toString())
        assertEquals(graph.edges().toString(), graphRead.edges().toString())
    }

    @Test
    fun jsonDirectedReadWriteTest() {
        val vertices = Array(6) { Vertex(it) }
        val edges = arrayOf(
            UnweightedEdge(Vertex(0), Vertex(1)),
            UnweightedEdge(vertices[1], vertices[2]),
            UnweightedEdge(vertices[2], vertices[4]),
            UnweightedEdge(vertices[4], vertices[2]),
            UnweightedEdge(vertices[0], vertices[5]),
            UnweightedEdge(vertices[0], vertices[4]),
        )

        val graph = DirectedGraph<Int>()
        graph.addVertices(*vertices)
        graph.addEdges(*edges)
        for (vertex in graph) println(vertex)

        val file = File("./testGraphs/graphD.json")

        ReadWriteIntGraph().writeDGraph(file, graph)

        val graphRead = ReadWriteIntGraph().readDGraph(file)
        //using toString, because without them test won't pass
        assertEquals(graph.vertices().toString(), graphRead.vertices().toString())
        assertEquals(graph.edges().toString(), graphRead.edges().toString())
    }

    @Test
    fun jsonUndirectedWeightedReadWriteTest() {
        val vertices = Array(10) { Vertex(it) }
        val edges = arrayOf(
            WeightedEdge(Vertex(0), Vertex(1), 42.0),
            WeightedEdge(vertices[1], vertices[2], 7461.0),
            WeightedEdge(vertices[2], vertices[3], 808.0),
            WeightedEdge(vertices[3], vertices[4], 101.0),
            WeightedEdge(vertices[0], vertices[3], 104.6),
            WeightedEdge(vertices[0], vertices[4], 1976.0),
            WeightedEdge(vertices[8], vertices[9], 0.0)
        )

        val graph = UndirectedWeightedGraph<Int>()
        graph.addVertices(*vertices)
        graph.addEdges(*edges)

        val file = File("./testGraphs/graphUW.json")

        ReadWriteIntGraph().writeUWGraph(file, graph)

        val graphReaded = ReadWriteIntGraph().readUWGraph(file)
        //using toString, because without them this test won't pass
        assertEquals(graph.vertices().toString(), graphReaded.vertices().toString())
        assertEquals(graph.edges().toString(), graphReaded.edges().toString())
    }

    @Test
    fun jsonDirectedWeightedReadWriteTest() {
        val vertices = Array(4) { Vertex(it) }
        val edges = arrayOf(
            WeightedEdge(Vertex(0), Vertex(1), 1999.0),
            WeightedEdge(vertices[2], vertices[3], 2000.0),
            WeightedEdge(vertices[0], vertices[3], 52.0),
            WeightedEdge(vertices[2], vertices[1], 7.0),
        )

        val graph = DirectedWeightedGraph<Int>()
        graph.addVertices(*vertices)
        graph.addEdges(*edges)

        val file = File("./testGraphs/graphDW.json")

        ReadWriteIntGraph().writeDWGraph(file, graph)

        val graphReaded = ReadWriteIntGraph().readDWGraph(file)
        //using toString, because without them this test won't pass
        assertEquals(graph.vertices().toString(), graphReaded.vertices().toString())
        assertEquals(graph.edges().toString(), graphReaded.edges().toString())
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun createTestDirectory() {
            val directoryPath = Paths.get("./testGraphs")
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath)
            }
        }


        //COMMENT THIS FUNCTION IF YOU WANT testGraphs DIRECTORY (all test graphs saved here)
        @JvmStatic
        @AfterAll
        fun deleteTestDirectory() {
            val directoryPath = Paths.get("./testGraphs")
            if (Files.exists(directoryPath)) {
                directoryPath.toFile().deleteRecursively()
            }
        }
    }
}