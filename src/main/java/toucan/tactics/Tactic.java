package toucan.tactics;

import toucan.environment.BattleMap;
import toucan.environment.Command;

public abstract class Tactic {
    protected final BattleMap map;

    protected Tactic(BattleMap map) {
        this.map = map;
    }

    public abstract Command[] offer();
}
