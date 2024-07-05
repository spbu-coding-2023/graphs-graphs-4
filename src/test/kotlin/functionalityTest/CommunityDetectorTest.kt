package functionalityTest

import model.functionality.CommunityDetector
import model.graphs.UndirectedGraph
import model.graphs.UnweightedEdge
import model.graphs.Vertex
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CommunityDetectorTest {

    @Nested
    inner class PrivateMethodsTest {
        private val sampleGraph = UndirectedGraph<Int>().apply {
            for (i in 1..34) {
                addVertex(i)
            }

            val nodes = arrayListOf(adjList.keys.toList())

            addEdge(nodes[0][1], nodes[0][0])
            addEdge(nodes[0][2], nodes[0][0])
            addEdge(nodes[0][2], nodes[0][1])
            addEdge(nodes[0][3], nodes[0][0])
            addEdge(nodes[0][3], nodes[0][1])
            addEdge(nodes[0][3], nodes[0][2])
            addEdge(nodes[0][4], nodes[0][0])
            addEdge(nodes[0][5], nodes[0][0])
            addEdge(nodes[0][6], nodes[0][0])
            addEdge(nodes[0][6], nodes[0][4])
            addEdge(nodes[0][6], nodes[0][5])
            addEdge(nodes[0][7], nodes[0][0])
            addEdge(nodes[0][7], nodes[0][1])
            addEdge(nodes[0][7], nodes[0][2])
            addEdge(nodes[0][7], nodes[0][3])
            addEdge(nodes[0][8], nodes[0][1])
            addEdge(nodes[0][8], nodes[0][2])
            addEdge(nodes[0][9], nodes[0][2])
            addEdge(nodes[0][10], nodes[0][0])
            addEdge(nodes[0][10], nodes[0][4])
            addEdge(nodes[0][10], nodes[0][5])
            addEdge(nodes[0][11], nodes[0][0])
            addEdge(nodes[0][12], nodes[0][0])
            addEdge(nodes[0][12], nodes[0][3])
            addEdge(nodes[0][13], nodes[0][0])
            addEdge(nodes[0][13], nodes[0][1])
            addEdge(nodes[0][13], nodes[0][2])
            addEdge(nodes[0][13], nodes[0][3])
            addEdge(nodes[0][16], nodes[0][5])
            addEdge(nodes[0][16], nodes[0][6])
            addEdge(nodes[0][17], nodes[0][0])
            addEdge(nodes[0][17], nodes[0][1])
            addEdge(nodes[0][19], nodes[0][0])
            addEdge(nodes[0][19], nodes[0][1])
            addEdge(nodes[0][21], nodes[0][0])
            addEdge(nodes[0][21], nodes[0][1])
            addEdge(nodes[0][25], nodes[0][23])
            addEdge(nodes[0][25], nodes[0][24])
            addEdge(nodes[0][27], nodes[0][2])
            addEdge(nodes[0][27], nodes[0][23])
            addEdge(nodes[0][27], nodes[0][24])
            addEdge(nodes[0][28], nodes[0][2])
            addEdge(nodes[0][29], nodes[0][23])
            addEdge(nodes[0][29], nodes[0][26])
            addEdge(nodes[0][30], nodes[0][1])
            addEdge(nodes[0][30], nodes[0][8])
            addEdge(nodes[0][31], nodes[0][0])
            addEdge(nodes[0][31], nodes[0][24])
            addEdge(nodes[0][31], nodes[0][25])
            addEdge(nodes[0][31], nodes[0][28])
            addEdge(nodes[0][32], nodes[0][2])
            addEdge(nodes[0][32], nodes[0][8])
            addEdge(nodes[0][32], nodes[0][14])
            addEdge(nodes[0][32], nodes[0][15])
            addEdge(nodes[0][32], nodes[0][18])
            addEdge(nodes[0][32], nodes[0][20])
            addEdge(nodes[0][32], nodes[0][22])
            addEdge(nodes[0][32], nodes[0][23])
            addEdge(nodes[0][32], nodes[0][29])
            addEdge(nodes[0][32], nodes[0][30])
            addEdge(nodes[0][32], nodes[0][31])
            addEdge(nodes[0][33], nodes[0][8])
            addEdge(nodes[0][33], nodes[0][9])
            addEdge(nodes[0][33], nodes[0][13])
            addEdge(nodes[0][33], nodes[0][14])
            addEdge(nodes[0][33], nodes[0][15])
            addEdge(nodes[0][33], nodes[0][18])
            addEdge(nodes[0][33], nodes[0][19])
            addEdge(nodes[0][33], nodes[0][20])
            addEdge(nodes[0][33], nodes[0][22])
            addEdge(nodes[0][33], nodes[0][23])
            addEdge(nodes[0][33], nodes[0][26])
            addEdge(nodes[0][33], nodes[0][27])
            addEdge(nodes[0][33], nodes[0][28])
            addEdge(nodes[0][33], nodes[0][29])
            addEdge(nodes[0][33], nodes[0][30])
            addEdge(nodes[0][33], nodes[0][31])
            addEdge(nodes[0][33], nodes[0][32])
        }

        private val nodes = sampleGraph.adjList.keys.toList()

        @Test
        @DisplayName("Each node is assigned to its own community")
        fun testInitPartition1() {
            val expected: HashSet<HashSet<Vertex<Int>>> = hashSetOf()
            for (i in 0..33) {
                expected.add(hashSetOf(nodes[i]))
            }

            assertEquals(expected, CommunityDetector(sampleGraph, 1.0).initPartition(sampleGraph))
        }

        @Test
        @DisplayName("Communities become nodes in aggregate graph")
        fun aggregateGraphTest1() {
            val partition: HashSet<HashSet<Vertex<Int>>> = hashSetOf(hashSetOf(), hashSetOf())
            for (i in 0..33) {
                if (i <= 16) {
                    partition.first().add(nodes[i])
                } else {
                    if (partition.size == 1) {
                        partition.add(hashSetOf())
                    }

                    partition.last().add(nodes[i])
                }
            }

            val aggregatedGraph = CommunityDetector(sampleGraph, 1.0).aggregateGraph(sampleGraph, partition)
            val expectedVertices = listOf(Vertex(partition.first()), Vertex(partition.last()))
            val expectedEdges: HashSet<UnweightedEdge<HashSet<Vertex<Int>>>> = hashSetOf()

            expectedEdges.add(UnweightedEdge(expectedVertices[0], expectedVertices[0]))
            expectedEdges.add(UnweightedEdge(expectedVertices[0], expectedVertices[1]))
            expectedEdges.add(UnweightedEdge(expectedVertices[1], expectedVertices[0]))
            expectedEdges.add(UnweightedEdge(expectedVertices[1], expectedVertices[1]))
            expectedEdges.forEach { it.copies = 20 }
            expectedEdges.first().copies = 119
            expectedEdges.last().copies = 40

            assertEquals(expectedVertices, aggregatedGraph.vertices().toList() as List<Vertex<HashSet<Vertex<Int>>>>)
            assertEquals(expectedEdges, aggregatedGraph.edges() as HashSet<UnweightedEdge<HashSet<Vertex<Int>>>>)
        }

        @Test
        @DisplayName("flatVertex(vertex<Collection>) will unpack nested vertices properly")
        ///        ___________   vertex   ___________
        ///      /                  |                 \
        ///   ([1, 2, 3], [4, 5])   ([6], [7, 8])     ([9, 10])
        fun flatVertexTest1() {
            val vertex = Vertex(
                hashSetOf(
                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(1), Vertex(2), Vertex(3))),
                            Vertex(hashSetOf(Vertex(4), Vertex(5)))
                        )
                    ),

                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(6))),
                            Vertex(hashSetOf(Vertex(7), Vertex(8)))
                        )
                    ),

                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(9), Vertex(10)))
                        )
                    ),
                )
            )

            val expected = hashSetOf(
                Vertex(1),
                Vertex(2),
                Vertex(3),
                Vertex(4),
                Vertex(5),
                Vertex(6),
                Vertex(7),
                Vertex(8),
                Vertex(9),
                Vertex(10),
            )

            assertEquals(expected, CommunityDetector(sampleGraph, 1.0).flatVertex(vertex))
        }

        @Test
        @DisplayName("flatVertex(vertex) will return hashSet of given vertex if it's key is not a collection")
        fun flatVertexTest2() {
            val vertex = Vertex(42)

            assertEquals(hashSetOf(vertex), CommunityDetector(sampleGraph, 1.0).flatVertex(vertex))
        }

        @Test
        @DisplayName("flatCommunity(<Collection>) unpacks vertices properly")
        fun flatCommunityTest1() {
            val vertex1 = Vertex(
                hashSetOf(
                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(1), Vertex(2), Vertex(3))),
                            Vertex(hashSetOf(Vertex(4), Vertex(5)))
                        )
                    ),

                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(6))),
                            Vertex(hashSetOf(Vertex(7), Vertex(8)))
                        )
                    ),

                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(9), Vertex(10)))
                        )
                    ),
                )
            )

            val vertex2 = Vertex(
                hashSetOf(
                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(11), Vertex(12), Vertex(13))),
                            Vertex(hashSetOf(Vertex(14), Vertex(15)))
                        )
                    ),

                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(16))),
                            Vertex(hashSetOf(Vertex(17), Vertex(18)))
                        )
                    ),

                    Vertex(
                        hashSetOf(
                            Vertex(hashSetOf(Vertex(19), Vertex(20)))
                        )
                    ),
                )
            )

            val community = hashSetOf(vertex1, vertex2)

            val expected = hashSetOf(
                Vertex(1),
                Vertex(2),
                Vertex(3),
                Vertex(4),
                Vertex(5),
                Vertex(6),
                Vertex(7),
                Vertex(8),
                Vertex(9),
                Vertex(10),
                Vertex(11),
                Vertex(12),
                Vertex(13),
                Vertex(14),
                Vertex(15),
                Vertex(16),
                Vertex(17),
                Vertex(18),
                Vertex(19),
                Vertex(20),
            )

            assertEquals(expected, CommunityDetector(sampleGraph, 1.0).flatCommunity(community))
        }

        @Test
        @DisplayName("countEdges() counts edges inside the given community properly")
        fun countEdgesTest1() {
            val communty = hashSetOf(
                nodes[0],
                nodes[1],
                nodes[2],
                nodes[3]
            )

            assertEquals(6, CommunityDetector(sampleGraph, 1.0).countEdges(sampleGraph, communty, communty))
        }

        @Test
        @DisplayName("countEdges() counts neighbors of the given vertex inside a specific community properly")
        fun countEdgesTest2() {
            val communty = hashSetOf(
                nodes[0],
                nodes[1],
                nodes[2],
                nodes[3]
            )

            val vertex = nodes[0]

            assertEquals(
                3,
                CommunityDetector(sampleGraph, 1.0).countEdges(sampleGraph, hashSetOf(vertex), communty.minus(vertex))
            )
        }

        @Test
        @DisplayName("countEdges() handles multi-edges properly")
        fun countEdgesTest3() {
            val testGraph = UndirectedGraph<Int>()
            testGraph.addVertex(1)
            testGraph.addVertex(2)

            val community = testGraph.adjList.keys.toHashSet()

            for (i in 1..50) {
                testGraph.addEdge(testGraph.adjList.keys.first(), testGraph.adjList.keys.last())
            }

            assertEquals(50, CommunityDetector(testGraph, 1.0).countEdges(testGraph, community, community))
        }
    }
}