package viewmodel.graphs

interface RepresentationStrategy {
	fun <T> place(width: Double, height: Double, vertices: Collection<VertexViewModel<T>>)
	fun <T> highlight(vertices: Collection<VertexViewModel<T>>)
}