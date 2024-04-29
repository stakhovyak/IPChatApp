package server.components;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private final BroadcastManager broadcastManager;

    public ServerController(int port) {
        try {
            broadcastManager = new BroadcastManager();
            serverSocket = new ServerSocket(port);
            executorService = Executors.newCachedThreadPool();
        } catch (IOException e) {
            // TODO: handle
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        var serverController = new ServerController(6565);
        serverController.startServer();
    }

    public void startServer() {
        new Thread(() -> {
            while (!serverSocket.isClosed()) {
                try {
                    var handler = new ServerConnectionHandler(serverSocket.accept(), broadcastManager);
                    System.out.println("new handler created");
                    executorService.submit(handler);
                    System.out.println("New connection handled");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error creating ServerConnectionHandler: " + e.getMessage());
                }
                System.out.println("ExecutorService size: " + executorService.toString());
            }
        }).start();
    }

    public void closeServer() {
        try {
            serverSocket.close();
            executorService.shutdown();
            broadcastManager.closeAllConnections();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}