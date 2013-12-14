package toucan.tactics;

import toucan.environment.BattleMap;
import toucan.environment.Command;
import toucan.environment.StatefulCallable;

public class AStarTactics extends Tactic {
    private final StatefulCallable statefulCallable;

    public AStarTactics(BattleMap map, StatefulCallable statefulCallable) {
        super(map);
        this.statefulCallable = statefulCallable;
    }

    @Override
    public Command[] offer() {
        return statefulCallable.commands;
    }
}
