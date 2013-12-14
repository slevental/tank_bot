package toucan.algorithms.mine.queue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings({"unchecked"})
public class Deque<E> implements Iterable<E> {
    private static final int DEFAULT_SIZE = 0x2;

    private int head;
    private int tail;
    private E[] container;

    private int modCount;

    public Deque() {
        container = (E[]) new Object[DEFAULT_SIZE];
    }

    public Deque(int size) {
        int pof2 = 1;
        while ((pof2 = pof2 << 1) < size) ;
        container = (E[]) new Object[pof2];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return head - tail & (container.length - 1);
    }

    public void addFirst(E item) {
        modCount++;
        checkItem(item);
        container[head = (head + 1) & (container.length - 1)] = item;
        if (head == tail)
            resizeArray();
    }

    public void addLast(E item) {
        modCount++;
        checkItem(item);
        container[tail] = item;
        if (head == (tail = (tail - 1) & (container.length - 1)))
            resizeArray();
    }

    public E removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        modCount++;
        E e = container[head];
        container[head] = null;
        head = (head - 1) & (container.length - 1);
        if (size() < container.length / 4)
            shrinkArray();
        return e;
    }

    public E removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        modCount++;
        E e = container[tail = (tail + 1) & (container.length - 1)];
        container[tail] = null;
        if (size() < container.length / 4)
            shrinkArray();
        return e;
    }

    public Iterator<E> iterator() {
        return new DequeIterator<E>();
    }

    private void checkItem(E item) {
        if (item == null)
            throw new NullPointerException("Item is null");
    }

    private void shrinkArray() {
        if (tail == head)
            return;

        E[] c = container;
        container = (E[]) new Object[Math.max(c.length / 4, DEFAULT_SIZE)];

        int n = c.length - 1; // last index of array
        int t = n - tail; // elements from tail to right edge
        int h = head + 1; // elements from left edge to head mark

        if (tail > head) {
            System.arraycopy(c, (tail + 1) & n, container, 0, t);
            System.arraycopy(c, 0, container, t, h);
            head = t + head;
        } else {
            System.arraycopy(c, (tail + 1) & n, container, 0, size());
            head = size() - 1;
        }

        // fill new tail mark
        tail = container.length - 1;
    }

    private void resizeArray() {
        assert tail == head;
        E[] prev = container;
        container = (E[]) new Object[prev.length * 2];
        int splitIndex = ++head;
        int rightElements = prev.length - splitIndex;
        System.arraycopy(prev, splitIndex, container, 0, rightElements);
        System.arraycopy(prev, 0, container, rightElements, splitIndex);
        head = prev.length - 1;
        tail = container.length - 1;
    }

    private class DequeIterator<E> implements Iterator<E> {
        int current = tail;
        int originalModCount = modCount;

        @Override
        public boolean hasNext() {
            return current != head;
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            if (modCount != originalModCount)
                throw new ConcurrentModificationException();
            return (E) container[current = (current + 1) & container.length - 1];
        }

        @Override
        public void remove() {
            if (modCount != originalModCount)
                throw new ConcurrentModificationException();
            if (current >= tail) {
                int n = current - tail; // elements after tail
                System.arraycopy(container, tail, container, tail = (tail + 1) & container.length - 1, n);
            } else {
                int n = head - current; // elements before head
                System.arraycopy(container, current + 1, container, current, n + 1);
                current = (current - 1) & container.length - 1;
                head = (head - 1) & (container.length - 1);
            }
        }
    }

}