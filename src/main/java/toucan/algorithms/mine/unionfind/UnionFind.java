package toucan.algorithms.mine.unionfind;

/**
 * Contains methods describes union-find
 * functionality, look at <a href="http://www.algorithmist.com/index.php/Union_Find">details</a>
 */
public interface UnionFind {
    /**
     * creates an edge between v and y
     *
     * @param v element
     * @param y element
     */
    void union(int v, int y);

    /**
     * returns a true if there is a link between
     * two provided elements
     *
     * @param v element
     * @param y element
     * @return true if link exists
     */
    boolean connected(int v, int y);
}
