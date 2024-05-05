package server.components;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServerApp {
    private final Server server;

    @Inject
    public ServerApp(Server server) {
        this.server = server;
    }

    public void startServer() {
        logger.info("The serverApp is started.");
        try {
            server.start();
        } catch (IOException e) {
            // ignore.
        }
        logger.info("The serverApp is started.");
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ServerModule());
        ServerApp serverStarter = injector.getInstance(ServerApp.class);
        serverStarter.startServer();
    }

    private final Logger logger = LoggerFactory.getLogger(Server.class);
}