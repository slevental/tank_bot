package toucan.algorithms.mine.graphs;

/**
 */
public class DepthFirstTraversal implements TraversalStrategy {

    public void traverse(TraversalHandler<Node> handler, Node[] graph, Node start, boolean directed) {
        int len = graph.length;
        TraversalContext ctx = TraversalContext.createDFSContext(len);
        recursiveTraverse(handler, graph, start, directed, ctx);
    }

    private void recursiveTraverse(TraversalHandler<Node> handler, Node[] graph, Node current, boolean directed, TraversalContext ctx) {
        handler.beforeVertex(ctx, current);
        int currentIndex = current.getIndex();
        ctx.markDiscovered(currentIndex);
        ctx.updateStartTime(currentIndex);
        Node next = graph[currentIndex];
        while (next != null) {
            int nextIndex = next.getIndex();
            if (!ctx.isDiscovered(nextIndex)) {
                ctx.setParent(nextIndex, currentIndex);
                handler.processEdge(ctx, current, next);
                recursiveTraverse(handler, graph, next, directed, ctx);
            } else if (!ctx.isProcessed(nextIndex) || directed) {
                handler.processEdge(ctx, current, next);
            }
            next = next.getNext();
        }
        ctx.markProcessed(currentIndex);
        handler.afterVertex(ctx, current);
        ctx.updateEndTime(currentIndex);
    }
}