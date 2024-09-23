package model.graphs

abstract class AbstractGraph<T, E: Edge<T>> : Graph<T, E> {
    var adjList: HashMap<Vertex<T>, HashSet<E>> = HashMap()
        internal set

    protected var _size: Int = 0
    override val size: Int
        get() = _size

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

    fun addEdges(vararg edges: E) {
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