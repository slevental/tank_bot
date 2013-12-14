package toucan.client;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Client {
    private static final String WS_HOST = "ws://tetrisj.jvmhost.net:12270/codenjoy-contest/ws?user=stas";

    public static void main(String[] args) {
        String destUri = WS_HOST;
        if (args.length > 0) {
            destUri = args[0];
        }
        WebSocketClient client = new WebSocketClient();
        TankWebSocket socket = new TankWebSocket();
        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            reconnect(client, socket, echoUri, request);
            System.out.printf("Connecting to : %s%n", echoUri);
            socket.awaitClose(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void reconnect(WebSocketClient client, TankWebSocket socket, URI echoUri, ClientUpgradeRequest request) throws IOException, InterruptedException {
        int millis = 10;
        for (; ; ) {
            try {
                client.connect(socket, echoUri, request).get(5, TimeUnit.SECONDS);
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e);
                System.out.println("Sleep before reconnect...");
                Thread.sleep(millis *= 2);
                System.out.printf("Reconnect... (timeout=%d)\n", millis);
            }
        }
    }

}
