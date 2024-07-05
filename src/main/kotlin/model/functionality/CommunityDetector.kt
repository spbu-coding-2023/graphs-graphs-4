package model.functionality

import model.graphs.GraphUndirected
import model.graphs.UndirectedGraph
import model.graphs.Vertex

class CommunityDetector<T>(var graph: GraphUndirected<T>, var y: Double) {
    private fun flatten(partition: HashSet<HashSet<Vertex<T>>>): HashSet<HashSet<Vertex<T>>> {
        val output = HashSet<HashSet<Vertex<T>>>()

        println(partition)

        for (community in partition) {
            output.add(flatCommunity(community))
        }

        return output
    }

    fun leiden(): HashSet<HashSet<Vertex<T>>> {
        var graphCurr = graph
        var partition: HashSet<HashSet<Vertex<T>>> = initPartition(graphCurr)
        var done = false

        while (!done) {
            partition = moveNodesFast(graphCurr, partition)
            done = ((partition.size) == (graphCurr.vertices().size))

            if (partition.size != graphCurr.vertices().size) {
                val refinedPartition = refinePartition(graphCurr, partition)

                val temp: HashSet<HashSet<Vertex<T>>> = hashSetOf()
                graphCurr = aggregateGraph(graphCurr, refinedPartition)

                for (community in partition) {
                    val tempCommunity: HashSet<Vertex<T>> = hashSetOf()

                    for (vertex in community) {
                        if (graphCurr.vertices().contains(vertex)) {
                            tempCommunity.add(vertex)
                        }
                    }

                    temp.add(tempCommunity)
                }

                partition = temp
            }
        }

        return flatten(partition)
    }

    private fun moveNodesFast(
        graph: GraphUndirected<T>,
        partition: HashSet<HashSet<Vertex<T>>>
    ): HashSet<HashSet<Vertex<T>>> {
        val vertexQueue = graph.vertices().toMutableList()
        vertexQueue.shuffle()

        for (vertex in vertexQueue) {
            val currentQuality = quality(graph, partition)
            var max = currentQuality
            var bestCommunity = partition.find { it.contains(vertex) }

            if (bestCommunity != null) {
                bestCommunity.remove(vertex)

                for (community in partition) {
                    community.add(vertex)

                    val tempQuality = quality(graph, partition)

                    if (tempQuality > max) {
                        max = tempQuality
                        bestCommunity = community
                    }

                    community.remove(vertex)
                }

                bestCommunity?.add(vertex)

                if (bestCommunity?.size == 0) {
                    partition.remove(bestCommunity)
                }
            }
        }

        return partition
    }

    private fun quality(graph: GraphUndirected<T>, partition: HashSet<HashSet<Vertex<T>>>): Double {
        var sum = 0.0

        for (community in partition) {
            val cS = flatCommunity(community).size
            sum += countEdges(graph, community, community) - ((y * cS * (cS - 1)) / 2)
        }

        return sum
    }

    private fun countEdges(currGraph: GraphUndirected<T>, set1: HashSet<Vertex<T>>, set2: Set<Vertex<T>>): Int {
        var count = 0

        for (u in set1) {
            for (v in currGraph.getNeighbors(u)) {
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

    internal fun aggregateGraph(
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
            }
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

    private fun <E> flatCommunity(community: HashSet<Vertex<E>>): HashSet<Vertex<T>> {
        val output: HashSet<Vertex<T>> = hashSetOf()

        for (vertex in community) {
            output.addAll(flatVertex(vertex))
        }

        return output
    }

    private fun unpack(vertices: HashSet<Vertex<T>>, vertex: Vertex<Collection<*>>): HashSet<Vertex<T>> {
        for (element in vertex.key) {
            element as Vertex<*>
            if (element.key is Collection<*>) {
                unpack(vertices, element as Vertex<Collection<*>>)
            } else {
                vertices.add(element as Vertex<T>)
            }
        }

        return vertices
    }

    internal fun <E> flatVertex(vertex: Vertex<E>): HashSet<Vertex<T>> {
        if (vertex.key is Collection<*>) {
            return unpack(hashSetOf(), vertex as Vertex<Collection<*>>)
        }

        return hashSetOf(vertex) as HashSet<Vertex<T>>
    }

    private fun mergeNodesSubset(
        graph: GraphUndirected<T>,
        refinedPartition: HashSet<HashSet<Vertex<T>>>,
        community: HashSet<Vertex<T>>
    ): HashSet<HashSet<Vertex<T>>> {
        val r: HashSet<Vertex<T>> = hashSetOf()

        for (vertex in community) {
            val vertexSize: Double = flatVertex(vertex).size.toDouble()

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
            val currentCommunity = refinedPartition.find { it.contains(vertex) }

            if (currentCommunity?.size == 1) {
                val wellConnectedCommunities = refinedPartition.filter { it.all { v -> community.contains(v) } }
                    .filter { countEdges(graph, it, community.minus(it)) > (y * it.size * (community.size - it.size)) }
                    .toHashSet()

                val newCommunity = wellConnectedCommunities.randomOrNull() ?: refinedPartition.random()

                currentCommunity.remove(vertex)
                newCommunity.add(vertex)
            }
        }

        return refinedPartition
    }

    internal fun initPartition(graph: GraphUndirected<T>): HashSet<HashSet<Vertex<T>>> {
        return graph.vertices().map { hashSetOf(it) }.toHashSet()
    }
}