package server.components;

import commons.IO;
import commons.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServerConnectionHandler implements Callable<Void> {
    private final Socket socket;
    private final IO inputOutput;
    private final BroadcastManager broadcastManager;

    public ServerConnectionHandler(Socket socket, BroadcastManager broadcastManager) {
        this.socket = socket;
        try {
            this.inputOutput = new IO(socket);
            this.broadcastManager = broadcastManager;
            broadcastManager.addConnection(this);
            System.out.println(broadcastManager.size());
        } catch (IOException e) {
            // TODO: handle
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Message message) {
        try {
            inputOutput.sendMessage(message);
        } catch (IOException e) {
            // TODO: handle
        }
    }

    public Message receiveMessage() {
        try {
            return inputOutput.receiveMessage();
        } catch (IOException | ClassNotFoundException e) {
            // TODO: handle
            return new Message(" ", " ");
        }
    }

    @Override
    public Void call() {
        try {
            while (socket.isConnected()) {
                broadcastManager.broadcastMessage(message -> {
                    try {
                        inputOutput.sendMessage(message);
                    } catch (IOException e) {
                        // TODO: handle
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (Exception e) {
            // TODO: handle!
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO: handle!
            }
        }
        return null;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            // Handle
        }
    }
}
