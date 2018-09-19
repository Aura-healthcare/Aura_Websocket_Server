package healthcare.aura.websocketserver;

/*
 * Copyright (c) 2010-2018 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

import java.io.*;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Type;

import com.google.gson.JsonIOException;
import healthcare.aura.websocketserver.utils.Utils;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;

import healthcare.aura.websocketserver.model.SensorData;
import healthcare.aura.websocketserver.model.SensitiveEventData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuraWebSocketServer extends WebSocketServer {
    private static final Logger LOGGER = LogManager.getLogger(AuraWebSocketServer.class);
    private AtomicInteger messageCounter = new AtomicInteger(0);
    private String dataFolderPath;

    AuraWebSocketServer(int port, String dataFolderPath) throws UnknownHostException {
        super(new InetSocketAddress(port));
        Utils.setupLocalFS(dataFolderPath);
        this.dataFolderPath = dataFolderPath;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        LOGGER.info("{} connected", conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        LOGGER.info("{} disconnected", conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        messageCounter.getAndIncrement();
        LOGGER.info("###### Message counter : {}", messageCounter);
        LOGGER.info("###### Thread number : {}", Thread.currentThread().getId());

        Gson gson = new Gson();
        String fileName = "";

        try {
            SensorData sensorData = gson.fromJson(message, SensorData.class);
            fileName = sensorData.generateFileName();
        } catch (Exception e) {
            LOGGER.info("Not a sensor data file");
        }

        try {
            Type type = new TypeToken<ArrayList<SensitiveEventData>>(){}.getType();
            ArrayList<SensitiveEventData> seizureDataList = gson.fromJson(message, type);
            fileName = seizureDataList.get(0).generateFileName();
            LOGGER.info("FileName " + fileName);
        } catch (Exception e) {
            LOGGER.info("Not a seizure data file");
        }

        if (fileName.isEmpty()) {
            LOGGER.error("Invalid input data file");
            return;
        }

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(dataFolderPath + fileName);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "utf-8");
                Writer bufferedWriter = new BufferedWriter(outputStreamWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter)
        ) {
            printWriter.println(message);
            conn.send(fileName + " : OK");
            LOGGER.info("New file written : {}", fileName);
        } catch (IOException e) {
            conn.send(fileName + " : KO");
            LOGGER.error("An error occurred when trying to write file : {}", fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        LOGGER.error("Some error occurred...");
        ex.printStackTrace();
        if (conn != null) {
            LOGGER.error("Connexion concerned : {}", conn.getRemoteSocketAddress().getAddress().getHostAddress());
        }
    }

    @Override
    public void onStart() {
        LOGGER.info("Server started on port : {}", this::getPort);
    }

}
