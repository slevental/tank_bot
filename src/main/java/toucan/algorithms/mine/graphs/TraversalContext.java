package toucan.algorithms.mine.graphs;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

public class TraversalContext {
    private final int[] parentCtx;
    private final BitSet processed = new BitSet();
    private final BitSet discovered = new BitSet();
    private final int[] startTime;
    private final int[] endTime;
    private final AtomicInteger time;

    private TraversalContext(int[] parentCtx, int[] startTime, int[] endTime, AtomicInteger time) {
        this.parentCtx = parentCtx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.time = time;
    }

    public static TraversalContext creteBFSContext(int vertexCount) {
        return new TraversalContext(new int[vertexCount], null, null, null);
    }

    public static TraversalContext createDFSContext(int vertexCount) {
        return new TraversalContext(new int[vertexCount], new int[vertexCount], new int[vertexCount], new AtomicInteger());
    }

    public BitSet getProcessed() {
        return processed;
    }

    public void markDiscovered(int nodeIndex) {
        discovered.set(nodeIndex);
    }

    public void markProcessed(int nodeIndex) {
        processed.set(nodeIndex);
    }

    public void updateStartTime(int nodeIndex) {
        if (startTime == null || time == null) {
            throw new IllegalStateException("Unsupported method for this kind of traversal");
        }
        startTime[nodeIndex] = time.incrementAndGet();
    }

    public boolean isDiscovered(int nodeIndex) {
        return discovered.get(nodeIndex);
    }

    public boolean isProcessed(int nodeIndex) {
        return processed.get(nodeIndex);
    }

    public void setParent(int child, int parent){
        parentCtx[child] = parent;
    }

    public void updateEndTime(int nodeIndex) {
        if (endTime == null || time == null) {
            throw new IllegalStateException("Unsupported method for this kind of traversal");
        }
        endTime[nodeIndex] = time.incrementAndGet();
    }

    public BitSet getDiscovered() {
        return discovered;
    }

    public int[] getStartTime() {
        return startTime;
    }

    public int[] getEndTime() {
        return endTime;
    }

    public AtomicInteger getTime() {
        return time;
    }

    public int[] getParentCtx() {
        return parentCtx;
    }
}
