package model.functionality

import model.graphs.GraphUndirected
import model.graphs.Vertex

class CommunityDetector<T>(var graph: GraphUndirected<T>, var y: Double) {
    private fun flatten(partition: HashSet<HashSet<Vertex<T>>>) {
        TODO()
    }

    fun leiden() {
        var partition: HashSet<HashSet<Vertex<T>>> = hashSetOf()

        while (partition.size != graph.vertices().size) {
            partition = moveNodesFast(graph, partition)

            if (partition.size != graph.vertices().size) {
                val refinedPartition = refinePartition(graph, partition)

                val temp: HashSet<HashSet<Vertex<T>>> = hashSetOf()
                graph = aggregateGraph(graph, refinedPartition)

                for (community in partition) {
                    val tempCommunity: HashSet<Vertex<T>> = hashSetOf()

                    for (vertex in community) {
                        if (graph.vertices().contains(vertex)) {
                            tempCommunity.add(vertex)
                        }
                    }

                    temp.add(tempCommunity)
                }

                partition = temp
            }
        }

        flatten(partition)
    }

    private fun moveNodesFast(
        graph: GraphUndirected<T>,
        partition: HashSet<HashSet<Vertex<T>>>
    ): HashSet<HashSet<Vertex<T>>> {

        for (vertex in graph.vertices()) {
            val currentQuality = quality(graph, partition)
            var bestCommunity = partition.find { it.contains(vertex) }
            var max = currentQuality

            bestCommunity?.remove(vertex)

            for (community in partition) {
                community.add(vertex)

                val tempQuality = quality(graph, partition)

                if (tempQuality > max) {
                    max = tempQuality
                    bestCommunity = community
                }
            }

            bestCommunity?.add(vertex)
        }

        return partition
    }

    private fun quality(graph: GraphUndirected<T>, partition: HashSet<HashSet<Vertex<T>>>): Double {
        var sum = 0.0

        for (community in partition) {
            sum += countEdges(graph, community, community) - ((y * community.size * (community.size - 1)) / 2)
        }

        return sum
    }

    private fun countEdges(graph: GraphUndirected<T>, set1: HashSet<Vertex<T>>, set2: HashSet<Vertex<T>>): Int {
        var count = 0

        for (u in set1) {
            for (v in graph.getNeighbors(u)) {
                if (v.to in set2) {
                    count += 1
                }
            }
        }

        if (set1 == set2) {
            count /= 2
        }

        return count
    }

    private fun aggregateGraph(
        graph: GraphUndirected<T>,
        refinedPartition: HashSet<HashSet<Vertex<T>>>
    ): GraphUndirected<T> {
        TODO()
    }

    private fun refinePartition(
        graph: GraphUndirected<T>,
        partition: HashSet<HashSet<Vertex<T>>>
    ): HashSet<HashSet<Vertex<T>>> {
        var refinedPartition = initPartition(graph)

        for (community in partition) {
            refinedPartition = mergeNodesSubset(graph, refinedPartition, community)
        }

        return refinedPartition
    }

    private fun mergeNodesSubset(
        graph: GraphUndirected<T>,
        refinedPartition: HashSet<HashSet<Vertex<T>>>,
        community: java.util.HashSet<Vertex<T>>
    ): HashSet<HashSet<Vertex<T>>> {


        TODO()
    }

    private fun initPartition(graph: GraphUndirected<T>): HashSet<HashSet<Vertex<T>>> {
        val partition: HashSet<HashSet<Vertex<T>>> = hashSetOf()

        for (vertex in graph.vertices()) {
            partition.add(hashSetOf(vertex))
        }

        return partition
    }
}