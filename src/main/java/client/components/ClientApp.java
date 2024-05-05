package client.components;

import client.view.ChatView;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.components.Server;

public class ClientApp {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ClientModule());
        ChatView chatView = injector.getInstance(ChatView.class);
        chatView.setVisible(true);
    }

    private final Logger logger = LoggerFactory.getLogger(ClientApp.class);
}
