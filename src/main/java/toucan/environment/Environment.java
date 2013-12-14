package toucan.environment;

import toucan.tactics.AStarTactics;

import java.util.Random;
import java.util.concurrent.*;

public class Environment {
    public static final int COMMANDS_SIZE = Command.values().length;
    public static final Random RND = new Random(19);
    public static final Executor EXECUTOR = Executors.newSingleThreadExecutor();

    private final BattleMap battleMap;

    public Environment(int size) {
        this.battleMap = new BattleMap(size);
    }

    public void update(String newMap) {
        this.battleMap.update(newMap);
    }

    @Override
    public String toString() {
        return battleMap.toString();
    }

    public Future<Command[]> getReaction() {
        Command[] cmd = new Command[]{battleMap.getFirstPossible()};
        if (cmd[0] != Command.ACT){
            cmd = new Command[]{Command.ACT, cmd[0]};
        }
        MomentFuture f = new MomentFuture(new StatefulCallable(cmd) {
            @Override
            public Command[] call() throws Exception {
                return new AStarTactics(battleMap, this).offer();
            }
        });
        EXECUTOR.execute(f);
        return f;
    }

    private class MomentFuture extends FutureTask<Command[]> {
        private final StatefulCallable callable;

        public MomentFuture(StatefulCallable callable) {
            super(callable);
            this.callable = callable;
        }

        @Override
        public Command[] get() throws InterruptedException, ExecutionException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Command[] get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            try {
                return super.get(timeout, unit);
            } catch (InterruptedException|ExecutionException e) {
                throw e;
            } catch (TimeoutException e) {
                System.out.println("WARN: timeout reached!");
                return callable.commands; // return as is
            }
        }
    }
}
