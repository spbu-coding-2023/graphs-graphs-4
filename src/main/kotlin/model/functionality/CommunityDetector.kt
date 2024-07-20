package model.functionality

import model.graphs.GraphUndirected
import model.graphs.UndirectedGraph
import model.graphs.Vertex

class CommunityDetector<T>(var graph: GraphUndirected<T>, var resolution: Double) {
    private fun flatten(partition: HashSet<HashSet<Vertex<T>>>): HashSet<HashSet<Vertex<T>>> {
        val output = HashSet<HashSet<Vertex<T>>>()

        for (community in partition) {
            output.add(flatCommunity(community))
        }

        return output
    }


    private fun <E> maintainPartition(
        partition: List<HashSet<Vertex<E>>>,
        currGraph: GraphUndirected<T>
    ): HashSet<HashSet<Vertex<T>>> {
        // проверить создается ли то что надо
        var newPartition: MutableList<HashSet<Vertex<T>>> = MutableList(partition.size) { hashSetOf() }

        for (vertex in currGraph.vertices()) {
            val index = partition.indexOf(partition.find { it.containsAll(vertex.key as HashSet<Vertex<E>>) })
            newPartition[index].add(vertex)
        }

        return newPartition.toHashSet()
    }


    fun leiden(): HashSet<HashSet<Vertex<T>>> {
        // currentGraph is used because we don't want to change the original graph
        var currentGraph = graph
        var partition: HashSet<HashSet<Vertex<T>>> = initPartition(graph)
        var notDone = true

        while (notDone) {
            // проверить меняет ли функция разбиение
            moveNodesFast(currentGraph, partition)
            notDone = (partition.size) != (currentGraph.vertices().size)

            if (notDone) {
                val refinedPartition = refinePartition(currentGraph, partition)
                currentGraph = aggregateGraph(currentGraph, refinedPartition)
                partition = maintainPartition(partition.toList(), currentGraph)
            }

            println(partition)
        }

        return flatten(partition)
    }

    private fun moveNodesFast(
        graph: GraphUndirected<T>,
        partition: HashSet<HashSet<Vertex<T>>>
    ) {
        val vertexQueue = graph.vertices().toMutableList()
        vertexQueue.shuffle()

        while (vertexQueue.size > 0) {
            val vertex = vertexQueue.first()

            vertexQueue.remove(vertex)

            val currentQuality = quality(graph, partition)
            var max = currentQuality
            var bestCommunity = partition.find { it.contains(vertex) }

            if (bestCommunity != null) {
                bestCommunity.remove(vertex)

                // searching for the best community where to move vertex

                for (community in partition) {
                    community.add(vertex)

                    val tempQuality = quality(graph, partition)

                    if (tempQuality >= max) {
                        max = tempQuality
                        bestCommunity = community
                    }

                    community.remove(vertex)
                }

                bestCommunity?.add(vertex)
            }
        }
    }

    private fun quality(graph: GraphUndirected<T>, partition: HashSet<HashSet<Vertex<T>>>): Double {
        var sum = 0.0

        for (community in partition) {
            val cS = flatCommunity(community).size
            sum += countEdges(graph, community, community) - ((resolution * cS * (cS - 1)) / 2)
        }

        return sum
    }

    internal fun countEdges(currGraph: GraphUndirected<T>, set1: HashSet<Vertex<T>>, set2: Set<Vertex<T>>): Int {
        var count = 0

        for (u in set1) {
            for (v in currGraph.getNeighbors(u)) {
                if (v.to in set2) {
                    count += v.copies
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

    internal fun <E> flatVertex(vertex: Vertex<E>): HashSet<Vertex<T>> {
        if (vertex.key is Collection<*>) {
            return unpack(hashSetOf(), vertex as Vertex<Collection<*>>)
        }

        return hashSetOf(vertex) as HashSet<Vertex<T>>
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

    internal fun <E> flatCommunity(community: HashSet<Vertex<E>>): HashSet<Vertex<T>> {
        val output: HashSet<Vertex<T>> = hashSetOf()

        for (vertex in community) {
            output.addAll(flatVertex(vertex))
        }

        return output
    }

    private fun mergeNodesSubset(
        graph: GraphUndirected<T>,
        refinedPartition: HashSet<HashSet<Vertex<T>>>,
        subset: HashSet<Vertex<T>>
    ): HashSet<HashSet<Vertex<T>>> {
        val r: HashSet<Vertex<T>> = hashSetOf()

        // Consider only nodes that are well-connected within subset

        for (vertex in subset) {
            val vertexSize: Double = flatVertex(vertex).size.toDouble()
            val edges = countEdges(graph, hashSetOf(vertex), subset.minus(vertex))

            if (edges >= (resolution * vertexSize * (flatCommunity(subset).size - vertexSize))) {
                r.add(vertex)
            }
        }

        for (vertex in r.shuffled()) {
            val currentCommunity = refinedPartition.find { it.contains(vertex) }

            // Consider only nodes that have not yet been merged

            if (currentCommunity?.size == 1) {
                val wellConnectedCommunities: HashSet<HashSet<Vertex<T>>> = hashSetOf()

                //  Consider only well-connected communities

                for (community in refinedPartition) {
                    if (subset.containsAll(community)) {
                        val communitySize = flatCommunity(community).size

                        if (countEdges(
                                graph,
                                community,
                                subset.minus(community)
                            ) >= (resolution * communitySize * (flatCommunity(subset).size) - communitySize)
                        ) {
                            wellConnectedCommunities.add(community)
                        }
                    }
                }

                val communitiesToConsider: HashSet<HashSet<Vertex<T>>> = hashSetOf()

                currentCommunity.remove(vertex)

                for (community in wellConnectedCommunities) {
                    community.add(vertex)
                    if (quality(graph, refinedPartition) >= 0) {
                        communitiesToConsider.add(community.minus(vertex).toHashSet())
                    }
                    community.remove(vertex)
                }

                if (communitiesToConsider.isNotEmpty()) {
                    refinedPartition.find { it == communitiesToConsider.random() }?.add(vertex)
                } else {
                    currentCommunity.add(vertex)
                }
            }
        }

        /// randomness parameter
        //  Randomness in the selection of a community
        //allows the partition space to be explored more broadly.
        // надо позволить пользователю вводить рандомность и резолюшон при запуске

        return refinedPartition
    }

    internal fun initPartition(graph: GraphUndirected<T>): HashSet<HashSet<Vertex<T>>> {
        return graph.vertices().map { hashSetOf(it) }.toHashSet()
    }
}