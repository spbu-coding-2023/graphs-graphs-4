package graphs

class Vertex<T>(
    val key: T,
    var sccIndex: Int = 0,
    var lowLink: Int = 0,
    var onStack: Boolean = false,
    ) {
	// don't forget getter / setter etc.
	// store coordinates here?
}
