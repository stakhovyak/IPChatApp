package server.components;

import commons.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BroadcastManager {
    private final List<ServerConnectionHandler> serverConnectionHandlerList;

    public BroadcastManager() {
        serverConnectionHandlerList = new ArrayList<>();
    }

    public void addConnection(ServerConnectionHandler serverConnectionHandler) {
        serverConnectionHandlerList.add(serverConnectionHandler);
    }

    public void broadcastMessage(Consumer<Message> consumer) {
        for (var connectionHandler : serverConnectionHandlerList) {
            consumer.accept(connectionHandler.receiveMessage());
        }
    }

    public int size() {
        return serverConnectionHandlerList.size();
    }

    public void closeAllConnections() {
        for (var connectionHandler : serverConnectionHandlerList) {
            connectionHandler.closeConnection();
        }
    }
}