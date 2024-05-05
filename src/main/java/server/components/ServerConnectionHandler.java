package server.components;

import commons.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;

public class ServerConnectionHandler implements Callable<Void> {
    private final Logger logger = LoggerFactory.getLogger(ServerApp.class);

    private final Socket socket;
    private final IO<byte[]> io;
    private final BroadcastManager broadcastManager;

    public ServerConnectionHandler(Socket socket, IO<byte[]> io, BroadcastManager broadcastManager) {
        this.socket = socket;
        this.io = io;
        this.broadcastManager = broadcastManager;
        logger.info("New ServerConnectionHandler is constructed.");
    }

    @Override
    public Void call() {
        try {
            broadcastManager.addOutputStream(socket.getOutputStream());
            logger.info("Adding a new output stream from client's socket");
            while (!Thread.currentThread().isInterrupted()) {
                logger.info("Listening for byte sequences");
                var received = io.receive(socket.getInputStream());
                logger.info("Got something");
                broadcastManager.broadcast(out -> {
                    try {
                        logger.info("Trying to send byte sequence through broadcast manager.");
                        io.send(received, out);
                    } catch (IOException e) {
                        logger.info("Error occurred trying to send the sequence through the broadcast manager");
                        // TODO: exclude the out from the operation.
                    }
                });
            }
        } catch (IOException e) {
            logger.debug("Error occurred opening connection with client.");
            // TODO: Handle.
        } finally {
            try {
                socket.close();
                broadcastManager.removeOutputStream(socket.getOutputStream().hashCode());
            } catch (IOException e) {
                logger.debug("Error occurred trying to close the connected socket.");
                // TODO: Handle.
            }
        }
        return null;
    }
}
