package toucan.algorithms.mine.unionfind;

import java.util.Arrays;

abstract class AbstractUnionFind implements UnionFind{
    protected final int[] container;

    public AbstractUnionFind(int size) {
        this.container = new int[size];
        clear();
    }

    protected void clear() {
        for (int i = 0; i < container.length; i++) {
            container[i] = i;
        }
    }

    protected void checkBounds(int elementToCheck){
        if (elementToCheck < 0 || elementToCheck >= container.length)
            throw new ArrayIndexOutOfBoundsException(elementToCheck);
    }

    @Override
    public String toString() {
        return Arrays.toString(container);
    }
}
