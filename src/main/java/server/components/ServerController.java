package server.components;

import com.google.inject.Inject;
import commons.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class ServerController {
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private final IO<byte[]> io;
    private final DataProcessor<Message> dataProcessor;
    private final BroadcastManager<Message> broadcastManager;

    @Inject
    public ServerController(ServerSocket serverSocket, ExecutorService executorService, IO<byte[]> io, DataProcessor<Message> dataProcessor, BroadcastManager<Message> broadcastManager) {
        this.serverSocket = serverSocket;
        this.executorService = executorService;
        this.io = io;
        this.dataProcessor = dataProcessor;
        this.broadcastManager = broadcastManager;
    }

    public void start() throws IOException {
        while (!serverSocket.isClosed()) {
            executorService.submit(new ServerConnectionHandler(serverSocket.accept(), io, dataProcessor, broadcastManager));
        }
    }
}
