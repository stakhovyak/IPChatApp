package commons;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IO {
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private Socket socket;

    public IO(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMessage(Message message) throws IOException {
        objectOutputStream.writeObject(message);
        System.out.println("Message sent");
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException {
        System.out.println("Message received");
        return (Message) objectInputStream.readObject();
    }
}
