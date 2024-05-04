package commons;

import java.io.IOException;
import java.net.Socket;

public interface IO <T> {
    void send(T data, Socket socket) throws IOException;
    T receive(Socket socket) throws IOException;
}
