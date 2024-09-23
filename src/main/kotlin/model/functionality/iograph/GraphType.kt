package model.functionality.iograph

enum class GraphType(val type: String) {
    UNDIRECTED_GRAPH("UndirectedGraph"),
    DIRECTED_GRAPH("DirectedGraph"),
    UNDIRECTED_WEIGHTED_GRAPH("UndirectedWeightedGraph"),
    DIRECTED_WEIGHTED_GRAPH("DirectedWeightedGraph"),
}
