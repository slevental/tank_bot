package toucan.algorithms.mine.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Traversal strategy
 */
public class BreadthFirstTraversal implements TraversalStrategy {
    public void traverse(TraversalHandler<Node> traversalHandler, Node[] graph, Node start, boolean directed) {
        TraversalContext ctx = TraversalContext.creteBFSContext(graph.length);
        final Queue<Node> processing = new LinkedList<Node>();

        ctx.markDiscovered(start.getIndex());
        processing.add(start);
        while (!processing.isEmpty()) {
            Node p = processing.poll();
            traversalHandler.beforeVertex(ctx, p);
            Node edges = graph[p.getIndex()];
            while (edges != null) {
                int y = edges.getIndex();
                if (!ctx.isProcessed(y) || directed) {
                    traversalHandler.processEdge(ctx, p, edges);
                }
                if (!ctx.isDiscovered(y)) {
                    processing.add(edges);
                    ctx.markDiscovered(y);
                    ctx.setParent(y, p.getIndex());
                }
                edges = edges.getNext();
            }
            traversalHandler.afterVertex(ctx, p);
        }
    }
}
