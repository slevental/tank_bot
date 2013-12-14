package toucan.algorithms.mine.graphs;

/**
 * Adapter with empty methods
 */
public abstract class TraversalHandlerAdapter<E extends Node> implements TraversalHandler<E> {
    public void beforeVertex(TraversalContext ctx, E vertex) {
    }

    public void afterVertex(TraversalContext ctx, E vertex) {
    }

    public void processEdge(TraversalContext ctx, E vertex1, E vertex2) {
    }
}
