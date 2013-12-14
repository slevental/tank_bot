package toucan.environment;

public class Shell extends Actor {
    private Command orientation;

    public Shell(int i, int j) {
        super(ElementType.SHELL, i, j);
    }

    public void setOrientation(Command orientation) {
        this.orientation = orientation;
    }

    public Command getOrientation() {
        return orientation;
    }

    @Override
    public void react(Environment environment) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shell)) return false;
        if (!super.equals(o)) return false;

        Shell shell = (Shell) o;

        if (orientation != shell.orientation) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (orientation != null ? orientation.hashCode() : 0);
        return result;
    }

    @Override
    public Element copy() {
        Shell copy = new Shell(position.i, position.j);
        copy.orientation = orientation;
        return copy;
    }
}
