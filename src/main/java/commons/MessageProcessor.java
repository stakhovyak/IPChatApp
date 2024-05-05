package commons;

import com.google.inject.Singleton;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Singleton
public class MessageProcessor implements DataProcessor<Message> {
    @Override
    public Message process(byte[] data) {
        try (var objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // TODO: Handle.
            return null;
        }
    }
}
