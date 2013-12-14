package toucan.algorithms.mine.trees;

import java.util.TreeMap;

//todo: implement
public class IntervalTree extends TreeMap<Integer, IntervalTree.Slice>{

    @Override
    public Slice put(Integer key, Slice value) {
        return super.put(key, value);
    }

    protected static class Slice{
        final int max;
        final int lo;
        final int hi;

        private Slice(int max, int lo, int hi) {
            this.max = max;
            this.lo = lo;
            this.hi = hi;
        }
    }
}
