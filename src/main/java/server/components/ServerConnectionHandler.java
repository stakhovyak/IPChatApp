package server.components;

import commons.*;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServerConnectionHandler implements Callable<Void> {
    private final Socket socket;
    private final IO<byte[]> io;
    private final DataProcessor<Message> dataProcessor;
    private final BroadcastManager<Message> broadcastManager;

    public ServerConnectionHandler(Socket socket, IO<byte[]> io, DataProcessor<Message> dataProcessor, BroadcastManager<Message> broadcastManager) {
        this.socket = socket;
        this.io = io;
        this.dataProcessor = dataProcessor;
        this.broadcastManager = broadcastManager;
    }

    @Override
    public Void call() {
        try {
            broadcastManager.addSocket(socket);
            while (!Thread.currentThread().isInterrupted()) {
                broadcastManager.broadcast(dataProcessor.process(io.receive(socket)));
            }
        } catch (IOException e) {
            // TODO: Handle.
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO: Handle.
            }
            broadcastManager.removeSocket(socket);
        }
        return null;
    }
}
