package toucan.algorithms.mine.sort;

import toucan.algorithms.mine.trees.Heap;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * O(n*log(n)) sorting algorithms
 */
public final class SortUtils {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    private SortUtils() {
    }

    public static <E extends Comparable<E>> void heapSort(List<E> list) {
        Heap.heapSort(list); // delegate to heap sort
    }

    public static <E extends Comparable<E>> void mergeSort(List<E> list) {
        mergeSort(list, 0, list.size() - 1);
    }

    public static <E extends Comparable<E>> void parallelMergeSort(List<E> list) throws ExecutionException, InterruptedException {
        parallelMergeSort(list, 0, list.size() - 1, 0);
    }

    public static <E extends Comparable<E>> void quickSort(List<E> list) {
        quickSort(list, 0, list.size() - 1);
    }

    private static <E extends Comparable<E>> void quickSort(List<E> list, int low, int high) {
        if (low >= high) {
            return;
        }
        int p = partition(list, low, high);
        quickSort(list, low, p - 1);
        quickSort(list, p + 1, high);
    }

    private static <E extends Comparable<E>> int partition(List<E> list, int low, int high) {
        for (int i = low; i < high; i++) {
            if (list.get(i).compareTo(list.get(high)) < 0) {
                swap(list, i, low);
                low++;
            }
        }
        swap(list, high, low);
        return low;
    }

    private static <E extends Comparable<E>> void mergeSort(List<E> list, int low, int high) {
        if (low >= high) {
            return;
        }
        int middle = (low + high) / 2;
        mergeSort(list, low, middle);
        mergeSort(list, middle + 1, high);
        merge(list, low, middle, high);
    }

    private static <E extends Comparable<E>> void parallelMergeSort(final List<E> list, final int low, final int high, final int depth) throws ExecutionException, InterruptedException {
        if (low >= high) {
            return;
        }

        class MergeSortAgent<E extends Comparable<E>> implements Runnable {
            private final List<E> list;
            private final int low;
            private final int high;

            MergeSortAgent(List<E> list, int low, int high) {
                this.list = list;
                this.low = low;
                this.high = high;
            }

            public void run() {
                try {
                    parallelMergeSort(list, low, high, depth + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        final int middle = (low + high) / 2;
        if (depth >= Runtime.getRuntime().availableProcessors() / 2) {
            parallelMergeSort(list, low, middle, depth + 1);
            parallelMergeSort(list, middle + 1, high, depth + 1);
        } else {
            Future<?> merge1 = EXECUTOR.submit(new MergeSortAgent<E>(list, low, middle));
            Future<?> merge2 = EXECUTOR.submit(new MergeSortAgent<E>(list, middle + 1, high));
            merge1.get();
            merge2.get();
        }
        merge(list, low, middle, high);
    }

    private static <E extends Comparable<E>> void merge(List<E> list, int low, int middle, int high) {
        Queue<E> left = createQueue(list, low, middle);
        Queue<E> right = createQueue(list, middle + 1, high);
        while (!(left.isEmpty() || right.isEmpty())) {
            if (left.peek().compareTo(right.peek()) < 0) {
                list.set(low++, left.poll());
            } else {
                list.set(low++, right.poll());
            }
        }
        while (!left.isEmpty()) list.set(low++, left.poll());
        while (!right.isEmpty()) list.set(low++, right.poll());
    }

    private static <E extends Comparable<E>> Queue<E> createQueue(List<E> list, int low, int high) {
        ArrayDeque<E> rc = new ArrayDeque<E>(high - low + 1);
        for (int i = low; i <= high; i++) {
            rc.addLast(list.get(i));
        }
        return rc;
    }

    private static <E> void swap(List<E> list, int pos1, int pos2) {
        E buff = list.get(pos1);
        list.set(pos1, list.get(pos2));
        list.set(pos2, buff);
    }
}
