package commons;

import com.google.inject.Singleton;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@Singleton
public class MessageBroadcastManager implements BroadcastManager {
    private final Logger logger = LoggerFactory.getLogger(MessageBroadcastManager.class);
    private final ConcurrentMap<Integer, OutputStream> outputStreamConcurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public void broadcast(Consumer<OutputStream> consumer) {
        for (var outputStream : outputStreamConcurrentHashMap.values()) {
            consumer.accept(outputStream);
        }
        logger.info("Message has been broadcasted.");
    }

    @Override
    public void addOutputStream(OutputStream outputStream) {
        outputStreamConcurrentHashMap.put(outputStream.hashCode(), outputStream);
        logger.info(outputStreamConcurrentHashMap.toString());
    }

    @Override
    public void removeOutputStream(int key) {

    }
}
