package model.graphs

abstract class AbstractGraph<T> : Graph<T> {
    var adjList: HashMap<Vertex<T>, HashSet<Edge<T>>> = HashMap()
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

    override fun vertices(): Set<Vertex<T>> {
        return adjList.keys
    }

    override fun getNeighbors(vertex: Vertex<T>): HashSet<Vertex<T>> {
        val output = HashSet<Vertex<T>>()

        for (edge in adjList[vertex] ?: HashSet()) {
            output.add(edge.to)
        }

        return output
    }
}