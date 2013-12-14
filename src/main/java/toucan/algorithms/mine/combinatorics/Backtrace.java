package toucan.algorithms.mine.combinatorics;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Backtrace {
    private boolean finished = false;
    private static final int MAXCANDIDATES = 100;

    public void backtrack(int[] a, AtomicInteger k, int input) {
        int[] c = new int[MAXCANDIDATES];
        AtomicInteger ncandidates = new AtomicInteger();
        if (isSolution(a, k, input)) {
            processSolution(a, k, input);
        } else {
            int j = k.incrementAndGet();
            constructCandidates(a, k, input, c, ncandidates);
            for (int i = 0; i < ncandidates.get(); i++) {
                a[j] = c[i];
                makeMove(a, k, input);
                backtrack(a, k, input);
                unmakeMove(a, k, input);
                if (finished) return;
            }
        }
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public abstract void unmakeMove(int[] a, AtomicInteger k, int input);
    public abstract void makeMove(int[] a, AtomicInteger k, int input);
    public abstract void constructCandidates(int[] a, AtomicInteger k, int input, int[] c, AtomicInteger ncandidates);
    public abstract void processSolution(int[] a, AtomicInteger k, int input);
    public abstract boolean isSolution(int[] a, AtomicInteger k, int input);
}
