package client.components;

import client.view.ChatView;
import commons.Message;

public interface ChatPresenter {
    void onMessageReceived(Message message);

    void onError(String errorMessage);

    void connect(String serverAddress, int port);

    void sendMessage(Message message);

    void setChatView(ChatView chatView);
}
