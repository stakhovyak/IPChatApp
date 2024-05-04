package server.components;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import java.io.IOException;

public class ServerApp {
    private final ServerController serverController;

    @Inject
    public ServerApp(ServerController serverController) {
        this.serverController = serverController;
    }

    public void startServer() {
        try {
            serverController.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Injector injector = Guice.createInjector(new ServerModule());
        ServerApp serverStarter = injector.getInstance(ServerApp.class);
        serverStarter.startServer();
    }
}