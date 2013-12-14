package toucan.client;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import toucan.environment.Command;
import toucan.environment.Environment;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class TankWebSocket {
    private static final String ANSI_CLS = "\u001b[2J";
    private static final String ANSI_HOME = "\u001b[H";
    private static final boolean DEBUG = Boolean.getBoolean("debug");
    private static final long timeout = 800;

    private Environment environment;
    private AtomicBoolean initialized = new AtomicBoolean();

    private final CountDownLatch closeLatch;
    private Session session;

    public TankWebSocket() {
        this.closeLatch = new CountDownLatch(1);
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("Connection closed: %d - %s%n", statusCode, reason);
        this.session = null;
        this.closeLatch.countDown();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("Got connect: %s%n", session);
        this.session = session;
    }

    @OnWebSocketMessage
    public void onMessage(String msg) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        long started = System.currentTimeMillis();

        msg = StringUtils.removeStart(msg, "board=");
        if (initialized.compareAndSet(false, true)) {
            environment = new Environment((int) Math.sqrt(msg.length()));
        }
        environment.update(msg);


        Future<Command[]> reaction = environment.getReaction();
        Command[] commands = reaction.get(timeout - (System.currentTimeMillis() - started), TimeUnit.MILLISECONDS);
        String action = StringUtils.join(commands, ", ");
        session.getRemote().sendString(action);

        if (DEBUG) {
            System.out.println(environment);
            System.out.println(action);
            System.out.flush();
        }
    }
}
