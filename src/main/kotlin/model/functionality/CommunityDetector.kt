package model.functionality

import model.graphs.GraphUndirected
import model.graphs.Vertex

class CommunityDetector<T>(var graph: GraphUndirected<T>, var y: Double) {
    // для невзвшененных графов

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


    // помни сделать amend когда допишешь

    // сделать vert() тоже хэшсетом?
    private fun moveNodesFast(
        graph: GraphUndirected<T>,
        partition: HashSet<HashSet<Vertex<T>>>
    ): HashSet<HashSet<Vertex<T>>> {
        val currentQuality = quality(graph, partition)
        var max = currentQuality
        var betterPartition = partition

        for (vertex in graph.vertices()) {
            val tempPartition: HashSet<HashSet<Vertex<T>>> = partition
            tempPartition.find { it.contains(vertex) }?.remove(vertex)

            for (community in tempPartition) {
                community.add(vertex)

                val newQuality = quality(graph, tempPartition)

                if (newQuality > max) {
                    max = newQuality
                    betterPartition = tempPartition
                }

                community.remove(vertex)
            }

            if (max > 0) {
                return betterPartition
            }
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
        TODO()
    }

    private fun mergeNodesSubset() {
        TODO()
    }

    private fun initPartition() {
        TODO()
    }
}