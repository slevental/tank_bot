package toucan.algorithms.mine.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

@SuppressWarnings({"unchecked"})
public class RandomizedQueue<E> implements Iterable<E> {
    public static final int DEFAULT_SIZE = 0x10;

    private final Random rnd = new Random();
    private E[] container;
    private int head;

    public RandomizedQueue() {
        container = (E[]) new Object[DEFAULT_SIZE];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return head;
    }


    public void enqueue(E item) {
        if (item == null)
            throw new NullPointerException();
        int h = head++;
        int toSwap = rnd.nextInt(head);
        container[h] = item;
        if (toSwap != h) {
            E e = container[toSwap];
            container[h] = e;
            container[toSwap] = item;
        }
        if (head == container.length) {
            doubleArray();
        }
    }

    public E dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        E res = container[--head];
        container[head] = null; //for gc
        if (head < container.length / 4)
            shrinkArray();
        return res;
    }


    public E sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return container[rnd.nextInt(head)];
    }

    public Iterator<E> iterator() {
        return new QueueIterator<E>();
    }

    private void doubleArray() {
        E[] prev = container;
        container = (E[]) new Object[head << 1];
        System.arraycopy(prev, 0, container, 0, head);
    }

    private void shrinkArray() {
        E[] prev = container;
        container = (E[]) new Object[Math.max(prev.length / 2, DEFAULT_SIZE)];
        System.arraycopy(prev, 0, container, 0, head);
    }

    private class QueueIterator<E> implements Iterator<E> {
        private final RandomizedQueue<Integer> order;

        public QueueIterator() {
            this.order = new RandomizedQueue<Integer>();
            for (int i = 0; i < head; i++) {
                order.enqueue(i);
            }
        }

        @Override
        public boolean hasNext() {
            return !order.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return (E) container[order.dequeue()];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}