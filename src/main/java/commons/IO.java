package commons;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IO <T> {
    void send(T data, OutputStream outputStream) throws IOException;
    T receive(InputStream inputStream) throws IOException;
}
