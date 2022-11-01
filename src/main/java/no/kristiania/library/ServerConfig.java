package no.kristiania.library;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class ServerConfig extends ResourceConfig {
    public ServerConfig() {
        super(BookEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(BookRepository.class).to(IBookRepository.class);
            }
        });
    }
}
