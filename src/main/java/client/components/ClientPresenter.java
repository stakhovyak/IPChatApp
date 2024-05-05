package client.components;

import client.view.ChatView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import commons.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ClientPresenter implements ChatPresenter {
    private ChatView chatView;
    private final ClientConnectionHandler connectionHandler;

    @Inject
    public ClientPresenter(ChatView chatView, ClientConnectionHandler connectionHandler) {
        this.chatView = chatView;
        this.connectionHandler = connectionHandler;
        this.connectionHandler.setChatPresenter(this);
        this.chatView.setChatPresenter(this);
    }

    public void sendMessage(Message message) {
        connectionHandler.sendMessage(message);
    }

    public void connect(String serverAddress, int port) {
        connectionHandler.connect(serverAddress, port);
    }

    @Override
    public void onMessageReceived(Message message) {
        logger.info("Message received and being displayed in ChatView");
        chatView.displayMessage(message);
    }

    @Override
    public void onError(String errorMessage) {
        chatView.displayError(errorMessage);
    }

    @Override
    public void setChatView(ChatView chatView) {
        this.chatView = chatView;
    }

    private static final Logger logger = LoggerFactory.getLogger(ClientConnectionHandler.class);
}
