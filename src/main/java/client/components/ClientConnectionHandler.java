package client.components;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.DataProcessor;
import commons.IO;
import commons.Message;
import commons.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

@Singleton
public class ClientConnectionHandler {
    private final IO<byte[]> io;
    private final Serializer<Message> messageSerializer;
    private final DataProcessor<Message> messageDataProcessor;

    private ChatPresenter chatPresenter;

    private Socket socket;

    @Inject
    public ClientConnectionHandler(IO<byte[]> io, Serializer<Message> serializer, DataProcessor<Message> messageDataProcessor) {
        this.io = io;
        this.messageSerializer = serializer;
        this.messageDataProcessor = messageDataProcessor;
    }

    public void setChatPresenter(ChatPresenter chatPresenter) {
        this.chatPresenter = chatPresenter;
    }

    public void connect(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            new Thread(this::receiveMessages).start();
        } catch (IOException e) {
            logger.error("Error connecting to server", e);
        }
    }

    private void receiveMessages() {
        try {
            while (!socket.isClosed()) {
                var received = io.receive(socket.getInputStream());
                logger.info("Received byte sequence");
                logger.info("Trying to process it");
                var processed = messageDataProcessor.process(received);
                logger.info("Processed, passing the message object to presenter");
                chatPresenter.onMessageReceived(processed);
            }
        } catch (Exception e) {
            logger.error("Error receiving message", e);
            chatPresenter.onError("Error receiving message");
        }
    }

    public void sendMessage(Message message) {
        try {
            logger.info("Trying to get an OutputStream from socket");
            var outputStream = socket.getOutputStream(); // TODO: socket is closed?
            logger.info("Serializing the message to send");
            var messageToSend = messageSerializer.serialize(message);
            logger.info("Sending message using IO interface");
            io.send(messageToSend, outputStream);
        } catch (Exception e) {
            logger.error("Error sending message", e);
            chatPresenter.onError("Error sending message.");
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.error("Error closing connection", e);
            chatPresenter.onError("Error closing connection.");
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(ClientConnectionHandler.class);
}
