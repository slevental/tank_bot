package toucan.environment;

import java.util.HashMap;
import java.util.Map;

public enum ElementType {
    ME(true, '►', '◄', '▲', '▼'),
    TANK(true, '>', '<', '˄', '˅'),
    DEAD_TANK(false, 'Ѡ'),
    TERRA_INCOGNITO(false, '☼'),
    FREE(false, ' '),
    SHELL(true, '•'),
    WALL(false, '╬','╩','╦','╠','╣','╨','╥','╞','╡','│','─','┌','┐','└','┘');

    private final boolean actor;
    private final char[] chars;

    private static Map<Character, ElementType> index = new HashMap<>();

    static {
        for (ElementType each : values()) {
            for (char c : each.chars) {
                index.put(c, each);
            }
        }
    }

    private ElementType(boolean oriented, char... chars) {
        this.actor = oriented;
        this.chars = chars;
    }

    public boolean isActor() {
        return actor;
    }

    public char[] getChars() {
        return chars;
    }

    public static ElementType parseChar(char c) {
        return index.get(c);
    }
}
