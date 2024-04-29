package client.components;

import commons.IO;
import commons.Message;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {
    private final Socket socket;
    private final IO inputOutput;

    public ClientConnectionHandler(Socket socket) {
        this.socket = socket;
        try {
            this.inputOutput = new IO(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                Message message = inputOutput.receiveMessage();
            }
        } catch (IOException | ClassNotFoundException e) {
            // Handle
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // Handle
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            inputOutput.sendMessage(message);
        } catch (IOException e) {
            // Handle
        }
        System.out.println("message sent in client");
    }

    public IO getInputOutput() {
        return inputOutput;
    }

    public Socket getSocket() {
        return socket;
    }
}