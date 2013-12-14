package toucan.algorithms.mine.unionfind;

/**
 * Implements union operation with O(N) and find operation with O(N)
 * complexities
 */
public class QuickUnion extends AbstractUnionFind{
    public QuickUnion(int size) {
        super(size);
    }

    public void union(int v, int y) {
        checkBounds(v);
        checkBounds(y);

        int rootV = getRoot(v);
        int rootY = getRoot(y);
        container[rootY] = rootV;
    }

    public boolean connected(int v, int y) {
        checkBounds(v);
        checkBounds(y);
        return getRoot(v) == getRoot(y);
    }

    protected int getRoot(int leaf){
        while ((leaf = container[leaf]) != container[leaf]);
        return leaf;
    }
}
