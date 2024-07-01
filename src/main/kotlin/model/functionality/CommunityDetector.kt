package model.functionality

import model.graphs.GraphUndirected
import model.graphs.UndirectedGraph
import model.graphs.Vertex

class CommunityDetector<T>(var graph: GraphUndirected<T>, var y: Double) {
    /// еще ОЧЕНЬ наверное стоит дробить T пока не избавимся от сета


    private fun flatten(partition: HashSet<HashSet<Vertex<T>>>): HashSet<HashSet<*>> {
        val output = HashSet<HashSet<*>>()

        for (community in partition) {
            output.add(flatCommunity(community))
        }

        return output
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

    private fun countEdges(graph: GraphUndirected<T>, set1: HashSet<Vertex<T>>, set2: Set<Vertex<T>>): Int {
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
        partition: HashSet<HashSet<Vertex<T>>>
    ): GraphUndirected<T> {

        val newGraph = UndirectedGraph<HashSet<Vertex<T>>>()

        for (community in partition) {
            newGraph.addVertex(community)
        }

        val communities = newGraph.vertices()

        for (edge in graph.edges()) {
            val v1 = edge.from
            val v2 = edge.to

            val c1 = communities.find { it.key.contains(v1) }
            val c2 = communities.find { it.key.contains(v2) }

            if (c1 != null && c2 != null) {
                newGraph.addEdge(c1, c2)
            } else throw IllegalArgumentException("idk something weird just happened")
        }

        return newGraph as GraphUndirected<T>
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

    private fun countEdgesInside(graph: GraphUndirected<T>, set: HashSet<Vertex<T>>, vertex: Vertex<T>): Int {
        var count = 0

        for (neighbor in graph.getNeighbors(vertex)) {
            if (set.contains(neighbor.to)) {
                count += 1
            }
        }

        return count
    }

    private fun flatVertex(vertex: Vertex<T>): HashSet<*> {
        if (vertex.key is HashSet<*>) {
            return vertex.key
        } else {
            return hashSetOf(vertex)
        }
    }

    private fun flatCommunity(community: HashSet<Vertex<T>>): HashSet<*> {
        val output: HashSet<Vertex<T>> = hashSetOf()

        for (vertex in community) {
            for (element in flatVertex(vertex)) {
                output.add(element as Vertex<T>)
            }
        }

        return output
    }

    private fun mergeNodesSubset(
        graph: GraphUndirected<T>,
        refinedPartition: HashSet<HashSet<Vertex<T>>>,
        community: HashSet<Vertex<T>>
    ): HashSet<HashSet<Vertex<T>>> {
        val r: HashSet<Vertex<T>> = hashSetOf()

        for (vertex in community) {
            val vertexSize = flatVertex(vertex).size

            if (countEdgesInside(
                    graph,
                    community,
                    vertex
                ) >= (y * vertexSize * (flatCommunity(community).size - vertexSize))
            ) {
                r.add(vertex)
            }
        }

        for (vertex in r) {
            if (refinedPartition.find { it.contains(vertex) }?.size == 1) {
                val wellConnectedCommunities = hashSetOf<HashSet<Vertex<T>>>()

                // мб функцию ?
                for (element in refinedPartition) {
                    if (flatCommunity(element).all { community.contains(it) }) {
                        if (countEdges(
                                graph,
                                element,
                                community.minus(element)
                            ) > (y * flatCommunity(element).size * (flatCommunity(community).size - flatCommunity(
                                element
                            ).size))
                        ) {
                            wellConnectedCommunities.add(element)
                        }
                    }
                }

                // была какая-то формула но написано вернуть случайное
                // вообще по хорошему надо проверять как изменилось качество
                val newCommunity = refinedPartition.random()

                refinedPartition.find { it.contains(vertex) }?.remove(vertex)

                newCommunity.add(vertex)
            }
        }

        return refinedPartition
    }

    private fun initPartition(graph: GraphUndirected<T>): HashSet<HashSet<Vertex<T>>> {
        val partition: HashSet<HashSet<Vertex<T>>> = hashSetOf()

        for (vertex in graph.vertices()) {
            partition.add(hashSetOf(vertex))
        }

        return partition
    }
}