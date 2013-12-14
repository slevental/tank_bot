package toucan.algorithms.mine.unionfind;

/**
 * Contains O(1) find operation implementation
 * and O(N) for union
 */
public class QuickFind extends AbstractUnionFind{

    public QuickFind(int size) {
        super(size);
    }

    public void union(int v, int y) {
        checkBounds(v);
        checkBounds(y);
        int toChange = container[v];
        int target = container[y];
        for (int i = 0; i < container.length; i++) {
            if (container[i] == toChange)
                container[i] = target;
        }
    }

    public boolean connected(int v, int y) {
        checkBounds(v);
        checkBounds(y);
        return container[v] == container[y];
    }
}
