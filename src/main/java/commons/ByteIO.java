package commons;

import com.google.inject.Singleton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Singleton
public class ByteIO implements IO<byte[]> {
    @Override
    public void send(byte[] data, Socket socket) {
        try {
            var outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(data);
        } catch (IOException e) {
            // TODO: handle
        }
    }

    @Override
    public byte[] receive(Socket socket) {
        try {
            var inputStream = new ObjectInputStream(socket.getInputStream());
            return (byte[]) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // TODO: Handle
            throw new RuntimeException();
        }
    }
}
