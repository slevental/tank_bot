package toucan.algorithms.mine.graphs;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * org.eslion.Graph implementation
 */
public class Graph {
    public static final TraversalStrategy DFS = new DepthFirstTraversal();
    public static final TraversalStrategy BFS = new BreadthFirstTraversal();

    private static final int MAX = 1000;

    private TraversalStrategy currentStrategy = BFS;

    private int nodesSize;
    private int edgesSize;
    private Entry[] nodes = new Entry[MAX];

    private int[] degrees = new int[MAX];

    private boolean directed = false;

    public void add(int x, int y, boolean directed) {
        this.directed |= directed;
        Entry node = new Entry();
        node.setWeight(0);
        node.setNext(nodes[x]);
        node.setY(y);
        nodes[x] = node;
        degrees[x]++;

        if (!directed) {
            add(y, x, true);
        } else {
            this.edgesSize++;
        }
    }

    public int edgesNum(int start) {
        final AtomicInteger counter = new AtomicInteger();
        currentStrategy.traverse(new TraversalHandlerAdapter<Node>() {
            public void processEdge(TraversalContext ctx, Node vertex1, Node vertex2) {
                counter.incrementAndGet();
            }
        }, nodes, NodeUtils.wrap(start), directed);
        return counter.get();
    }

    public boolean hasCycles() {
        final AtomicBoolean b = new AtomicBoolean(false);
        DFS.traverse(new TraversalHandlerAdapter<Node>() {
            @Override
            public void processEdge(TraversalContext ctx, Node vertex1, Node vertex2) {
                if (ctx.getParentCtx()[vertex2.getIndex()] != vertex1.getIndex()){
                    b.set(true);
                }
            }
        }, nodes, getAnyNode(), directed);
        return b.get();
    }

    private Node getAnyNode() {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                return nodes[i];
            }
        }
        return null;
    }

    public boolean contains(int x, int y) {
        return find(nodes[x], y);
    }

    private boolean find(Entry node, int y) {
        return node != null && (node.getY() == y || find(node.getNext(), y));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("org.eslion.Graph {\n");
        for (int i = 0; i < MAX; i++) {
            Entry n = nodes[i];
            while (n != null) {
                sb.append('\t').append(i).append(" -> ").append(n.getY()).append('\n');
                n = n.getNext();
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public void setStrategy(TraversalStrategy currentStrategy) {
        this.currentStrategy = currentStrategy;
    }

    public void delete(int x, int y) {
        Entry node = nodes[x];
        Entry previous = null;
        while (node != null && node.getY() != y) {
            previous = node;
            node = node.getNext();
        }
        if (node == null) {
            return; // no such edge
        }
        if (previous == null) {
            nodes[x] = node.getNext();
            return; // first edge
        }
        previous.setNext(node.getNext());
        if (!directed) {
            delete(y, x);
        }
    }

    private static class Entry implements Node {
        private int y;
        private int weight;
        private Entry next;

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getIndex() {
            return getY();
        }

        public Entry getNext() {
            return next;
        }

        public void setNext(Entry next) {
            this.next = next;
        }
    }
}
