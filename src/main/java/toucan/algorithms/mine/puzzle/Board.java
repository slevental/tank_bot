package toucan.algorithms.mine.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
    private static final Random rnd = new Random();
    private static final int[][] MOVES = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    private final int[][] blocks;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = clone(blocks);
    }

    // board dimension N
    public int dimension() {
        return this.blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int res = 0;
        for (int i = 1; i <= blocks.length * blocks.length; i++) {
            int el = blocks[toRow(i)][toCol(i)];
            if (el == 0)
                continue;
            if (el != i) res++;
        }
        return res;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int res = 0;
        for (int i = 1; i <= blocks.length * blocks.length; i++) {
            int el = blocks[toRow(i)][toCol(i)];
            if (el == 0)
                continue;
            if (el != i)
                res += Math.abs(toCol(i) - toCol(el)) + Math.abs(toRow(i) - toRow(el));
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        if (blocks.length < 2)
            return new Board(blocks);
        int[][] res = clone(blocks);
        int row, pos;
        do {
            row = rnd.nextInt(blocks.length);
            pos = rnd.nextInt(blocks.length - 1);
        } while (blocks[row][pos] == 0 || blocks[row][pos + 1] == 0);
        exch(res[row], pos, pos + 1);
        return new Board(res);
    }

    // does this board equal y?
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this == that) return true;
        if (this.getClass() != that.getClass()) return false;

        Board b = (Board) that;
        if (!Arrays.deepEquals(this.blocks, b.blocks)) return false;

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> res = new ArrayList<Board>(4);
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    for (int[] move : MOVES) {
                        int newI = i + move[0];
                        int newJ = j + move[1];
                        if (newI >= 0 && newI < blocks.length && newJ >= 0 && newJ < blocks.length) {
                            int[][] newNeighbor = clone(blocks);
                            exch(newNeighbor, i, j, newI, newJ);
                            res.add(new Board(newNeighbor));
                        }
                    }
                    break;
                }
            }
        }
        return res;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        int size = ("" + blocks.length * blocks.length).length();
        StringBuilder sb = new StringBuilder();
        sb.append(dimension()).append('\n');
        for (int[] row : blocks) {
            for (int i = 0; i < row.length; i++) {
                sb.append(String.format("%" + size + "s", row[i]));
                if (i < row.length - 1)
                    sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private int[][] clone(int[][] blocks) {
        int[][] res = blocks.clone();
        for (int i = 0; i < blocks.length; i++) {
            res[i] = blocks[i].clone();
        }
        return res;
    }

    private void exch(int[] array, int pos1, int pos2) {
        int v = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = v;
    }

    private void exch(int[][] newNeighbor, int i, int j, int newI, int newJ) {
        int v = newNeighbor[i][j];
        newNeighbor[i][j] = newNeighbor[newI][newJ];
        newNeighbor[newI][newJ] = v;
    }

    private int toRow(int i) {
        return (i - 1) / blocks.length;
    }

    private int toCol(int i) {
        return (i - 1) % blocks.length;
    }
}