package functionalityTest

import model.functionality.iograph.ReadWriteIntGraph
import model.graphs.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

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

        val file = File("./testGraphs/graphU.json")

        ReadWriteIntGraph().writeUGraph(file, graph)
        val graphReaded = ReadWriteIntGraph().readUGraph(file)
//        assertEquals(graph.vertices().toSet(), graphReaded.vertices().toSet())
//        assertEquals(graph.edges().toSet(), graphReaded.edges().toSet())
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

        val graphReaded = ReadWriteIntGraph().readDGraph(file)
//        assertEquals(graph.vertices().toSet(), graphReaded.vertices().toSet())
//        assertEquals(graph.edges().toSet(), graphReaded.edges().toSet())
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
//        assertEquals(graph.vertices().toSet(), graphReaded.vertices().toSet())
//        assertEquals(graph.edges().toSet(), graphReaded.edges().toSet())
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
//        assertEquals(graph.vertices().toSet(), graphReaded.vertices().toSet())
//        assertEquals(graph.edges().toSet(), graphReaded.edges().toSet())
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun createTestDirectory() {
            Files.createDirectory(Paths.get("./testGraphs"))
        }

//        @JvmStatic
//        @AfterAll
//        fun deleteTestDirectory() {
//            Files.delete(Paths.get("./testGraphs/"))
//        }
    }
}