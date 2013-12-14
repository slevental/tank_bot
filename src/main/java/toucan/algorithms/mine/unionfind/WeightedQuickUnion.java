package toucan.algorithms.mine.unionfind;

import java.util.Arrays;

/**
 * Uses weight vector to define merge order
 */
public class WeightedQuickUnion extends QuickUnion {
    private final int[] size;

    public WeightedQuickUnion(int size) {
        super(size);
        this.size = new int[size];
        clear();
    }

    @Override
    protected void clear() {
        super.clear();
        if(this.size != null)
            Arrays.fill(this.size, 1);
    }

    @Override
    public void union(int v, int y) {
        int rootV = getRoot(v);
        int rootY = getRoot(y);
        if (size[rootV] >= size[rootY]){
            size[rootV] += size[rootY];
            container[rootY] = rootV;
        }else{
            size[rootY] += size[rootV];
            container[rootV] = rootY;
        }
    }
}
