package client.components;

import client.view.ChatView;
import client.view.ChatWindow;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import commons.*;

public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(new TypeLiteral<IO<byte[]>>() {}).to(ByteIO.class);
        bind(new TypeLiteral<DataProcessor<Message>>() {}).to(MessageProcessor.class);
        bind(new TypeLiteral<Serializer<Message>>() {}).to(MessageSerializer.class);
        bind(ClientConnectionHandler.class).in(Singleton.class);
        bind(ChatPresenter.class).to(ClientPresenter.class).in(Singleton.class);
        bind(ChatView.class).to(ChatWindow.class).in(Singleton.class);

        bind(String.class).annotatedWith(Names.named("serverAddress")).toInstance("localhost");
        bind(Integer.class).annotatedWith(Names.named("port")).toInstance(1111);
    }
}
