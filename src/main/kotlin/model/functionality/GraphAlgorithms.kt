package model.functionality

enum class GraphAlgorithms(val string: String) {
    STRONG_CONNECTION_COMPONENTS("Find Strong Connection Components"),
    LAYOUT("ForceAtlas2"),
    COMMUNITIES("Find Communities"),
    BRIDGES("Find Bridges"),
    MINIMAL_SPANNING_TREE("Find Minimal Spanning Tree"),
    SHORTEST_DISTANCE("Find Shortest Distance")
}
