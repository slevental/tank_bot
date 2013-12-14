package toucan.algorithms.mine.trees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * org.eslion.Heap implementation
 */
public class Heap<E extends Comparable<E>> {
    private List<E> entries;

    public Heap(int size) {
        this.entries = new ArrayList<E>(size);
    }

    public Heap(Collection<E> collection) {
        entries = new ArrayList<E>(collection);
        for (int i = entries.size() - 1; i >= 0; i--){
            bubbleDown(i);
        }
    }

    public void insert(E element) {
        entries.add(entries.size(), element);
        bubbleUp(entries.size() - 1);
    }

    public E popMin() {
        if (entries.isEmpty()) {
            return null;
        }
        E min = entries.get(0);
        if (entries.size() > 1){
            entries.set(0, entries.remove(entries.size() - 1));
            bubbleDown(0);
        }
        return min;
    }

    public int size() {
        return entries.size();
    }

    public static <E extends Comparable<E>> void heapSort(List<E> list){
        Heap<E> heap = new Heap<E>(list);
        for (int i = 0; i < list.size(); i++){
            list.set(i, heap.popMin());
        }
    }

    private void bubbleDown(int position) {
        int c = getLeft(position);
        int minIndex = position;
        for (int i = 0; i <= 1; i++)
            if (c + i < entries.size() && entries.get(minIndex).compareTo(entries.get(c + i)) > 0) minIndex = c + i;
        if (minIndex != position){
            swap(position, minIndex);
            bubbleDown(minIndex);
        }
    }

    private void bubbleUp(int position) {
        int parent = getParent(position);
        if (parent == -1)
            return;
        if (entries.get(parent).compareTo(entries.get(position)) > 0) {
            swap(position, parent);
            bubbleUp(parent);
        }
    }

    private void swap(int pos1, int pos2) {
        E buff = entries.get(pos1);
        entries.set(pos1, entries.get(pos2));
        entries.set(pos2, buff);
    }

    private int getLeft(int pos) {
        return pos * 2 + 1;
    }

    private int getRight(int pos) {
        return pos * 2 + 2;
    }

    private int getParent(int pos) {
        return pos != 0 ? (pos - 1) / 2 : -1;
    }
}
