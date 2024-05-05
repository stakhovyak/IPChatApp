package commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageSerializer implements Serializer<Message> {
    @Override
    public byte[] serialize(Message obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Error serializing object", e);
            throw new RuntimeException(e);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(MessageSerializer.class);
}
