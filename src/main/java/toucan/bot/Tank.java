package toucan.bot;

import toucan.environment.Actor;
import toucan.environment.Element;
import toucan.environment.ElementType;
import toucan.environment.Environment;

public  class Tank extends Actor {

    public Tank(ElementType type, int i, int j) {
        super(type, i, j);
    }

    @Override
    public void react(Environment environment) {
    }

    @Override
    public Element copy() {
        return new Tank(type, position.i, position.j);
    }
}
