package toucan.environment;

public abstract class Actor extends Element {
    protected Position position;

    public Actor(ElementType type, int i, int j) {
        super(type);
        position(i, j);
    }

    public void position(int i, int j) {
        position = new Position(i, j);
    }



    public abstract void react(Environment environment);

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;

        Actor actor = (Actor) o;

        if (!position.equals(actor.position)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }

}
