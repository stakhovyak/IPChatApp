package client.view;

import client.components.ChatPresenter;
import commons.Message;

public interface ChatView {
    void displayMessage(Message message);

    void displayError(String errorMessage);

    void setChatPresenter(ChatPresenter presenter);

    void setVisible(Boolean visible);
}
