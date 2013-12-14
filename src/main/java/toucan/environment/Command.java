package toucan.environment;

import java.util.EnumMap;

public enum Command {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    ACT
    ;

    private static final EnumMap<Command, Command> BACK = new EnumMap<>(Command.class);

    static {
        BACK.put(LEFT, RIGHT);
        BACK.put(RIGHT, LEFT);
        BACK.put(UP, DOWN);
        BACK.put(DOWN, UP);
        BACK.put(ACT, ACT);
    }

    public Command back(){
        return BACK.get(this);
    }

}
