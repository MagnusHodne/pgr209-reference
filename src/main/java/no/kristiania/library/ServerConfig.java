package no.kristiania.library;

import jakarta.inject.Singleton;
import no.kristiania.library.database.BookRepository;
import no.kristiania.library.database.JdbcBookRepository;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class ServerConfig extends ResourceConfig {
    public ServerConfig(DataSource dataSource) {
        super(BookEndpoint.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(JdbcBookRepository.class).to(BookRepository.class).in(Singleton.class);
                bind(dataSource).to(DataSource.class);
            }
        });

        Map<String, String> props = new HashMap<>();
        props.put("jersey.config.server.wadl.disableWadl", "true");
        setProperties(props);
    }
}
