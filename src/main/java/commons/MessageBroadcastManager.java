package commons;

import com.google.inject.Singleton;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Singleton
public class MessageBroadcastManager implements BroadcastManager<Message> {
    private final Logger logger = LoggerFactory.getLogger(MessageBroadcastManager.class);
    private final ConcurrentMap<Socket, ObjectOutputStream> outputStreams = new ConcurrentHashMap<>();

    @Override
    public void broadcast(Message message) throws IOException {
        for (var outputStream : outputStreams.values()) {
            outputStream.writeObject(message);
        }
        logger.info("Broadcast: Message {} has been broadcasted.", message);
    }

    @Override
    public void addSocket(Socket socket) throws IOException {
        outputStreams.put(socket, new ObjectOutputStream(socket.getOutputStream()));
        logger.info("Broadcast: New socket {} has been added to the map.", socket);
    }

    @Override
    public void removeSocket(Socket socket) {
        outputStreams.remove(socket);
        logger.info("Broadcast: Socket {} has been removed from the map.", socket);
    }
}
