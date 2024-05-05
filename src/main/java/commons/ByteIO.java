package commons;

import com.google.inject.Singleton;

import java.io.*;

@Singleton
public class ByteIO implements IO<byte[]> {
    @Override
    public void send(byte[] data, OutputStream outputStream) {
        try {
            (new ObjectOutputStream(outputStream)).writeObject(data);
        } catch (IOException e) {
            // TODO: handle
        }
    }

    @Override
    public byte[] receive(InputStream inputStream) {
        try {
            return (byte[]) (new ObjectInputStream(inputStream)).readObject();
        } catch (IOException | ClassNotFoundException e) {
            // TODO: Handle
            throw new RuntimeException();
        }
    }
}
