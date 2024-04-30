package client.components;

import java.io.IOException;
import java.net.Socket;

public class ClientController {
    private final Socket socket;
    private final ClientConnectionHandler connectionHandler;

    public ClientController(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            connectionHandler = new ClientConnectionHandler(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startClient() {
        new Thread(connectionHandler).start();
        //SwingUtilities.invokeLater(ChatWindow::new);
    }

    public ClientConnectionHandler getClientConnectionHandler() {
        return connectionHandler;
    }
}