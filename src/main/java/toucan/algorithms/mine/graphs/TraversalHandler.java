package toucan.algorithms.mine.graphs;

public interface TraversalHandler<E extends Node> {
    void beforeVertex(TraversalContext ctx, E vertex);

    void afterVertex(TraversalContext ctx, E vertex);

    void processEdge(TraversalContext ctx, E vertex1, E vertex2);
}
