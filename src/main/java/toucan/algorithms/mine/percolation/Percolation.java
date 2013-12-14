package toucan.algorithms.mine.percolation;

import toucan.algorithms.mine.unionfind.UnionFind;
import toucan.algorithms.mine.unionfind.WeightedQuickUnion;

import java.util.BitSet;

/**
 * <a href="http://coursera.cs.princeton.edu/algs4/assignments/percolation.html">details</a>
 */
public class Percolation {
    public static final int ADDITIONAL_SITES_COUNT = 2;

    private final int size;
    private final int upperVirtualSite;
    private final int downVirtualSite;
    private final UnionFind percolationArray;
    private final UnionFind isFullArray;
    private final BitSet states;

    /**
     * create N-by-N grid, with all sites blocked
     *
     * @param size grid size
     */
    public Percolation(int size) {
        int sqSize = size * size;
        this.size = size;
        this.percolationArray = new WeightedQuickUnion(sqSize + ADDITIONAL_SITES_COUNT);
        this.isFullArray = new WeightedQuickUnion(sqSize + 1);
        this.states = new BitSet(sqSize);
        this.upperVirtualSite = sqSize;
        this.downVirtualSite = sqSize + 1;
    }

    /**
     * open site (row i, column j) if it is not already
     */
    public void open(int i, int j) {
        int pos = i + j * size;
        if (!states.get(pos)) {
            states.set(pos);
            join(pos, i - 1, j); //left
            join(pos, i + 1, j); //right
            join(pos, i, j - 1); //up
            join(pos, i, j + 1); //down
        }
    }

    private void join(int original, int i, int j) {
        if (i < 0 || i >= size)
            return;
        if (j < 0) {
            percolationArray.union(original, upperVirtualSite);
            isFullArray.union(original, upperVirtualSite);
        } else if (j >= size) {
            percolationArray.union(original, downVirtualSite);
        } else if (isOpen(i, j)) {
            percolationArray.union(original, i + j * size);
            isFullArray.union(original, i + j * size);
        }
    }

    /**
     * is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        return states.get(i + j * size);
    }

    /**
     * is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        return states.get(i + j * size) && isFullArray.connected(upperVirtualSite, i + j * size);
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return percolationArray.connected(upperVirtualSite, downVirtualSite);
    }
}
