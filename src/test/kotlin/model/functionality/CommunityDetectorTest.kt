package model.functionality

import model.graphs.UndirectedGraph
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
    }
}