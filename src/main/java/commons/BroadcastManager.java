package commons;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Consumer;

public interface BroadcastManager  {
    void broadcast(Consumer<OutputStream> consumer) throws IOException;
    void addOutputStream(OutputStream outputStream);
    void removeOutputStream(int key);
}
