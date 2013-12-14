package toucan.environment;

import toucan.bot.Tank;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BattleMap {
    private final Element[][] map;
    private final char[][] raw;
    private final int size;

    private final ArrayDeque<Actor> actors = new ArrayDeque<>();
    private Tank me;

    public BattleMap(int size) {
        this.size = size;
        this.map = new Element[size][size];
        this.raw = new char[size][size];
    }

    public Command getFirstPossible() {
        List<Command> values = Arrays.asList(Command.LEFT, Command.RIGHT, Command.UP, Command.DOWN);
        Collections.shuffle(values);
        for (Command command : values) {
            if (isPossible(command, me)) {
                return command;
            }
        }
        return Command.ACT;
    }

    public ArrayDeque<Actor> getActors() {
        return actors;
    }

    public Tank getMe() {
        return me;
    }

    public Element[][] getMap() {
        return map;
    }

    private boolean isPossible(Command command, Actor actor) {
        Position p = actor.getPosition();
        Element el = p.act(command, 1).get(map);
        return el != null && el.getType() == ElementType.FREE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                sb.append(raw[i][j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public void update(String newMap) {
        actors.clear();
        Element[][] previous = deepClone(map);
        updateState(newMap);
        identifyShellsOrientation(previous);
    }

    private void identifyShellsOrientation(Element[][] previous) {
        Map<Actor, Command> candidates = new HashMap<>();
        for (Actor actor : actors) {
            if (actor instanceof Shell && ((Shell) actor).getOrientation() == null) {
                for (Command each : Command.values()) {
                    if (each == Command.ACT) continue; // skip ACT
                    Element prev = actor.getPosition().act(each.back(), 2).get(previous);
                    if (prev != null && prev.getType() == ElementType.SHELL) {
                        if (!candidates.containsKey(actor) || ((Shell) prev).getOrientation() == each) {
                            candidates.put(actor, each);
                        }
                    }
                }
            }
        }
        for (Map.Entry<Actor, Command> each : candidates.entrySet()) {
            ((Shell) each.getKey()).setOrientation(each.getValue());
        }
    }

    private void updateState(String newMap) {
        for (int i = 0, j = 0; i < newMap.length(); i++, j = i / size) {
            raw[i % size][j] = newMap.charAt(i);
            ElementType type = ElementType.parseChar(newMap.charAt(i));
            Element el;
            if (type == ElementType.ME) {
                me = new Tank(ElementType.ME, i % size, j);
                el = me;
            } else if (type == ElementType.TANK) {
                el = new Tank(ElementType.TANK, i % size, j);
                actors.add((Actor) el);
            } else if (type == ElementType.SHELL) {
                el = new Shell(i % size, j);
                actors.add((Actor) el);
            } else {
                el = new Element(type);
            }
            map[i % size][j] = el;
        }
    }

    private Element[][] deepClone(Element[][] map) {
        Element[][] clone = new Element[map.length][map.length];
        for (int i = 0; i < clone.length; i++) {
            for (int j = 0; j < clone.length; j++) {
                Element el = map[i][j];
                clone[i][j] = el == null ? null : el.copy();
            }
        }
        return clone;
    }

}
