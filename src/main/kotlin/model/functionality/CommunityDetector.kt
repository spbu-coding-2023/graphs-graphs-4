package model.functionality

import model.graphs.GraphUndirected
import model.graphs.UndirectedGraph
import model.graphs.UnweightedEdge
import model.graphs.Vertex
import java.lang.Math.random
import kotlin.math.exp
import kotlin.math.pow

class CommunityDetector<T>(
    var graph: GraphUndirected<T>,
    private var resolution: Double,
    private var randomness: Double
) {
    internal fun <K> flatten(partition: HashSet<HashSet<Vertex<K>>>): HashSet<HashSet<Vertex<T>>> {
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
        val newPartition: MutableList<HashSet<Vertex<T>>> = MutableList(partition.size) { hashSetOf() }

        for (vertex in currGraph.vertices()) {
            val index = partition.indexOf(partition.find { it.containsAll(vertex.key as HashSet<*>) })
            newPartition[index].add(vertex)
        }

        return newPartition.toHashSet()
    }


    fun leiden(): HashSet<HashSet<Vertex<T>>> {
        // currentGraph is used because original graph should remain unchanged
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
        }

        return flatten(partition)
    }

    private fun moveNodesFast(graph: GraphUndirected<T>, partition: HashSet<HashSet<Vertex<T>>>) {
        val vertexQueue = graph.vertices().toMutableList()
        vertexQueue.shuffle()

        while (vertexQueue.isNotEmpty()) {
            val currentVertex = vertexQueue.first()
            vertexQueue.remove(currentVertex)

            val startingQuality = quality(graph, partition)
            var max = 0.0
            var bestCommunity = partition.find { it.contains(currentVertex) }
            val originalCommunity = bestCommunity

            if (bestCommunity != null) {
                bestCommunity.remove(currentVertex)

                partition.add(hashSetOf())

                // Determine the best community for currentVertex

                for (community in partition) {
                    community.add(currentVertex)

                    val currentQuality = quality(graph, partition)
                    community.remove(currentVertex)

                    if (currentQuality - startingQuality >= max) {
                        max = currentQuality - startingQuality
                        bestCommunity = community
                    }
                }

                bestCommunity?.add(currentVertex)

                if (bestCommunity != originalCommunity) {
                    for (edge in graph.getNeighbors(currentVertex)) {
                        if (bestCommunity?.contains(edge.to) == false) {
                            vertexQueue.add(edge.to)
                        }
                    }
                }
            } else throw IllegalArgumentException("Community that contains currentVertex must exist.")
        }

        partition.removeIf { it.size == 0 }
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
            if (community.size != 0) {
                newGraph.addVertex(community)
            }
        }

        val communities = newGraph.vertices()

        for (edge in graph.edges()) {
            val v1 = edge.from
            val v2 = edge.to

            val c1 = communities.find { it.key.contains(v1) }
            val c2 = communities.find { it.key.contains(v2) }

            if (c1 != null && c2 != null) {
                newGraph.addSingleEdge(UnweightedEdge(c1, c2))
            }
        }

        // ANY UndirectedGraph is GraphUndirected
        @Suppress("UNCHECKED_CAST")
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

        refinedPartition.removeAll { it.size == 0 }

        return refinedPartition
    }

    internal fun <E> flatVertex(vertex: Vertex<E>): HashSet<Vertex<T>> {
        if (vertex.key is Collection<*>) {
            @Suppress("UNCHECKED_CAST")
            return unpack(hashSetOf(), vertex as Vertex<Collection<*>>)
        }

        @Suppress("UNCHECKED_CAST")
        return hashSetOf(vertex) as HashSet<Vertex<T>>
    }

    private fun unpack(vertices: HashSet<Vertex<T>>, vertex: Vertex<Collection<*>>): HashSet<Vertex<T>> {
        for (element in vertex.key) {
            element as Vertex<*>
            if (element.key is Collection<*>) {
                @Suppress("UNCHECKED_CAST")
                unpack(vertices, element as Vertex<Collection<*>>)
            } else {
                @Suppress("UNCHECKED_CAST")
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
        partition: HashSet<HashSet<Vertex<T>>>,
        subset: HashSet<Vertex<T>>
    ): HashSet<HashSet<Vertex<T>>> {
        // Consider only nodes that are well-connected within subset
        val r: MutableList<Vertex<T>> = mutableListOf()

        for (vertex in subset) {
            val vertexSize: Double = flatVertex(vertex).size.toDouble()

            var edges = 0
            graph.getNeighbors(vertex).forEach {
                if (subset.contains(it.to)) {
                    edges += 1
                }
            }

            if (edges >= (resolution * vertexSize * (flatCommunity(subset).size - vertexSize))) {
                r.add(vertex)
            }
        }

        for (vertex in r.shuffled()) {
            // Consider only nodes that have not yet been merged
            val originalCommunity = partition.find { it.contains(vertex) }

            if (originalCommunity?.size == 1) {
                // Consider only well-connected communities
                val wellConnectedCommunities: HashSet<HashSet<Vertex<T>>> = hashSetOf()

                for (community in partition) {
                    if (subset.containsAll(community)) {
                        val communitySize = flatCommunity(community).size
                        val edges = countEdges(graph, community, subset.minus(community))

                        if (edges >= (resolution * communitySize * (flatCommunity(subset).size) - communitySize)) {
                            wellConnectedCommunities.add(community)
                        }
                    }
                }

                originalCommunity.remove(vertex)

                val qualityProbability: HashMap<HashSet<Vertex<T>>, Double> = hashMapOf()
                val startingQuality = quality(graph, partition)
                val temp: HashSet<HashSet<Vertex<T>>> = HashSet()

                for (community in wellConnectedCommunities) {
                    // вообще проверка на ноль - костыль?
                    // original community скорее всего единственное 0-ое коммьюнити
                    // цель: случайно не вернуть нод в одиночное коммьюнити
                    if (community.size != 0) {
                        community.add(vertex)

                        val currentQuality = quality(graph, partition)

                        if (currentQuality - startingQuality < 0) {
                            temp.add(community.minus(vertex).toHashSet())
                        } else {
                            qualityProbability[community.minus(vertex).toHashSet()] =
                                exp((currentQuality - startingQuality) * (randomness.pow(-1.0)))
                        }

                        community.remove(vertex)
                    }
                }

                wellConnectedCommunities.removeAll(temp)
                wellConnectedCommunities.removeIf { it.size == 0 }

                if (wellConnectedCommunities.size != 0) {
                    // Choose random community for more broad exploration of possible partitions

                    var totalWeight = 0.0

                    for (community in wellConnectedCommunities) {
                        val x = qualityProbability[community]

                        if (x != null) {
                            totalWeight += x
                        } else throw Exception("failed miserably")
                    }

                    val randomNumber = random() * totalWeight
                    val keyList = qualityProbability.values.filter { it < randomNumber }
                    val key = keyList.maxOrNull() ?: qualityProbability.values.min()
                    val newCommunity = qualityProbability.entries.find { it.value == key }?.key

                    if (newCommunity != null) {
                        partition.find { it == newCommunity }?.add(vertex)
                    } else throw Exception("failed to assign newCommunity")
                } else {
                    originalCommunity.add(vertex)
                }
            }
        }

        return partition
    }

    internal fun initPartition(graph: GraphUndirected<T>): HashSet<HashSet<Vertex<T>>> {
        return graph.vertices().map { hashSetOf(it) }.toHashSet()
    }
}
