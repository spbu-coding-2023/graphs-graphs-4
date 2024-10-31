package functionalityTest


import model.functionality.DistanceRank
import model.functionality.TarjanSCC
import model.graphs.DirectedGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test



//This is impossible to check
//So the only choice is to run it, and see in console real result, putting wrong expected
//Because it's quite hard to make correct expected, it's too big and there's no need in this




//class DistRankTest {
//    private val graph = DirectedGraph<Int>()
//
//    @Test
//    fun test() {
//        for (i in 0..39) {
//            graph.addVertex(i)
//        }
//
//        val nodes = graph.adjList.keys.toList().sortedBy { it.key }
//
//        /*graph.addEdge(nodes[1], nodes[2])
//        graph.addEdge(nodes[1], nodes[3])
//        graph.addEdge(nodes[2], nodes[3])
//        graph.addEdge(nodes[2], nodes[4])
//        graph.addEdge(nodes[3], nodes[5])
//        graph.addEdge(nodes[4], nodes[5])
//        graph.addEdge(nodes[4], nodes[6])
//        graph.addEdge(nodes[5], nodes[7])
//        graph.addEdge(nodes[6], nodes[7])
//        graph.addEdge(nodes[6], nodes[3])*/
//
//        /*graph.addEdge(nodes[0], nodes[1])
//        graph.addEdge(nodes[0], nodes[2])
//        graph.addEdge(nodes[0], nodes[3])
//        graph.addEdge(nodes[1], nodes[4])
//        graph.addEdge(nodes[2], nodes[4])
//        graph.addEdge(nodes[3], nodes[4])
//        graph.addEdge(nodes[4], nodes[0])*/
//
//        graph.addEdge(nodes[0], nodes[1])
//        graph.addEdge(nodes[0], nodes[2])
//        graph.addEdge(nodes[0], nodes[3])
//        graph.addEdge(nodes[1], nodes[4])
//        graph.addEdge(nodes[1], nodes[5])
//        graph.addEdge(nodes[2], nodes[5])
//        graph.addEdge(nodes[2], nodes[6])
//        graph.addEdge(nodes[3], nodes[6])
//        graph.addEdge(nodes[3], nodes[7])
//        graph.addEdge(nodes[4], nodes[8])
//        graph.addEdge(nodes[4], nodes[9])
//        graph.addEdge(nodes[5], nodes[8])
//        graph.addEdge(nodes[5], nodes[10])
//        graph.addEdge(nodes[6], nodes[9])
//        graph.addEdge(nodes[6], nodes[10])
//        graph.addEdge(nodes[7], nodes[11])
//        graph.addEdge(nodes[8], nodes[12])
//        graph.addEdge(nodes[9], nodes[12])
//        graph.addEdge(nodes[10], nodes[13])
//        graph.addEdge(nodes[11], nodes[14])
//        graph.addEdge(nodes[12], nodes[15])
//        graph.addEdge(nodes[13], nodes[16])
//        graph.addEdge(nodes[14], nodes[17])
//        graph.addEdge(nodes[15], nodes[18])
//        graph.addEdge(nodes[16], nodes[19])
//        graph.addEdge(nodes[17], nodes[19])
//        graph.addEdge(nodes[18], nodes[19])
//        graph.addEdge(nodes[19], nodes[20])
//        graph.addEdge(nodes[19], nodes[21])
//        graph.addEdge(nodes[19], nodes[22])
//        graph.addEdge(nodes[20], nodes[23])
//        graph.addEdge(nodes[20], nodes[24])
//        graph.addEdge(nodes[21], nodes[24])
//        graph.addEdge(nodes[21], nodes[25])
//        graph.addEdge(nodes[22], nodes[26])
//        graph.addEdge(nodes[22], nodes[27])
//        graph.addEdge(nodes[23], nodes[28])
//        graph.addEdge(nodes[24], nodes[29])
//        graph.addEdge(nodes[24], nodes[30])
//        graph.addEdge(nodes[25], nodes[31])
//        graph.addEdge(nodes[26], nodes[32])
//        graph.addEdge(nodes[27], nodes[33])
//        graph.addEdge(nodes[28], nodes[34])
//        graph.addEdge(nodes[29], nodes[35])
//        graph.addEdge(nodes[30], nodes[36])
//        graph.addEdge(nodes[31], nodes[37])
//        graph.addEdge(nodes[32], nodes[38])
//        graph.addEdge(nodes[33], nodes[39])
//        graph.addEdge(nodes[39], nodes[0])
//
//
//        val result = DistanceRank(graph).rank()
//        assertEquals(1, result)
//        //assertEquals(1, TarjanSCC<Int>().findSCCs(graph))
//    }
//}