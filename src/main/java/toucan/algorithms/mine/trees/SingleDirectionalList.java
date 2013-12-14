package toucan.algorithms.mine.trees;

/**
 * @author Stanislav Levental
 */
public class SingleDirectionalList<E> {
    private Entry<E> head;
    private Entry<E> tail;
    private int size;

    public void insert(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot be null");
        }
        size++;
        if (tail == null) {
            assert head == null; // first element
            head = tail = new Entry<E>(element);
            return;
        }
        tail.setNext(new Entry<E>(element));
        tail = tail.getNext();
    }

    public int size() {
        return size;
    }

    public E get(int idx) {
        return getEntry(idx).getValue();
    }

    public Entry<E> getEntry(int idx) {
        validateIdx(idx);
        if (idx == size - 1) {
            return tail;
        }
        Entry<E> next = head;
        return iterate(idx, next, 0);
    }

    public boolean delete(int idx) {
        validateIdx(idx);
        if (idx == 0) {
            head = head.getNext();
            size--;
            return true;
        }
        Entry<E> previous = iterate(idx, head, 1);
        Entry<E> next = previous.getNext();
        if (next.getNext() != null) {
            previous.setNext(next.getNext());
        } else {
            previous.setNext(null);
            tail = previous;
        }
        size--;
        return true;
    }

    private Entry<E> iterate(int idx, Entry<E> next, int till) {
        while (idx-- > till) {
            next = next.getNext();
        }
        return next;
    }

    private void validateIdx(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Index cannot be lower than 0 and bigger or equal than size:" + size);
        }
    }

    public void reverse() {
        Entry<E> buffer = head;
        head = reverse(head, null);
        tail = buffer;
    }

    private Entry<E> reverse(Entry<E> curr, Entry<E> prev) {
        Entry<E> next = curr.getNext();
        curr.setNext(prev);
        return next != null ? reverse(next, curr) : curr;
    }

    public static class Entry<E> {
        private final E value;
        private Entry<E> next;

        public Entry(E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }

        public Entry<E> getNext() {
            return next;
        }

        public void setNext(Entry<E> next) {
            this.next = next;
        }
    }
}
