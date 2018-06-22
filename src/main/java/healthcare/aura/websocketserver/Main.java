package healthcare.aura.websocketserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocketImpl;

import java.io.IOException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PORT = 8887;

    public static void main(String[] args) throws InterruptedException , IOException {
        WebSocketImpl.DEBUG = true;
        LOGGER.info("WebSocketImpl debug mode : {}", WebSocketImpl.DEBUG);
        AuraWebSocketServer server = new AuraWebSocketServer(PORT);
        server.start();
    }
}
