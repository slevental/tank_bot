package toucan.environment;

import java.util.concurrent.Callable;

/**
* User: esLion
* Date: 12/14/13
*/
public abstract class StatefulCallable implements Callable<Command[]> {
    public volatile Command[] commands;

    StatefulCallable(Command[] commands) {
        this.commands = commands;
    }
}
