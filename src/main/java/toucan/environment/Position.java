package toucan.environment;

import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;

public class Position implements Serializable{
    public final int i;
    public final int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public <E> E get(E[][] arr){
        if (!inBounds(arr.length, i, j)) return null;
        return arr[i][j];
    }

    private boolean inBounds(int length, int i, int j) {
        return i < length && i >= 0 && j < length && j >= 0;
    }

    public Position act(Command command, int moves) {
        switch (command) {
            case LEFT:
                return new Position(i, j - moves);
            case RIGHT:
                return new Position(i, j + moves);
            case UP:
                return new Position(i - moves, j);
            case DOWN:
                return new Position(i + moves, j);
            case ACT:
            default:
                return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (i != position.i) return false;
        if (j != position.j) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = i;
        result = 31 * result + j;
        return result;
    }
}
