package server.components;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import commons.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ServerSocket.class).toInstance(createServerSocket());
        bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
        bind(new TypeLiteral<IO<byte[]>>(){}).to(ByteIO.class);
        bind(new TypeLiteral<BroadcastManager>(){}).to(MessageBroadcastManager.class);
    }

    private ServerSocket createServerSocket() {
        try {
            return new ServerSocket(1111);
        } catch (IOException e) {
            throw new RuntimeException("Error creating server socket.", e);
        }
    }
}
