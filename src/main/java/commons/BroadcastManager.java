package commons;

import java.io.IOException;
import java.net.Socket;

public interface BroadcastManager<T>  {
    void broadcast(T data) throws IOException;
    void addSocket(Socket socket) throws IOException;
    void removeSocket(Socket socket);
}
