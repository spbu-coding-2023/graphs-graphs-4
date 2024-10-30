package model.graphs

import model.functionality.ShortestPathFinder

interface GraphWeighted<T> : Graph<T, WeightedEdge<T>> {
    fun findDistancesBellman(start: Vertex<T>): Map<Vertex<T>, Double> {
        val output = ShortestPathFinder(this).bellmanFord(start)
        return output
    }


    fun findDistancesDijkstra(start: Vertex<T>): Map<Vertex<T>, Double> {
      return ShortestPathFinder(this).dijkstra(start)
    }
}
