package model.graphs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
abstract class AbstractGraph<T, E: Edge<T>> : Graph<T, E> {
    @SerialName("graph")
    var adjList: HashMap<Vertex<T>, HashSet<E>> = HashMap()
        internal set

    @SerialName("size")
    protected var _size: Int = 0
    override val size: Int
        get() = _size

    override fun areConnected(u: Vertex<T>, v: Vertex<T>): Boolean {
        return (adjList[u]?.any { it.contains(v) } ?: false)
            || (adjList[v]?.any { it.contains(u) } ?: false)
    }

    override fun addVertex(key: T): Vertex<T> {
        for (v in adjList.keys) {
            if (v.key == key) {
                return v
            }
        }

        val vertex = Vertex(key)
        adjList[vertex] = HashSet()

        _size += 1

        return vertex
    }

    override fun addVertex(vertex: Vertex<T>): Vertex<T> {
        if (adjList.containsKey(vertex)) {
            return vertex
        }

        adjList[vertex] = HashSet()

        _size += 1

        return vertex
    }

    override fun addVertices(vararg keys: T) {
        for (key in keys) {
            addVertex(key)
        }
    }

    override fun addVertices(vararg vertices: Vertex<T>) {
        for (vertex in vertices) {
            addVertex(vertex)
        }
    }

    override fun addEdges(vararg edges: E) {
        for (edge in edges) {
            this.addEdge(edge)
        }
    }

    override fun addEdge(edge: E) {
        for (vertex in adjList.keys) {
            if (edge.from == vertex) {
                adjList[vertex]?.add(edge)
            }
        }
    }

    override fun vertices(): Set<Vertex<T>> {
        return adjList.keys
    }

    override fun getNeighbors(vertex: Vertex<T>): HashSet<E> {
        return adjList[vertex] ?: HashSet()
    }

    override fun edges(): Set<E> {
        val edges = HashSet<E>()
        for (vertex in adjList.keys) {
            for (edge in adjList[vertex] ?: HashSet()) {
                edges.add(edge)
            }
        }

        return edges
    }
}
