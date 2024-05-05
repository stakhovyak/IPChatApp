package server.components;

import com.google.inject.Inject;
import commons.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class Server {
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private final IO<byte[]> io;
    private final BroadcastManager broadcastManager;

    @Inject
    public Server(ServerSocket serverSocket, ExecutorService executorService, IO<byte[]> io, BroadcastManager broadcastManager) {
        this.serverSocket = serverSocket;
        this.executorService = executorService;
        this.io = io;
        this.broadcastManager = broadcastManager;

        logger.debug("Dependencies of Server are injected");
    }

    public void start() throws IOException {
        while (!serverSocket.isClosed()) {
            logger.info("Server is listening to incoming connections.");
            var socket = serverSocket.accept();
            logger.info("New connection has accepted");
            executorService.submit(new ServerConnectionHandler(socket, io, broadcastManager));
            logger.info("Creating ServerConnectionHandler");
        }
    }
    private final Logger logger = LoggerFactory.getLogger(Server.class);
}
