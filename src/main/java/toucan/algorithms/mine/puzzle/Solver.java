package toucan.algorithms.mine.puzzle;

import java.util.*;

public class Solver {
    private final Board initial;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable() != null;
    }

    private Entry solvable() {
        SolutionIterator i1 = new SolutionIterator(initial);
        SolutionIterator i2 = new SolutionIterator(initial.twin());
        while (i1.hasNext() && i2.hasNext()) {
            Entry n = i1.next();
            if (n.value.isGoal()) return n;
            if (i2.next().value.isGoal()) return null;
        }
        return null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        Iterable<Board> s = solution();
        return s != null ? ((List) s).size() - 1 : -1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        Entry s = solvable();
        return s == null ? null : revert(s);
    }

    private static class SolutionIterator implements Iterator<Entry> {
        private final PriorityQueue<Entry> queue = new PriorityQueue<Entry>(0x10, COMPARATOR);

        private SolutionIterator(Board init) {
            queue.add(new Entry(null, init, 0));
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Entry next() {
            Entry curr = queue.poll();
            for (Board each : curr.value.neighbors()) {
                if (curr.parent != null && each.equals(curr.parent.value)) continue;
                queue.add(new Entry(curr, each, curr.moves + 1));
            }
            return curr;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Entry {
        final Board value;
        final int moves;
        final Entry parent;

        public Entry(Entry parent, Board value, int moves) {
            this.parent = parent;
            this.value = value;
            this.moves = moves;
        }
    }

    private static final Comparator<Entry> COMPARATOR = new Comparator<Entry>() {
        @Override
        public int compare(Entry o1, Entry o2) {
            return (o1.value.manhattan() + o1.moves) - (o2.value.manhattan() + o2.moves);
        }
    };

    private List<Board> revert(Entry curr) {
        LinkedList<Board> res = new LinkedList<Board>();
        while (curr != null) {
            res.addFirst(curr.value);
            curr = curr.parent;
        }
        return res;
    }
}