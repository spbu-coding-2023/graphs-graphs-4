package model.graphs

interface Graph<T> : Iterable<Vertex<T>> {
    val size: Int

    fun addVertex(key: T): Vertex<T>

    fun addVertex(vertex: Vertex<T>): Vertex<T>

    fun addVertices(vararg keys: T)

    fun addVertices(vararg vertices: Vertex<T>)

    fun vertices(): Set<Vertex<T>>

    fun edges(): Set<Edge<T>>

    fun findBridges(): Set<Pair<Vertex<T>, Vertex<T>>>

//    fun findSCC(): Set<Set<Vertex<T>>>
//    fun findDistancesBellman(start: Vertex<T>): Map<Vertex<T>, Double> {
//        val output = ShortestPathFinder(this).bellmanFord(start)
//        return output
//    }

//    fun findMinSpanTree(): Set<Edge<T>>?
//    fun findDistancesDijkstra(start: Vertex<T>): Map<Vertex<T>, Double> {
//        return ShortestPathFinder(this).dijkstra(start)
//    }

    override fun iterator(): Iterator<Vertex<T>> {
        return this.vertices().iterator()
    }

    fun getNeighbors(vertex: Vertex<T>): HashSet<Edge<T>>
}
