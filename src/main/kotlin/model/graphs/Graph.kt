package model.graphs

interface Graph<T, E: Edge<T>> : Iterable<Vertex<T>> {
    val size: Int

    fun addVertex(key: T): Vertex<T>

    fun addVertex(vertex: Vertex<T>): Vertex<T>

    fun addVertices(vararg keys: T)

    fun addVertices(vararg vertices: Vertex<T>)

    fun addEdge(edge: E)

    fun addEdges(vararg edges: E)

    fun vertices(): Set<Vertex<T>>

    fun edges(): Set<E>

    fun areConnected(u: Vertex<T>, v: Vertex<T>): Boolean

    override fun iterator(): Iterator<Vertex<T>> {
        return this.vertices().iterator()
    }

    fun getNeighbors(vertex: Vertex<T>): HashSet<E>

//    fun cyclesForVertex(vertex: Vertex<T>): HashSet<List<Vertex<T>>> {
//        return JohnsonAlg(this).findCycles(vertex)
//    }
}
