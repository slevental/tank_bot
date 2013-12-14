package toucan.algorithms.mine.combinatorics;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class AllPossibleSubsetsGeneration extends Backtrace {
    @Override
    public void unmakeMove(int[] a, AtomicInteger k, int input) {
    }

    @Override
    public void makeMove(int[] a, AtomicInteger k, int input) {
    }

    @Override
    public void constructCandidates(int[] a, AtomicInteger k, int input, int[] c, AtomicInteger ncandidates) {
        c[0] = 1;
        c[1] = 0;
        ncandidates.set(2);
    }

    @Override
    public void processSolution(int[] a, AtomicInteger k, int input) {
        System.out.println(Arrays.toString(a));
    }

    @Override
    public boolean isSolution(int[] a, AtomicInteger k, int input) {
        return k.get() == input;
    }

    public static void main(String[] args) {
        AllPossibleSubsetsGeneration generation = new AllPossibleSubsetsGeneration();
        int[] ints = new int[5];
        generation.backtrack(ints,new AtomicInteger(), 4);
    }
}
