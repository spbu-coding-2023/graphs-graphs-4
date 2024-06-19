package model.graphs

interface Graph<T> : Iterable<Vertex<T>> {
    val size: Int

    fun addVertex(key: T): Vertex<T>

    fun addVertex(vertex: Vertex<T>): Vertex<T>

    fun addVertices(vararg keys: T)

    fun addVertices(vararg vertices: Vertex<T>)

    fun vertices(): Set<Vertex<T>>

    fun edges(): Set<Edge<T>>

    override fun iterator(): Iterator<Vertex<T>> {
        return this.vertices().iterator()
    }

    fun getNeighbors(vertex: Vertex<T>): HashSet<Edge<T>>

//    fun cyclesForVertex(vertex: Vertex<T>): HashSet<List<Vertex<T>>> {
//        return JohnsonAlg(this).findCycles(vertex)
//    }

//    fun findDistancesBellman(start: Vertex<T>): Map<Vertex<T>, Double> {
//        val output = ShortestPathFinder(this).bellmanFord(start)
//        return output
//    }

//    fun findDistancesDijkstra(start: Vertex<T>): Map<Vertex<T>, Double> {
//      return ShortestPathFinder(this).dijkstra(start)
//    }
}